package com.generation.hairlab.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.UserDto;
import com.generation.hairlab.service.ServiceException;
import com.generation.hairlab.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/hairlab/api/users")
@CrossOrigin(origins = "http://localhost:4200") 
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping
    
    public ResponseEntity<?> insert(@RequestBody UserDto userDto) {
        try{
            userDto = userService.insert(userDto);
            return ResponseEntity.ok(userDto);
        }
        catch(ServiceException e){
            return ResponseEntity.badRequest().body(e.toMap("Error saving"));
        }
    }
}