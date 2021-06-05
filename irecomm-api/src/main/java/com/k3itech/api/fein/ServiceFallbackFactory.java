package com.k3itech.api.fein;

import com.k3itech.api.fein.YunqueClient;
import com.k3itech.vo.YunqueContent;
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
            public Object postMessage(YunqueContent yunqueContent) {
                log.error("fallback");
                return "test";
            }
        };
    }
}
