package com.example.InternetShop.validators;

import com.example.InternetShop.models.User;
import com.example.InternetShop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.not_empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.not_empty");

        if (user.getUsername().length() < 4) {
            errors.rejectValue("username", "register.error.username.less_4");
        }
        if(user.getUsername().length() > 32){
            errors.rejectValue("username","register.error.username.over_32");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "register.error.duplicated.username");
        }

        if (userService.findByEmail(user.getEmail()) != null){
            errors.rejectValue("email", "register.error.duplicated.email");
        }

        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "register.error.password.less_8");
        }
        if (user.getPassword().length() > 32){
            errors.rejectValue("password", "register.error.password.over_32");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "register.error.diff_password");
        }

        if (user.getAge() <= 13){
            errors.rejectValue("age", "register.error.age_size");
        }
    }
}
