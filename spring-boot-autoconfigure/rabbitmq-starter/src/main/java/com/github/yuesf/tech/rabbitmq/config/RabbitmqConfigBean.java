package com.github.yuesf.tech.rabbitmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 17081286
 * @date 2019/9/7`
 * @since 2019.0821
 */
@Component

@ConfigurationProperties(RabbitmqConfigBean.PREFIX)
public class RabbitmqConfigBean {
    public static final String PREFIX = "rabbitmq";

    @NestedConfigurationProperty
    private RabbitmqConfig defaultConfig;

    @NestedConfigurationProperty
    private Map<String, RabbitmqConfig> config = new HashMap<>();

    public RabbitmqConfig getDefaultConfig() {
        return defaultConfig;
    }

    public void setDefaultConfig(RabbitmqConfig defaultConfig) {
        this.defaultConfig = defaultConfig;
    }

    public Map<String, RabbitmqConfig> getConfig() {
        return config;
    }

    public void setConfig(Map<String, RabbitmqConfig> config) {
        this.config = config;
    }
}
