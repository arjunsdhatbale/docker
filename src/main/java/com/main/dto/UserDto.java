package com.main.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String userName;
    private Email email;
    private String password;

}
