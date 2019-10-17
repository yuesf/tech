package com.github.yuesf.tech.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author 17081286
 * @date 2019/9/22
 * @since 1.0
 */
@Validated
public class QueueConfig {
    private static final Logger logger = LoggerFactory.getLogger(QueueConfig.class);
    @NotNull(message = "queue name 不能为空")
    private String name;
    private Boolean durable = true;
    private Boolean autoDelete = false;
    private Boolean exclusive = false;
    private Map<String, Object> arguments;
    public boolean valid(){
        if(StringUtils.isEmpty(this.name)){
            logger.error("queue name cannot be null.");
            return false;
        }
        return true;
    }
    public Boolean getExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDurable() {
        return durable;
    }

    public void setDurable(Boolean durable) {
        this.durable = durable;
    }

    public Boolean getAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(Boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }
}
