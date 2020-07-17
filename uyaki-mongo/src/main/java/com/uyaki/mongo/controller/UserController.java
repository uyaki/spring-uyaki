package com.uyaki.mongo.controller;

import com.uyaki.mongo.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @date 2020/07/17
 */
@Api("user-service")
@RestController
public class UserController {
    @Resource
    private MongoTemplate mongoTemplate;

    @GetMapping("/user")
    @ApiOperation(("获取全量用户"))
    public List<User> listUser(){
        return mongoTemplate.findAll(User.class);
    }
}
