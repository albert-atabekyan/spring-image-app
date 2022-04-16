package com.project.repo.pro.dao;

import com.project.repo.pro.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {
}
