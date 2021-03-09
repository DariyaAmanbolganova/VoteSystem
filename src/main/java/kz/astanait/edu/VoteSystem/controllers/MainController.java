package kz.astanait.edu.VoteSystem.controllers;

import kz.astanait.edu.VoteSystem.models.Role;
import kz.astanait.edu.VoteSystem.models.User;
import kz.astanait.edu.VoteSystem.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/reg")
    public String reg(Model model){
        model.addAttribute("user", new User());
        return "reg";
    }

    @GetMapping("/")
    public String main(Model model){
        return "profile";
    }

    @PostMapping("/reg")
    public void regUser(HttpServletResponse response, User user, Model model) throws IOException {
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        response.sendRedirect("/login");
    }
}
