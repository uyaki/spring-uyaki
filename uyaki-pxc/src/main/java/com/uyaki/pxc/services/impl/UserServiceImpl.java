package com.uyaki.pxc.services.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uyaki.pxc.entity.User;
import com.uyaki.pxc.mapper.UserMapper;
import com.uyaki.pxc.model.dto.UserDTO;
import com.uyaki.pxc.model.vo.UserVO;
import com.uyaki.pxc.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author uyaki
 * @since 2020 -06-15 10:51:44
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 根据 Dto 条件分页获取列表
     *
     * @param userDTO DTO查询实体
     * @return 分页数据
     */
    @Override
    public IPage<UserVO> getUserList(UserDTO userDTO) {
        Page<User> page = new Page<>(userDTO.getPage(), userDTO.getSize());
        page.setOrders(OrderItem.descs("created_at"));
        return userMapper.getUserList(userDTO, page).convert(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        });
    }

    /**
     * 根据ID获取信息
     *
     * @param id 主键
     * @return 单条数据
     */
    @Override
    public UserVO getUserInfo(Long id) {
        User user = userMapper.selectById(id);
        if (user != null) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }
        return null;
    }

    /**
     * 修改信息
     *
     * @param userDTO DTO实体对象
     */
    @Override
    public void updateUserInfo(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userMapper.updateById(user);
    }

    /**
     * 新增信息
     *
     * @param userDTO DTO实体对象
     */
    @Override
    public void saveUserInfo(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userMapper.insert(user);
    }

    /**
     * 根据Id列表删除信息(可批量)
     *
     * @param idList 主键集合
     */
    @Override
    public void deleteUserByIdList(List<Long> idList) {
        userMapper.deleteBatchIds(idList);
    }
}