package com.main.service;

import com.main.dto.UserDto;
import com.main.entity.UserEntity;
import com.main.repo.UserRepo;
import com.main.utils.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepo userRepo;
    private final GenericMapper genericMapper;

    public UserService(UserRepo userRepo, GenericMapper genericMapper){
        this.userRepo = userRepo;
        this.genericMapper = genericMapper;
    }
    public UserEntity saveUser(UserEntity user) {
        logger.info("Service request received to save user.");
        UserEntity savedUser = this.userRepo.save(user);
        return savedUser;
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

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity existingUser = this.userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        GenericMapper.copyNonNullProperties(userDto, existingUser);
        UserEntity updatedUser = userRepo.save(existingUser);
        return genericMapper.map(updatedUser,UserDto.class);
    }

    public UserEntity getUser(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found by id: " + id));
    }


}
