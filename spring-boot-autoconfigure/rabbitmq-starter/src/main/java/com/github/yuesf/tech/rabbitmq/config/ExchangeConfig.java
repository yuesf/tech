package com.github.yuesf.tech.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Exchange 基本配置
 *
 * @author 17081286
 * @since 1.0
 */
@Valid
public class ExchangeConfig {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeConfig.class);
    @NotNull(message = "exchange name 不能为空")
    private String name;
    @NotNull(message = "exchange type 不能为空")
    private ExchangeType type;
    private Boolean durable = true;
    private Boolean autoDelete = false;
    private Map<String, Object> arguments;

    public boolean valid(){
        if(StringUtils.isEmpty(this.name)){
            logger.error("exchange name cannot be null.");
            return false;
        }
        if(StringUtils.isEmpty(this.type)){
            logger.error("exchange type cannot be null.");
            return false;
        }
        return true;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExchangeType getType() {
        return type;
    }

    public void setType(ExchangeType type) {
        this.type = type;
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
