package com.uyaki.mongo.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)DTO类
 *
 * @author gknoone
 * @since 2020-07-17 10:39:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(" DTO")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -14387492819095790L;

    private String id;

    private Integer age;

    private Date createdAt;

    private String name;

}