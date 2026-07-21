package com.generation.hairlab.service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.LoginRequestDto;
import com.generation.hairlab.dto.LoginResponseDto;
import com.generation.hairlab.dto.UserDto;
import com.generation.hairlab.mapper.UserMapper;
import com.generation.hairlab.model.User;
import com.generation.hairlab.repository.UserRepository;
import com.generation.hairlab.security.JwtService;
import com.generation.hairlab.utility.PasswordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService{ 

    private final UserRepository userRepo;
    private final PasswordService passwordService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void changePassword(String password) throws ServiceException {

        // email dell'utente loggato perchÃ© abbiamo usato come subject (sub) l'email

        String email = SecurityContextHolder.getContext()
                       .getAuthentication()
                       .getName();
        
        User user = userRepo.findByEmail(email);

        if(user==null)
            throw new ServiceException("User with email "+email+" not found");

        String hashed = passwordService.encode(password);

        if(hashed.equals(user.getPassword()))
            throw new ServiceException("Same hash. Cannot update");

        user.setPassword(hashed);

        userRepo.save(user);
   
    }

    public LoginResponseDto login(LoginRequestDto dto) throws ServiceException {
        
        User user = userRepo.findByEmail(dto.getEmail());
        if(user==null)
            throw new ServiceException("Invalid credentials");

        if(!passwordService.matches(dto.getPassword(), user.getPassword()))
            throw new ServiceException("Invalid credentials");

        String token = jwtService.generateToken(user);

        LoginResponseDto res = new LoginResponseDto();
        res.setToken(token);

        return res;
    }

    public UserDto insert(UserDto dto) throws ServiceException{
        try{
            User user = userMapper.toEntity(dto);
            userRepo.save(user);
            return userMapper.toDto(user);
        }
        catch(Exception e){
            throw new ServiceException(e.getMessage());
        }

    }

}