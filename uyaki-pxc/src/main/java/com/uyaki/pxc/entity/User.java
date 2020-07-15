package com.uyaki.pxc.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

/**
 * (User)表实体类
 *
 * @author uyaki
 * @since 2020 -06-15 10:51:46
 */
@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 518614174049490990L;

    @TableId(type = ASSIGN_ID)
    private Long id;

    private String name;


}