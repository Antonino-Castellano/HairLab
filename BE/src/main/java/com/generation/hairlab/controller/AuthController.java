package com.generation.hairlab.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.hairlab.dto.ChangePasswordDto;
import com.generation.hairlab.dto.LoginRequestDto;
import com.generation.hairlab.dto.LoginResponseDto;
import com.generation.hairlab.service.ServiceException;
import com.generation.hairlab.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/hairlab/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // dichiaro la dipendenza come interfaccia ma mi darÃ  UserServiceImpl

    private final UserService userService;

    @GetMapping("/isalive")
    public Map<String,String> getInfo(){
        Map<String,String> res = new HashMap<String,String>();
        
        res.put("name", "jd");
        res.put("time", LocalDateTime.now().toString());

        return res;

    }

    @PatchMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto dto){
        try{
            userService.changePassword(dto.getPassword());
            return ResponseEntity.status(200).build();
        }
        catch(ServiceException e){
            return ResponseEntity.status(400).body(e.toMap("Change password failed"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto){
        try{
            LoginResponseDto res = userService.login(dto);
            return ResponseEntity.ok(res);
        }
        catch(ServiceException e){
            return ResponseEntity.status(403).body(e.toMap("Bad login"));
        }

    }

    @GetMapping("/whoami")
    public Map<String,String> isLogged(){

        String currentUsername = SecurityContextHolder.getContext()
                                                      .getAuthentication()
                                                      .getName();

        Map<String,String> res = new HashMap<String,String>();


        return res;


    }
}