package com.example.board.controller;

import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/userRegForm")
    public String userRegForm() {
        return "userRegForm";
    }

    @PostMapping("/userReg")
    public String userReg(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String password
    ) {
        userService.addUser(email, name, password);

        return "redirect:/welcome.html";
    }

    @GetMapping("/welcome.html")
    public String welcome() {
        return "welcome";
    }
}
