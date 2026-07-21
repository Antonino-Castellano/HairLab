package com.generation.hairlab.dto;

import java.time.LocalDate;

import com.generation.hairlab.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String firstName, lastName, address;
    private String email;
    private String password;
    private LocalDate dob;
    private Role role;
}
