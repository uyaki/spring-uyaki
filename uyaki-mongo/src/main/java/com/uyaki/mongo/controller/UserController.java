package com.uyaki.mongo.controller;

import com.uyaki.mongo.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * The type User controller.
 *
 * @date 2020 /07/17
 */
@Api("user-service")
@RestController
public class UserController {
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * List user list.
     *
     * @return the list
     */
    @ApiOperation(("获取全量用户"))
    @GetMapping("/user")
    public List<User> listUser() {
        return mongoTemplate.findAll(User.class);
    }

    /**
     * Find by id user.
     *
     * @param userId the user id
     * @return the user
     */
    @ApiOperation(("获取用户"))
    @GetMapping("/user/{userId}")
    public User findById(@PathVariable String userId) {
        return mongoTemplate.findById(userId, User.class);
    }

    /**
     * Save user.
     *
     * @param user the user
     * @return the user
     */
    @ApiOperation(("新增用户"))
    @PostMapping("/user")
    public User insert(User user) {
        return mongoTemplate.save(user);
    }

    /**
     * Delete by id.
     *
     * @param userId the user id
     */
    @ApiOperation(("删除用户"))
    @DeleteMapping("/user/{userId}")
    public void deleteById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        mongoTemplate.remove(query, User.class);
    }
}
