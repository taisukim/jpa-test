package com.example.jpatest.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name = "user_id", unique = true, nullable = false)
    private String id;

    @Column(name = "user_name", nullable = false)
    private String name;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_regno", unique = true, nullable = false)
    private String regNo;

    //사용자키
    @Column(name = "user_secret", nullable = false)
    private String secret;

    @Builder
    public User(String id, String name, String password, String regNo, String secret) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.regNo = regNo;
        this.secret = secret;
    }
}
