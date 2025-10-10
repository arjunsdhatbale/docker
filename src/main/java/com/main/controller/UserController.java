package com.main.controller;

import com.main.dto.UserDto;
import com.main.entity.UserEntity;
import com.main.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity user){
        logger.info("Request receive to Save User.");
        UserEntity response = this.userService.saveUser(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto){
        logger.info("Request received to update user by id: {}.", id);
        UserDto response = userService.updateUser(id,userDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers(){
        logger.info("Request receive to getAll users .");
        List<UserEntity> response = this.userService.getAllUsers();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long id){
        logger.info("Request received to get user by id: {}.", id);
        UserEntity user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Request received to delete user by userid: {}.", id);
        try {
            userService.deleteUserById(id);
            logger.info("User with id {} deleted successfully.", id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            logger.error("User with id {} not found.", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting user with id {}: {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
