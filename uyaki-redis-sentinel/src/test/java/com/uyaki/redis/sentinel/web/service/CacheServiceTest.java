package com.uyaki.redis.sentinel.web.service;

import com.uyaki.redis.sentinel.base.util.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author uyaki
 * @date 2020/07/05
 */
@SpringBootTest(classes = CacheServiceTest.class)
@RunWith(SpringRunner.class)
@SpringBootApplication
public class CacheServiceTest {
    @Resource
    private RedisTemplate redisTemplate;
    @Test
    public void testAdd(){
//        RedisUtils.set("s:v",123);
        redisTemplate.opsForValue().set("s:v",123);
    }
    @Test
    public void testGet(){
        assert  RedisUtils.getLong("s:v") == 123L;
    }
}