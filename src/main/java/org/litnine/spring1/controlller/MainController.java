package org.litnine.spring1.controlller;

import org.litnine.spring1.domain.Role;
import org.litnine.spring1.domain.User;
import org.litnine.spring1.domain.UserDto;
import org.litnine.spring1.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.*;
import javax.validation.valueextraction.ValueExtractor;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();*/
        model.put("users", userRepository.findAll());
        return "index";
    }

    @GetMapping("/register")
    public String register(Map<String, Object> model) {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(UserDto userDto, Map<String, Object> model) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);

        if(violations.size() > 0){
            model.put("message", "Either some field is not filled or email format is wrong.");
            return "register";
        }

        User userFromDb = userRepository.findByUsername(userDto.getUsername());

        if (userFromDb != null) {
            model.put("message", "Username is already taken!");
            return "register";
        }

        if (!userDto.getPassword().equals(userDto.getRepeatPassword()))
        {
            model.put("message", "Passwords don't match.");
            return "register";
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setActive(true);
        user.setRegistrationDate(new Date());
        user.setLastLoginDate(new Date());
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
