package com.electricity_project.calculationsdbaccess.service.impl;

import com.electricity_project.calculationsdbaccess.entity.UserEntity;
import com.electricity_project.calculationsdbaccess.repository.UserRepository;
import com.electricity_project.calculationsdbaccess.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
