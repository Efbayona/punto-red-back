package com.project.punto_red.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
@Table(name = "user", schema = "main")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "user_name", nullable = false, unique = true, length = 50)
    private String userName;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    public User() {
    }
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static User create(String userName, String password) {
        return new User(userName, password);
    }
}
