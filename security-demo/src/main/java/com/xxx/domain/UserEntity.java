package com.xxx.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @Description: 用户实体类
 * @Author: Jimmy
 */
@Data
@Entity
@Table(name = "tb_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

}
