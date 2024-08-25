package com.marcosvn3.login_auth_api.repositories;

import com.marcosvn3.login_auth_api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Para declarar os repositories usando JPA basta ser uma interface
public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
}
