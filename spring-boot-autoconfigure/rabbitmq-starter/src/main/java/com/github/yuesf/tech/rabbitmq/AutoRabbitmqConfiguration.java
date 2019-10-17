package com.github.yuesf.tech.rabbitmq;

import com.github.yuesf.tech.rabbitmq.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author 17081286
 * @date 2019/9/7
 * @since 2019.0821
 */
@Configuration
@ConditionalOnClass(Exchange.class)
@EnableConfigurationProperties(RabbitmqConfigBean.class)
public class AutoRabbitmqConfiguration implements InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(AutoRabbitmqConfiguration.class);
    private ApplicationContext context;
    @Autowired
    private RabbitmqConfigBean configBean;
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public AutoRabbitmqConfiguration(RabbitmqConfigBean configBean) {
        this.configBean = configBean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("AutoRabbitmqImportSelector init....");
        ConfigurableApplicationContext annotationContext = (ConfigurableApplicationContext) context;
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) annotationContext.getBeanFactory();

        RabbitmqConfig defaultConfig = configBean.getDefaultConfig();
        Map<String, RabbitmqConfig> configMap = configBean.getConfig();
        configMap.put("default", defaultConfig);
        Iterator<Map.Entry<String, RabbitmqConfig>> iterator = configMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, RabbitmqConfig> entry = iterator.next();
            String entryKey = entry.getKey();
            RabbitmqConfig entryValue = entry.getValue();
            valid(entryValue);
            //声明队列
            declareQueue(beanFactory, entryKey, entryValue.getQueue());
            //声明交换机
            declareExchange(beanFactory, entryKey, entryValue.getExchange());
            //绑定关系

            bindingQueueExchange(beanFactory, entryKey, entryValue);
        }
    }

    /**
     * 绑定关系
     *
     * @param beanFactory
     * @param entryKey
     * @param config
     */
    private void bindingQueueExchange(DefaultListableBeanFactory beanFactory, String entryKey,
            RabbitmqConfig config) {
        BindingConfig binding = config.getBinding();
        QueueConfig queue = config.getQueue();
        ExchangeConfig exchange = config.getExchange();
        if (null == binding || !binding.getEnable()) {
            return;
        }
            if(null == queue || null == exchange){
                logger.error("routing key don't binding, form queueName={}, exchangeName={} ",
                        queue.getName(), exchange.getName());
                return;
            }

        if (!ExchangeType.fanout.name().equals(exchange.getType()) && StringUtils
                .isEmpty(binding.getRoutingKey())) {
            logger.error("binding routing key is null, form queueName={}, exchangeName={} ",
                    queue.getName(), exchange.getName());
            return;
        }
        BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition();
        AbstractBeanDefinition definition = beanDefinition.getBeanDefinition();
        definition.setBeanClass(Binding.class);
        beanDefinition.addConstructorArgValue(queue.getName());
        beanDefinition.addConstructorArgValue(Binding.DestinationType.QUEUE);
        beanDefinition.addConstructorArgValue(exchange.getName());
        beanDefinition.addConstructorArgValue(binding.getRoutingKey());
        beanDefinition.addConstructorArgValue(binding.getArguments());
        beanFactory.registerBeanDefinition(entryKey + "Binding", definition);
    }

    /**
     * 声明队列
     *
     * @param beanFactory
     * @param entryKey
     * @param queue
     */
    private void declareQueue(DefaultListableBeanFactory beanFactory, String entryKey, QueueConfig queue) {
        if (null == queue || !queue.valid()) {
            return;
        }

        BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition();
        AbstractBeanDefinition definition = beanDefinition.getBeanDefinition();
        beanDefinition.addConstructorArgValue(queue.getName());
        beanDefinition.addConstructorArgValue(queue.getDurable());
        beanDefinition.addConstructorArgValue(queue.getExclusive());
        beanDefinition.addConstructorArgValue(queue.getAutoDelete());
        beanDefinition.addConstructorArgValue(queue.getArguments());
        //
        definition.setBeanClass(Queue.class);
        beanFactory.registerBeanDefinition(entryKey + "Queue", definition);
    }

    /**
     * 声明交换机
     *
     * @param beanFactory
     * @param entryKey
     * @param exchange
     */
    private void declareExchange(DefaultListableBeanFactory beanFactory, String entryKey, ExchangeConfig exchange) {
        if (null == exchange || !exchange.valid()) {
            return;
        }
        BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.genericBeanDefinition();
        AbstractBeanDefinition definition = beanDefinition.getBeanDefinition();
        beanDefinition.addConstructorArgValue(exchange.getName());
        beanDefinition.addConstructorArgValue(exchange.getDurable());
        beanDefinition.addConstructorArgValue(exchange.getAutoDelete());
        beanDefinition.addConstructorArgValue(exchange.getArguments());
        switch (exchange.getType()) {
            case topic:
                definition.setBeanClass(TopicExchange.class);
                break;
            case direct:
                definition.setBeanClass(DirectExchange.class);
                break;
            case fanout:
                definition.setBeanClass(FanoutExchange.class);
                break;
            default:
        }
        beanFactory.registerBeanDefinition(entryKey + "Exchange", definition);
        logger.info(exchange.getType() + " register.done...");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void valid(RabbitmqConfig config) {
        validate(config.getExchange());
        validate(config.getQueue());
        validate(config.getBinding());
    }

    private void validate(Object o) {
        if (null == o) {
            return;
        }
        Set<ConstraintViolation<Object>> errors = validator.validate(o);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors.iterator().next().getMessage());
        }
    }

}
