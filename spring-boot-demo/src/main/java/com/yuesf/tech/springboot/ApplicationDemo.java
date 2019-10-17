package com.yuesf.tech.springboot;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/*
 * @auth yuesf
 * @data 2019/10/17
 */
@Configurable
@EnableAutoConfiguration
public class ApplicationDemo {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationDemo.class,args);
    }
}
