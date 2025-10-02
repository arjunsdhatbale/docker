package com.main.controller;

import com.main.entity.UserEntity;
import com.main.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity user){
        logger.info("Request receive to Save User.");
        UserEntity response = this.userService.saveUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        logger.info("Request receive to getAll users .");
        List<UserEntity> response = this.userService.getAllUsers();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}
