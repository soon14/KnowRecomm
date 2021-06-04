package com.k3itech.api.fein;

import com.k3itech.api.fein.YunqueClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author:dell
 * @since: 2021-06-03
 */
@Component
@Slf4j
public class ServiceFallbackFactory implements FallbackFactory<YunqueClient> {
    @Override
    public YunqueClient create(Throwable throwable) {
        return new YunqueClient() {
            @Override
            public Object postMessage(String data) {
                log.error("fallback");
                return "test";
            }
        };
    }
}
