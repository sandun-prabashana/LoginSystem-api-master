package com.epic.demo.controller;

import com.epic.demo.dto.UserDTO;
import com.epic.demo.service.UserService;
import com.epic.demo.util.JwtUtil;
import com.epic.demo.util.StandradResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * @author Sandun Prabashana <sandunprabashana@gmail.com> (prabashana.tk/)
 * @since 10/11/2021
 */


@RestController
@RequestMapping("/token")
@CrossOrigin
public class DecodedTokenController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UserService service;

    public String sub;
    public String name;
    public Boolean admin;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StandradResponse> getDecoded(@RequestHeader("auth") String auth) throws UnsupportedEncodingException {

        String token=null;
        String username=null;

        System.out.println(auth);
        token = auth.substring(5);
        System.out.println(token);
        username=jwtUtil.extractUsername(token);
        System.out.println(username);

        UserDTO dto = service.getUserDetails(username);
        System.out.println(dto.username);
        System.out.println(dto.email);

        return new ResponseEntity<>(new StandradResponse("200", "Validate User", dto), HttpStatus.CREATED);

    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
