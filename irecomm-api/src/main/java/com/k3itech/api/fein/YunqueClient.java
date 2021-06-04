package com.k3itech.api.fein;

import com.k3itech.config.FeignHasHystrixConfigure;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author dell
 * @since 2021-05-16
 */
@FeignClient(name = "IRECOMM",configuration = FeignHasHystrixConfigure.class, fallbackFactory = ServiceFallbackFactory.class  )
public interface YunqueClient {
        /**
         *yunque推送接口调用
         * @param name
         * @return
         */
        @RequestMapping("/Hello/World")
        @HystrixCommand(fallbackMethod = "postFallBack")
        public Object postMessage(@RequestParam(value="name") String name);


}


