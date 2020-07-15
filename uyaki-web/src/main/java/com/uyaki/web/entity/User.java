package com.uyaki.web.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

/**
 * 用户表(User)表实体类
 *
 * @author uyaki
 * @since 2020 -07-03 11:10:55
 */
@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 836661282948829742L;

    /**
     * 编号
     */
    @TableId(type = ASSIGN_ID)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdAt;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedAt;

    /**
     * 状态。0：禁用；1：正常；
     */
    private Boolean isEnable;

    /**
     * 是否已删除。0：未删除；1：已删除；
     */
    private Boolean isDeleted;


}