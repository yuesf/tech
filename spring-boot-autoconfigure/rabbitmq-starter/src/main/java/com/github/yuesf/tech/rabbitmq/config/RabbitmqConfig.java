package com.github.yuesf.tech.rabbitmq.config;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * rabbitmq 基本配置
 *
 * @author 17081286
 * @date 2019/9/22
 * @since 1.0
 */
public class RabbitmqConfig {

    @NestedConfigurationProperty
    private ExchangeConfig exchange;
    @NestedConfigurationProperty
    private QueueConfig queue;
    @NestedConfigurationProperty
    private BindingConfig binding;

    public ExchangeConfig getExchange() {
        return exchange;
    }

    public void setExchange(ExchangeConfig exchange) {
        this.exchange = exchange;
    }

    public QueueConfig getQueue() {
        return queue;
    }

    public void setQueue(QueueConfig queue) {
        this.queue = queue;
    }

    public BindingConfig getBinding() {
        return binding;
    }

    public void setBinding(BindingConfig binding) {
        this.binding = binding;
    }
}
