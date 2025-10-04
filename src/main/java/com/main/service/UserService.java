package com.main.service;

import com.main.entity.UserEntity;
import com.main.repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public UserEntity saveUser(UserEntity user) {
        logger.info("Service request received to save user.");
        return this.userRepo.save(user);
    }

    public List<UserEntity> getAllUsers() {
        logger.info("Service request receive for get All users.");
        return this.userRepo.findAll();
    }

    public String deleteUserById(Long id) {
        logger.info("Service request received to delete user by id: {}.", id);
        Optional<UserEntity> user = this.userRepo.findById(id);
        if(!user.isPresent()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"user not found with id : " + id);
        }
        this.userRepo.deleteById(id);
        return "User Deleted successfully";
    }
}
