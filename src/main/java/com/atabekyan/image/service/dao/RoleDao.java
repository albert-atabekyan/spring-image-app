package com.atabekyan.image.service.dao;

import com.atabekyan.image.service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
