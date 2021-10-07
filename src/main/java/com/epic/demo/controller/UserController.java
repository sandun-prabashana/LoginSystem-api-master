package com.epic.demo.controller;

/**
 * @author Sandun Prabashana <sandunprabashana@gmail.com> (prabashana.tk/)
 * @since 10/4/2021
 */

import com.epic.demo.dto.UserDTO;
import com.epic.demo.exception.NotfoundException;
import com.epic.demo.security.PasswordConveter;
import com.epic.demo.service.UserService;
import com.epic.demo.util.JwtUtil;
import com.epic.demo.util.StandradResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    private PasswordConveter conveter = new PasswordConveter();


    private String validateData(UserDTO dto) {
        if (dto.getUsername().trim().length() <= 0) {
            return "User User_Name is missing";
        } else if (dto.getEmail().trim().length() <= 0) {
            return "User Email is missing";
        } else if (dto.getPassword().trim().length() <= 0) {
            return "User Password is missing";
        } else {
            return "true";
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getDetails( @RequestBody UserDTO dto) throws Exception {

        String validate = validateData(dto);
        if (validate.equals("true")) {

//            Password encrypt
            String encdata = conveter.convertPassword(dto.password);
            dto.setPassword(encdata);

//            Create User ID
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

//            Check UserName
            if (username==null){

//                Check EmailAddress
                if (emailAddress==null){

//                    Save User
                    service.addUser(dto);
                }else {
                    throw new NotFoundException("This Email Already Take");
                }
            }else {
                throw new NotFoundException("User Name Already Taken");
            }

            return new ResponseEntity(new StandradResponse("201","Success",dto), HttpStatus.CREATED);

        } else {
            throw new NotFoundException(validate);
        }




    }

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<StandradResponse> SearchUser(@RequestHeader("username") String username) throws Exception {
//        System.out.println(username + " username login ");
//        if (username.trim().length() <= 0) {
//            throw new NotFoundException("username cannot be empty");
//        } else {
//            UserDTO userDTO = service.validateUser(username);
//            String encdata = conveter.decryptPassword(userDTO.password);
//            userDTO.setPassword(encdata);
//            return new ResponseEntity<>(new StandradResponse("200", "Validate User", userDTO), HttpStatus.CREATED);
//        }
//    }

    @GetMapping("/a")
    public String welcome() {
        return "Welcome to Doom";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandradResponse> generateToken(@RequestHeader("username") String username, @RequestHeader("password") String password) throws Exception {
        UserDTO dto = new UserDTO();
        dto.setUsername(username);


        System.out.println(password);
        String encdata = conveter.convertPassword(password);
        System.out.println(encdata);
        dto.setPassword(encdata);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                    encdata));
        }catch (Exception ex){
            throw new Exception("invalid username/password");
        }
        String token= jwtUtil.generateToken(username);
                    return new ResponseEntity<>(new StandradResponse("200", "Validate User", token), HttpStatus.CREATED);

    }
}
