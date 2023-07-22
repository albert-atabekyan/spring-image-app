package com.atabekyan.image.service.dao;

import com.atabekyan.image.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
