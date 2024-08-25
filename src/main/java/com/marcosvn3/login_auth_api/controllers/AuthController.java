package com.marcosvn3.login_auth_api.controllers;

import com.marcosvn3.login_auth_api.domain.user.User;
import com.marcosvn3.login_auth_api.dto.LoginRequestDTO;
import com.marcosvn3.login_auth_api.dto.RegisterRequestDTO;
import com.marcosvn3.login_auth_api.dto.ResponseDTO;
import com.marcosvn3.login_auth_api.infra.security.TokenService;
import com.marcosvn3.login_auth_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){

        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuario não encontrado!"));

        if(!passwordEncoder.matches(user.getPassword(), body.password())){
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(),token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/Register")
    public ResponseEntity Register(@RequestBody RegisterRequestDTO body){

        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setUsername(body.email());

            this.repository.save(newUser);
                String token = tokenService.generateToken(newUser);
                return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(),token));
        }

        return ResponseEntity.badRequest().build();
    }


}
