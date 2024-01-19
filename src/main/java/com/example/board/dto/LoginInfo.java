package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LoginInfo {

    private int userId;
    private String name;
    private String email;
    private List<String> roles;

    public LoginInfo(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
