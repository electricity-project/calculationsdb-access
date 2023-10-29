package com.electricity_project.calculationsdbaccess.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_table")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name ="name")
    private String name;
    @Column(name ="email")
    private String email;
    public UserEntity(){}
}
