package com.yuesf.tech.springboot;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/*
 * @auth yuesf
 * @data 2019/10/16
 */
@SpringBootApplication
public class SpringApplicationBuilderDemo {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringApplicationBuilderDemo.class)
                .run(args);
    }
}
