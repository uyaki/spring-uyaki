package com.uyaki.hello.controller;

import com.uyaki.hello.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Hello controller.
 *
 * @date 2020 /07/15
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    /**
     * Say hello string.
     *
     * @return the string
     */
    @GetMapping(value = "/say", produces = "application/json")
    public String sayHello() {
        return "hello";
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    @GetMapping(value = "/user/{id}", produces = "application/json")
    public User getUser(@PathVariable Long id) {
        User user = new User(1L, "张三");
        return user;
    }

    /**
     * List user list.
     *
     * @return the list
     */
    @GetMapping(value = "/user", produces = "application/json")
    public List<User> listUser() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "张三"));
        userList.add(new User(2L, "李四"));
        return userList;
    }
}
