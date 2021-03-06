package com.bjike.goddess.contractquotemanager;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.context.annotation.*;

import java.io.IOException;

/**
 * 扫描com.bjike.goddess.common.consumer 加入过滤器引入userToken
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.bjike.goddess.contractquotemanager.action","com.bjike.goddess.common.consumer","com.bjike.goddess.contractquotemanager.config"},
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = {Configuration.class})})
@ImportResource({"classpath:app.xml"})
@EnableAutoConfiguration(exclude = {ValidationAutoConfiguration.class})
@PropertySource(value = {"classpath:permission.properties"},encoding="utf-8")
public class Application{

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class,args);
        System.in.read();
    }

}
