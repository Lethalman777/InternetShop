package com.example.InternetShop.controllers;

import com.example.InternetShop.enums.Role;
import com.example.InternetShop.models.User;
import com.example.InternetShop.services.UserService;
import com.example.InternetShop.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class RegisterController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public RegisterController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("userRole", Role.customer);
        model.addAttribute("workerRole", Role.worker);

        return "register";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            log.error(String.valueOf(bindingResult.getFieldError()));
            return "register";
        }

        userService.save(userForm);
        userService.login(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/home";
    }
}

