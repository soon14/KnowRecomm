package com.k3itech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author dell
 * @since 2021-05-16
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.k3itech.api.fein")
public class IRecommApplicationPost
{
    public static void main( String[] args )
    {
        SpringApplication.run(IRecommApplicationPost.class,args);
    }
}
