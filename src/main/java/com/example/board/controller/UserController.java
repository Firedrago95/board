package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/userRegForm")
    public String userRegForm() {
        return "userRegForm";
    }
}
