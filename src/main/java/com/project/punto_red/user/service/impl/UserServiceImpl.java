package com.project.punto_red.user.service.impl;

import com.project.punto_red.user.repository.UserRepository;
import com.project.punto_red.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
