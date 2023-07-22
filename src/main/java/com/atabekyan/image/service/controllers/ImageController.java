package com.atabekyan.image.service.controllers;

import com.atabekyan.image.service.exception.StorageException;
import com.atabekyan.image.service.model.User;
import com.atabekyan.image.service.services.StorageService;
import com.atabekyan.image.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequestMapping(path = "api/v1/images")
public class ImageController {
    private final UserService userService;
    private final StorageService storageService;

    @Autowired
    public ImageController(StorageService storageService, UserService userService) {
        this.storageService = storageService;
        this.userService = userService;
    }

    @GetMapping("/add")
    public ResponseEntity<?> getImages() throws IOException {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(user.getImages());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            String filename = storageService.store(file);
            userService.addImage(user.getId(), filename);
        } catch (StorageException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Файл загружен");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteImg(@RequestParam("url") String url) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean flag = userService.deleteImage(user.getId(), url);
        if(flag) {
            storageService.deleteImage(url);
        }
        else System.out.println("Error");
        return ResponseEntity.ok("Файл удален.");
    }
}
