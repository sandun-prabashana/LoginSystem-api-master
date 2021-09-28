package com.epic.demo.service;

import com.epic.demo.dto.UserDTO;

public interface UserService {

    boolean addUser(UserDTO user);
    UserDTO validateUser(String email, String password);


}
