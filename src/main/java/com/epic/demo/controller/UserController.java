package com.epic.demo.controller;


import com.epic.demo.dto.UserDTO;
import com.epic.demo.exception.NotfoundException;
import com.epic.demo.service.UserService;
import com.epic.demo.util.StandradResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class UserController {

    @Autowired
    UserService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDetails(@RequestBody UserDTO dto) {
        if (dto.getId().trim().length()<=0){
            throw new NotfoundException("User ID Cannot Be Empty");
        }
        service.addUser(dto);
        return new ResponseEntity(new StandradResponse("201","Done",dto), HttpStatus.CREATED);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandradResponse> SearchUser(@RequestHeader("email") String email, @RequestHeader("password") String password) throws NotFoundException {
        System.out.println(email + " username login ");
        System.out.println(password + " password login ");
        if (email.trim().length() <= 0) {
            throw new NotFoundException("email cannot be empty");
        } else {
            UserDTO userDTO = service.validateUser(email, password);
            System.out.println(userDTO);
            return new ResponseEntity<>(new StandradResponse("200", "Validate User", userDTO), HttpStatus.CREATED);
        }
    }
}
