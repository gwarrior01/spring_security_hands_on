package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    private String username;
    private String password;
}
