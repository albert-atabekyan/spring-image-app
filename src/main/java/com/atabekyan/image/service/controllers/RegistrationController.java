package com.atabekyan.image.service.controllers;

import com.atabekyan.image.service.model.User;
import com.atabekyan.image.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String addUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        if (!userService.saveUser(user)){
            return "registration";
        }

        return "redirect:/";
    }

    @DeleteMapping
    public String deleteUser(@RequestBody Long id) {
        if (!userService.deleteUser(id)){
            return "registration";
        }

        return "redirect:/";
    }
}
