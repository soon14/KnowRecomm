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
public class IRecommApplicationTag
{
    public static void main( String[] args )
    {
        SpringApplication.run(IRecommApplicationTag.class,args);
    }
}
