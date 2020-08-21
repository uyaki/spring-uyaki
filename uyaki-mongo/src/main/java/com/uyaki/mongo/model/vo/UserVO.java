package com.uyaki.mongo.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)VO类
 *
 * @since 2020-07-17 10:39:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("VO")
public class UserVO implements Serializable {

    private static final long serialVersionUID = -61883724608926488L;

    private String id;

    private Integer age;

    private Date createdAt;

    private String name;


}