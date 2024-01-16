package com.example.board.controller;

import com.example.board.dto.LoginInfo;
import com.example.board.dto.User;
import com.example.board.service.UserService;
import jakarta.servlet.http.HttpSession;
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
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        userService.addUser(name, email, password);

        return "redirect:/welcome.html";
    }

    @GetMapping("/welcome.html")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/loginform")
    public String loginForm(){
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession httpSession
    ){
        try {
            User user = userService.getUser(email);
            if (user.getPassword().equals(password)) {
                System.out.println("암호가 같습니다.");
                LoginInfo loginInfo = new LoginInfo(user.getUserId(), user.getName(), user.getEmail());
                httpSession.setAttribute("loginInfo", loginInfo);
            } else {
                throw new RuntimeException("암호가 일치하지 않음.");
            }
        } catch (Exception e) {
            return "redirect:/loginform?error=true";
        }
        return "redirect:/";
    }
}
