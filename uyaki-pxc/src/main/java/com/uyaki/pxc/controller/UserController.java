package com.uyaki.pxc.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uyaki.pxc.model.dto.UserDTO;
import com.uyaki.pxc.model.vo.UserVO;
import com.uyaki.pxc.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表控制器
 *
 * @author uyaki
 * @since 2020 -06-15 10:51:38
 */
@Slf4j
@RestController
@RequestMapping("/user/v1")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 根据 Dto 条件分页获取列表
     *
     * @param userDTO DTO查询实体
     * @return 分页数据 user list
     */
    @GetMapping("/user")
    public IPage<UserVO> getUserList(UserDTO userDTO) {
        return this.userService.getUserList(userDTO);
    }

    /**
     * 根据ID获取信息
     *
     * @param id 主键
     * @return 单条数据 user info
     */
    @GetMapping("/user/{id}")
    public UserVO getUserInfo(@PathVariable("id") Long id) {
        return this.userService.getUserInfo(id);
    }

    /**
     * 新增信息
     *
     * @param userDTO DTO实体对象
     */
    @PostMapping("/user")
    public void saveUserInfo(@RequestBody UserDTO userDTO) {
        this.userService.saveUserInfo(userDTO);
    }

    /**
     * 修改信息
     *
     * @param userDTO DTO实体对象
     */
    @PutMapping("/user")
    public void updateUserInfo(@RequestBody UserDTO userDTO) {
        this.userService.updateUserInfo(userDTO);
    }

    /**
     * 删除信息(可批量)
     *
     * @param idList 主键集合
     */
    @DeleteMapping("/user")
    public void deleteUserByIdList(@RequestParam("idList") List<Long> idList) {
        this.userService.deleteUserByIdList(idList);
    }
}