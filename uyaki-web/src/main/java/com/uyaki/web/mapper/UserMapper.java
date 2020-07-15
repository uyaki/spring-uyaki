package com.uyaki.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uyaki.web.entity.User;
import com.uyaki.web.model.dto.UserDTO;
import com.uyaki.web.model.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户表(User)表数据库访问层
 *
 * @author uyaki
 * @since 2020 -07-03 11:10:54
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据 Dto 条件分页获取用户表列表
     *
     * @param userDTO DTO查询实体
     * @param page    分页与排序信息
     * @return 分页数据 user list
     */
    IPage<UserVO> getUserList(@Param("condition") UserDTO userDTO, @Param("page") Page<User> page);
}