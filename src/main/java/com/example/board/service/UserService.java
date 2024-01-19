package com.example.board.service;

import com.example.board.dao.UserDao;
import com.example.board.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    @Transactional
    public User addUser(String name, String email, String password) {
        User user1 = userDao.getUser(email);
        if (user1 != null) {throw new RuntimeException("가입된 이메일이 있습니다.");}

        User user = userDao.addUser(name, email, password);
        userDao.mappingUserRole(user.getUserId());
        return user;
    }

    @Transactional
    public User getUser(String email) {
        User user = userDao.getUser(email);
        return user;
    }

    @Transactional(readOnly = true)
    public List<String> getRoles(int userId) {
        return userDao.getRoles(userId);
    }
}
