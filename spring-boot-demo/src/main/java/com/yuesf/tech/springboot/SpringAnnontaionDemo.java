package com.yuesf.tech.springboot;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
 * @auth yuesf
 * @data 2019/10/16
 */
@Configurable
public class SpringAnnontaionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        context.register(SpringAnnontaionDemo.class);

        context.refresh();

        System.out.println(context.getBean(SpringAnnontaionDemo.class));
    }
}
