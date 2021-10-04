package com.epic.demo.service.impl;

import com.epic.demo.dto.UserDTO;
import com.epic.demo.entity.User;
import com.epic.demo.exception.ValidateException;
import com.epic.demo.repo.UserRepo;
import com.epic.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    ModelMapper mapper;

    @Override
    public boolean addUser(UserDTO user) {


        User map = mapper.map(user, User.class);
        userRepo.save(map);
        return true;
    }

    @Override
    public UserDTO validateUser(String username) {
        Optional<User> optionalUser = userRepo.findByUsername(username);
        System.out.println(optionalUser + " find method");
        if (optionalUser.isPresent()) {
            return mapper.map(optionalUser.get(), UserDTO.class);
        } else {
            throw new ValidateException("There is no optionalUser for this UserName");
        }
    }

    @Override
    public String getUid() {
        return userRepo.gerLastUid();
    }

    @Override
    public String getUserName(String username) {
        return userRepo.username(username);
    }

    @Override
    public String getEmail(String email) {
        return userRepo.emailAddress(email);
    }


}
