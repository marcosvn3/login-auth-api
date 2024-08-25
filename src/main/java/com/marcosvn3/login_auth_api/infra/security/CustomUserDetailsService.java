package com.marcosvn3.login_auth_api.infra.security;

import com.marcosvn3.login_auth_api.domain.user.User;
import com.marcosvn3.login_auth_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;


public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado!"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), new ArrayList<>());



    }
}
