package com.uyaki.web.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uyaki.web.model.dto.UserDTO;
import com.uyaki.web.model.vo.UserVO;
import com.uyaki.web.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户表(User)表控制器
 *
 * @author uyaki
 * @since 2020 -07-03 11:10:47
 */
@Slf4j
@Api(tags = "用户表")
@RestController
@RequestMapping("/user/v1")
public class UserController {
    /**
     * 用户表服务对象
     */
    @Resource
    private UserService userService;

    /**
     * 根据 Dto 条件分页获取用户表列表
     *
     * @param userDTO DTO查询实体
     * @return 分页数据 user list
     */
    @GetMapping("/user")
    @ApiOperation(value = "分页获取用户表列表", produces = "application/json")
    public IPage<UserVO> getUserList(UserDTO userDTO) {
        return this.userService.getUserList(userDTO);
    }

    /**
     * 根据ID获取用户表信息
     *
     * @param id 主键
     * @return 单条数据 user info
     */
    @GetMapping("/user/{id}")
    @ApiOperation(value = "根据ID获取用户表信息", produces = "application/json")
    public UserVO getUserInfo(@PathVariable("id") Long id) {
        return this.userService.getUserInfo(id);
    }

    /**
     * 新增用户表信息
     *
     * @param userDTO DTO实体对象
     */
    @PostMapping("/user")
    @ApiOperation(value = "新增用户表信息", produces = "application/json")
    public void saveUserInfo(@RequestBody UserDTO userDTO) {
        this.userService.saveUserInfo(userDTO);
    }

    /**
     * 修改用户表信息
     *
     * @param userDTO DTO实体对象
     */
    @PutMapping("/user")
    @ApiOperation(value = "修改用户表信息", produces = "application/json")
    public void updateUserInfo(@RequestBody UserDTO userDTO) {
        this.userService.updateUserInfo(userDTO);
    }

    /**
     * 删除用户表信息(可批量)
     *
     * @param idList 主键集合
     */
    @DeleteMapping("/user")
    @ApiOperation(value = "删除用户表信息", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "主键ID集合", name = "idList", required = true)
    })
    public void deleteUserByIdList(@RequestParam("idList") List<Long> idList) {
        this.userService.deleteUserByIdList(idList);
    }
}