package com.k3itech.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author:dell
 * @since: 2021-06-03
 */
@Configuration
public class FeignNotHystrixConfigure {

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        //改成Feign.Builder禁用Hystrix的支持
        return Feign.builder();
    }

}
