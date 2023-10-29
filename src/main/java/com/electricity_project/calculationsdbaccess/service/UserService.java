package com.electricity_project.calculationsdbaccess.service;

import com.electricity_project.calculationsdbaccess.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAllUsers();
    UserEntity saveUser(UserEntity userEntity);
    void deleteUser(Long id);
}
