package com.generation.hairlab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.generation.hairlab.model.User;

public interface UserRepository extends JpaRepository <User, Integer> {
    public User findByEmail(String email);
}
