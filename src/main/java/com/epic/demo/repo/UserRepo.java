package com.epic.demo.repo;

import com.epic.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User , String> {

    Optional<User> findByEmail(String email);



}
