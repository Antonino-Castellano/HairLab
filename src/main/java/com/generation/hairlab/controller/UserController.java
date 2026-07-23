package com.generation.hairlab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.generation.hairlab.dto.UserDto;
import com.generation.hairlab.service.ServiceException;
import com.generation.hairlab.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato alla gestione utenti. */
@RestController
@RequestMapping("/hairlab/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser() throws ServiceException {
        return ResponseEntity.ok(userService.getCurrentUserDto());
    }

    @PostMapping
    public ResponseEntity<UserDto> insert(@Valid @RequestBody UserDto userDto) throws ServiceException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insert(userDto));
    }
}