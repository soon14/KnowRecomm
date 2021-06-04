package com.k3itech.config;

import feign.Feign;
import feign.hystrix.HystrixFeign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author:dell
 * @since: 2021-06-03
 */
@Configuration
public class FeignHasHystrixConfigure {

    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder() {
        //返回HystrixFeign.Builder Feign默认支持Hystrix
        return HystrixFeign.builder();
    }
}



