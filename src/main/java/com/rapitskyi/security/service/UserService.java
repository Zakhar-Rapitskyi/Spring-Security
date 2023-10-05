package com.rapitskyi.security.service;

import com.rapitskyi.security.entity.User;

public interface UserService {
    void save(User user);
    User findById(Integer id);
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
