package com.example.board.service;

import com.example.board.dao.UserDao;
import com.example.board.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    @Transactional
    public User addUser(String email, String name, String password) {
        User user = userDao.addUser(email, name, password);
        userDao.mappingUserRole(user.getUserId());
        return user;
    }
}
