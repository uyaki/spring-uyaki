package com.uyaki.web.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表(User)DTO类
 *
 * @author uyaki
 * @since 2020 -07-03 11:10:56
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("用户表 DTO")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -93932974931788391L;

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "创建时间")
    private Date createdAt;

    @ApiModelProperty(value = "更新时间")
    private Date updatedAt;

    @ApiModelProperty(value = "状态。0：禁用；1：正常；")
    private Boolean isEnable;

    @ApiModelProperty(value = "是否已删除。0：未删除；1：已删除；")
    private Boolean isDeleted;


    @ApiModelProperty(value = "页数")
    private Integer page;

    @ApiModelProperty(value = "每页大小")
    private Integer size;
}