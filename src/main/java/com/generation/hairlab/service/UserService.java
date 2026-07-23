package com.generation.hairlab.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.generation.hairlab.dto.LoginRequestDto;
import com.generation.hairlab.dto.LoginResponseDto;
import com.generation.hairlab.dto.UserDto;
import com.generation.hairlab.enums.Role;
import com.generation.hairlab.mapper.UserMapper;
import com.generation.hairlab.model.User;
import com.generation.hairlab.repository.UserRepository;
import com.generation.hairlab.security.JwtService;
import com.generation.hairlab.utility.PasswordService;

import lombok.RequiredArgsConstructor;

/** Service dedicato alla gestione degli utenti e dell'autenticazione. */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordService passwordService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public UserDto getCurrentUserDto() throws ServiceException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new ServiceException("Utente non autenticato", HttpStatus.UNAUTHORIZED);
        }
        User user = userRepo.findByEmail(auth.getName());
        if (user == null) {
            throw new ServiceException("Utente non trovato", HttpStatus.NOT_FOUND);
        }
        return userMapper.toDto(user);
    }

    public void changePassword(String password) throws ServiceException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new ServiceException("Utente non autenticato", HttpStatus.UNAUTHORIZED);
        }
        if (password == null || password.isBlank()) {
            throw new ServiceException("La nuova password è obbligatoria", HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findByEmail(auth.getName());
        if (user == null) {
            throw new ServiceException("Utente non trovato", HttpStatus.NOT_FOUND);
        }

        if (passwordService.matches(password, user.getPassword())) {
            throw new ServiceException("La nuova password deve essere diversa da quella attuale", HttpStatus.CONFLICT);
        }

        user.setPassword(passwordService.encode(password));
        userRepo.save(user);
    }

    public LoginResponseDto login(LoginRequestDto dto) throws ServiceException {
        if (dto == null || dto.getEmail() == null || dto.getEmail().isBlank() || 
            dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ServiceException("Email e password sono obbligatorie", HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findByEmail(dto.getEmail().trim().toLowerCase());
        if (user == null || !passwordService.matches(dto.getPassword(), user.getPassword())) {
            throw new ServiceException("Credenziali non valide", HttpStatus.UNAUTHORIZED);
        }

        LoginResponseDto response = new LoginResponseDto();
        response.setToken(jwtService.generateToken(user));
        return response;
    }

    public UserDto insert(UserDto dto) throws ServiceException {
        if (dto.getEmail() == null || dto.getEmail().isBlank() || 
            dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new ServiceException("Email e password sono obbligatorie", HttpStatus.BAD_REQUEST);
        }

        String normalizedEmail = dto.getEmail().trim().toLowerCase();
        if (userRepo.findByEmail(normalizedEmail) != null) {
            throw new ServiceException("Esiste già un utente con questa email", HttpStatus.CONFLICT);
        }

        try {
            User user = userMapper.toEntity(dto);
            user.setEmail(normalizedEmail);

            Role roleToAssign = Role.USER;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = auth != null && auth.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));

            if (isAdmin && dto.getRole() != null) {
                roleToAssign = dto.getRole();
            }

            user.setRole(roleToAssign);
            user.setPassword(passwordService.encode(user.getPassword()));

            return userMapper.toDto(userRepo.save(user));
        } catch (Exception e) {
            throw new ServiceException("Errore durante la creazione dell'utente", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        public List<UserDto> getAllUsers() throws ServiceException {
        try {
            List<User> users = userRepo.findAll();
            return users.stream()
                    .map(userMapper::toDto)
                    .toList();
        } catch (Exception e) {
            throw new ServiceException(
                "Errore durante il recupero della lista utenti",
                HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}