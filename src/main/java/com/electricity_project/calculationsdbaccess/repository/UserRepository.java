package com.electricity_project.calculationsdbaccess.repository;


import com.electricity_project.calculationsdbaccess.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
