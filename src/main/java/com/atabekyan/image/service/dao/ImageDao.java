package com.atabekyan.image.service.dao;

import com.atabekyan.image.service.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDao extends JpaRepository<Image, Long> {
}
