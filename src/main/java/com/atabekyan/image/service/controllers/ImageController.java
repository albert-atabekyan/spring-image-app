package com.atabekyan.image.service.controllers;

import com.atabekyan.image.service.exception.StorageException;
import com.atabekyan.image.service.model.Image;
import com.atabekyan.image.service.model.User;
import com.atabekyan.image.service.services.ImageService;
import com.atabekyan.image.service.services.StorageService;
import com.atabekyan.image.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
@RequestMapping(path = "api/v1/image/")
public class ImageController {
    private final UserService userService;
    private final StorageService storageService;
    private final ImageService imageService;


    @Autowired
    public ImageController(StorageService storageService,
                           UserService userService,
                           ImageService imageService) {
        this.storageService = storageService;
        this.userService = userService;
        this.imageService = imageService;
    }

    @GetMapping("{image_id}")
    public ResponseEntity<?> getImage(@PathVariable long image_id) {
        User user = getAthenticatedUser();

        Image image = getImage(image_id, user);
        
        if(Objects.isNull(image)) 
            return imageNotFound();
        
        File file = getImageFile(image);

        try {
            return getHttpImage(getImageResource(file), image);
        } catch (IOException e) {
            return imageNotFound();
        }
    }

    private static ResponseEntity<Object> imageNotFound() {
        return ResponseEntity
                .notFound()
                .build();
    }

    private ResponseEntity<InputStreamResource> getHttpImage(InputStreamResource imageResource,
                                                             Image image) throws IOException {

        HttpHeaders imageContentTypeHeader = new HttpHeaders();
        imageContentTypeHeader.setContentType(getMediaType(image));

        return ResponseEntity
                .ok()
                .headers(imageContentTypeHeader)
                .body(imageResource);
    }

    private MediaType getMediaType(Image image) throws IOException {
        return MediaType.valueOf(getContentTypeString(image));
    }

    private String getContentTypeString(Image image) throws IOException {
        return Files.probeContentType(getAbsoluteImagePath(image));
    }

    private File getImageFile(Image image) {
        Path path = getAbsoluteImagePath(image);
        return path.toFile();
    }
    private static InputStreamResource getImageResource(File file) throws FileNotFoundException {
        return new InputStreamResource(new FileInputStream(file));
    }

    private Path getAbsoluteImagePath(Image image) {
        String absoluteImagePath = storageService
                .getRootLocation()
                + File.separator
                + image.getName();

        return Paths.get(absoluteImagePath);
    }
    @Nullable
    private static Image getImage(long image_id, User user) {
        for (Image image : user.getImages()) {
            if(image.getId() == image_id) {
                return image;
            }
        }

        return null;
    }
    @GetMapping("{image_id}/info")
    public ResponseEntity<?> getImageInfo(@PathVariable long image_id) {
        User user = getAthenticatedUser();

        Image image = getImage(image_id, user);

        if(Objects.isNull(image))
            return imageNotFound();

        return ResponseEntity.ok().body(image);
    }

    @DeleteMapping("{image_id}")
    public ResponseEntity<?> deleteImage(@PathVariable long image_id) {
        User user = getAthenticatedUser();

        String filename;
        
        try {
            filename = userService.deleteImage(user.getId(), image_id);
        } catch (Exception e) {
            return imageNotFound();
        }

        storageService.deleteFile(filename);
        return fileDeleted();
    }

    private static ResponseEntity<String> fileDeleted() {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("add")
    public ResponseEntity<?> addImage(@RequestParam("file") MultipartFile file) {
        User user = getAthenticatedUser();

        try {
            String filename = storageService.store(file);
            userService.addImage(user.getId(), filename);
        } catch (StorageException e) {
            return ResponseEntity.badRequest().build();
        }

        return fileUploaded();
    }

    @PostMapping("{image_id}/updateTitle")
    public ResponseEntity<?> updateTitle(@PathVariable long image_id,
                                         @RequestParam String title) {
        System.out.println("title from update title " + title);
        imageService.updateImageTitle(image_id, title);

        return ResponseEntity.ok().build();
    }

    private static ResponseEntity<String> fileUploaded() {
        return ResponseEntity.noContent().build();
    }

    private User getAthenticatedUser() {
         UserDetails userDetails =
                 (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

         return (User) userService.loadUserByUsername(userDetails.getUsername());
    }
}
