package com.epic.demo.repo;

import com.epic.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User , String> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT id FROM USER ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String gerLastUid();

    @Query(value = "SELECT username FROM User WHERE username=?1")
    String username(String id);

    @Query(value = "SELECT email FROM User WHERE email=?1")
    String emailAddress(String email);



}
