package com.k3itech.service;

import java.util.concurrent.TimeUnit;

/**
 * @author dell
 * @since 2021-05-16
 */
public interface RedisService {
        /**
         * 存储数据
         * @param key
         * @param value
         * */

        void set(String key, String value);


        void  set(String key, String value, Long timeout, TimeUnit timeUnit);

        /**
         * 获取数据
         * @param key
         * @return 数据
         */
        String get(String key);

        /**
         * 设置超期时间
         * @param key
         * @param expire
         * @return 是否成功
         */
        boolean expire(String key, long expire);

        /**
         * 删除数据
         * @param key
         */
        void remove(String key);

        /**
         * 自增操作
         * @param delta 自增步长
         * @param key
         * @return 自增值
         */
        Long increment(String key, long delta);


    }


