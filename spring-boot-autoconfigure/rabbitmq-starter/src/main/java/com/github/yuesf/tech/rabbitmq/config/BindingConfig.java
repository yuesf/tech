package com.github.yuesf.tech.rabbitmq.config;

import java.util.Map;

/**
 * @author 17081286
 * @date 2019/9/22
 * @since 1.0
 */
public class BindingConfig {
    private Boolean enable;
    private String routingKey;
    private Map<String, Object> arguments;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }
}
