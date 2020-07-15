package com.uyaki.pxc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uyaki.pxc.entity.User;
import com.uyaki.pxc.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (User)表数据库访问层
 *
 * @author uyaki
 * @since 2020 -06-15 10:51:45
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据 Dto 条件分页获取列表
     *
     * @param userDTO DTO查询实体
     * @param page    分页与排序信息
     * @return 分页数据 user list
     */
    IPage<User> getUserList(@Param("condition") UserDTO userDTO, @Param("page") Page<User> page);

}