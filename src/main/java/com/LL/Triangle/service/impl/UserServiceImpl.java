package com.LL.Triangle.service.impl;

import com.LL.Triangle.pojo.User;
import com.LL.Triangle.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements IUserService {
    private Map<String,User> userMap = new ConcurrentHashMap<>();

    @Override
    public boolean userSignIn(User user) {
        return false;
    }

    @Override
    public void userSignOut(String sessionId) {

    }

    @Override
    public boolean checkLogin(String sessionId) {
        return false;
    }
}
