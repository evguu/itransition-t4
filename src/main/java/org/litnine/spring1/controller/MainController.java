package org.litnine.spring1.controller;

import org.litnine.spring1.domain.IndexArrDto;
import org.litnine.spring1.domain.Role;
import org.litnine.spring1.domain.User;
import org.litnine.spring1.domain.UserDto;
import org.litnine.spring1.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.*;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        model.put("suicide", isCurrentUserBannedOrDeleted());
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

        if (violations.size() > 0) {
            model.put("message", "Either some field is not filled or email format is wrong.");
            return "register";
        }

        User userFromDb = userRepository.findByUsername(userDto.getUsername());

        if (userFromDb != null) {
            model.put("message", "Username is already taken!");
            return "register";
        }

        if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
            model.put("message", "Passwords don't match.");
            return "register";
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        user.setEmail(userDto.getEmail());
        user.setActive(true);
        user.setRegistrationDate(new Date());
        user.setLastLoginDate(new Date());
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String getLogout(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.setAuthenticated(false);
        return "redirect:/login";
    }

    private boolean isCurrentUserBannedOrDeleted(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userRepository.findByUsername(username);
        if(user == null) return true;
        return !user.getActive();
    }

    @PostMapping("/command")
    public ResponseEntity<?> command(IndexArrDto indexArrDto, Map<String, Object> model) {

        if(isCurrentUserBannedOrDeleted()){
            return ResponseEntity.ok("{\"isLoggedOut\": true}");
        }

        if(indexArrDto.getIndexes() != null) {
            for(Long id: indexArrDto.getIndexes()) {
                Optional<User> ou = userRepository.findById(id);
                if(ou.isPresent()) {
                    User u = ou.get();
                    switch (indexArrDto.getAction()) {
                        case ("unblock"):
                            u.setActive(true);
                            userRepository.save(u);
                            break;
                        case ("block"):
                            u.setActive(false);
                            userRepository.save(u);
                            break;
                        case ("delete"):
                            userRepository.deleteById(u.getId());
                            break;
                        default:
                            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                }
            }
        }
        return ResponseEntity.ok("{\"isLoggedOut\": " +
                (isCurrentUserBannedOrDeleted()?"true":"false")
                + "}");
    }
}
