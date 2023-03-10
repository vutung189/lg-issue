package com.lg.service;

import com.lg.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
