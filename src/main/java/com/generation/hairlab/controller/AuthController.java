package com.generation.hairlab.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/** Controller REST dedicato all'autenticazione HairLab. */
@RestController
@RequestMapping("/hairlab/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/isalive")
    public ResponseEntity<Map<String, String>> getInfo() {
        return ResponseEntity.ok(
                Map.of(
                        "name", "hairlab",
                        "time", LocalDateTime.now().toString()));
    }

    @PatchMapping("/changepassword")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordDto dto) throws ServiceException {
        userService.changePassword(dto.getPassword());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto) throws ServiceException {
        return ResponseEntity.ok(userService.login(dto));
    }

    @GetMapping("/whoami")
    public ResponseEntity<Map<String, Object>> whoAmI(
            Authentication authentication) {

        List<String> roles = authentication
                .getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .toList();

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        Map.of(
                                "email", authentication.getName(),
                                "roles", roles));
    }
}
