package com.uyaki.pxc.services;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.uyaki.pxc.model.dto.UserDTO;
import com.uyaki.pxc.model.vo.UserVO;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author uyaki
 * @since 2020 -06-15 10:51:43
 */
public interface UserService {

    /**
     * 根据 Dto 条件分页获取列表
     *
     * @param userDTO DTO查询实体
     * @return 分页数据 user list
     */
    IPage<UserVO> getUserList(UserDTO userDTO);

    /**
     * 根据ID获取信息
     *
     * @param id 主键
     * @return 单条数据 user info
     */
    UserVO getUserInfo(Long id);

    /**
     * 修改信息
     *
     * @param userDTO DTO实体对象
     */
    void updateUserInfo(UserDTO userDTO);

    /**
     * 新增信息
     *
     * @param userDTO DTO实体对象
     */
    void saveUserInfo(UserDTO userDTO);

    /**
     * 删除信息(可批量)
     *
     * @param idList 主键集合
     */
    void deleteUserByIdList(List<Long> idList);

}