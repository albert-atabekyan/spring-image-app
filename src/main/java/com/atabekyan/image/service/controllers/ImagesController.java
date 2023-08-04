package com.atabekyan.image.service.controllers;

import com.atabekyan.image.service.model.User;
import com.atabekyan.image.service.services.StorageService;
import com.atabekyan.image.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping(path = "api/v1/images")
public class ImagesController {
    @GetMapping()
    public ResponseEntity<?> getImages() {
        User user = getAthenticatedUser();

        ArrayList<Long> idArray = new ArrayList<>();

        user.getImages().forEach(elem -> {
            idArray.add(elem.getId());
        });

        return ResponseEntity.ok().body(idArray);
    }

    private static User getAthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
