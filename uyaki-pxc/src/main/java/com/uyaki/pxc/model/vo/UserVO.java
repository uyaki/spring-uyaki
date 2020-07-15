package com.uyaki.pxc.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (User)VO类
 *
 * @author uyaki
 * @since 2020 -06-15 10:51:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = 955374047852235227L;

    private Long id;

    private String name;

}