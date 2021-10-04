package com.epic.demo.service;

import com.epic.demo.dto.UserDTO;

public interface UserService {

    boolean addUser(UserDTO user);
    UserDTO validateUser(String username);
    String getUid();
    String getUserName(String username);
    String getEmail(String email);


}
