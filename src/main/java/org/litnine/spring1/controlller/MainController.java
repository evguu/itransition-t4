package org.litnine.spring1.controlller;

import org.litnine.spring1.domain.Role;
import org.litnine.spring1.domain.User;
import org.litnine.spring1.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println(username);
        return "index";
    }

    @GetMapping("/register")
    public String register(Map<String, Object> model) {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(String username, String password, String repeatPassword, Map<String, Object> model) {
        User userFromDb = userRepository.findByUsername(username);

        if (userFromDb != null) {
            model.put("message", "Username is already taken!");
            return "register";
        }

        if (!password.equals(repeatPassword))
        {
            model.put("message", "Passwords don't match." + password + ":::" + repeatPassword);
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
