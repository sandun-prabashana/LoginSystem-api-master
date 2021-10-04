package com.epic.demo.controller;


import com.epic.demo.dto.UserDTO;
import com.epic.demo.exception.NotfoundException;
import com.epic.demo.security.PasswordConveter;
import com.epic.demo.service.UserService;
import com.epic.demo.util.StandradResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.lang.Integer.parseInt;


@RestController
@RequestMapping("/login")
@CrossOrigin
public class UserController {

    @Autowired
    UserService service;

    private PasswordConveter conveter = new PasswordConveter();

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDetails( @RequestBody UserDTO dto) throws Exception {

        String encdata = conveter.convertPassword(dto.password);
        dto.setPassword(encdata);

        String id=service.getUid();
        String request_id = "U001";
        try {

            String last_uid = id;
            int newId = parseInt(last_uid.substring(1, 4)) + 1;
            if (newId < 10) {
                request_id = "U00" + newId;
                dto.setId(request_id);
            } else if (newId < 100) {
                request_id = "U0" + newId;
                dto.setId(request_id);
            } else {
                request_id = "U" + newId;
                dto.setId(request_id);
            }

        } catch (Exception e) {
            request_id = "U001";
            dto.setId(request_id);
        }

        String username = service.getUserName(dto.username);
        String emailAddress = service.getEmail(dto.email);

        if (username==null){
            if (emailAddress==null){
                service.addUser(dto);
            }else {
                throw new NotFoundException("This Email Already Take");
            }
        }else {
            throw new NotFoundException("User Name Already Taken");
        }




        return new ResponseEntity(new StandradResponse("201","Done",dto), HttpStatus.CREATED);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandradResponse> SearchUser(@RequestHeader("email") String email) throws Exception {
        System.out.println(email + " username login ");
        if (email.trim().length() <= 0) {
            throw new NotFoundException("email cannot be empty");
        } else {
            UserDTO userDTO = service.validateUser(email);
            String encdata = conveter.decryptPassword(userDTO.password);
            userDTO.setPassword(encdata);
            return new ResponseEntity<>(new StandradResponse("200", "Validate User", userDTO), HttpStatus.CREATED);
        }
    }
}
