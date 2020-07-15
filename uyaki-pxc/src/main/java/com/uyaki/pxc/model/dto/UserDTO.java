package com.uyaki.pxc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (User)DTO类
 *
 * @author uyaki
 * @since 2020 -06-15 10:51:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -41217888017227647L;

    private Long id;

    private String name;

    private Integer page;

    private Integer size;

}