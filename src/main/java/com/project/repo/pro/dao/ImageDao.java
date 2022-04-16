package com.project.repo.pro.dao;

import com.project.repo.pro.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDao extends JpaRepository<Image, Long> {
}
