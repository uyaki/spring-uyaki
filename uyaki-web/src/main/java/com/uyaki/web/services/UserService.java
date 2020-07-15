package com.uyaki.web.services;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uyaki.web.model.dto.UserDTO;
import com.uyaki.web.model.vo.UserVO;

import java.util.List;

/**
 * 用户表(User)表服务接口
 *
 * @author uyaki
 * @since 2020 -07-03 11:10:52
 */
public interface UserService {

    /**
     * 根据 Dto 条件分页获取用户表列表
     *
     * @param userDTO DTO查询实体
     * @return 分页数据 user list
     */
    IPage<UserVO> getUserList(UserDTO userDTO);

    /**
     * 根据ID获取用户表信息
     *
     * @param id 主键
     * @return 单条数据 user info
     */
    UserVO getUserInfo(Long id);

    /**
     * 修改用户表信息
     *
     * @param userDTO DTO实体对象
     */
    void updateUserInfo(UserDTO userDTO);

    /**
     * 新增用户表信息
     *
     * @param userDTO DTO实体对象
     */
    void saveUserInfo(UserDTO userDTO);

    /**
     * 删除用户表信息(可批量)
     *
     * @param idList 主键集合
     */
    void deleteUserByIdList(List<Long> idList);

}