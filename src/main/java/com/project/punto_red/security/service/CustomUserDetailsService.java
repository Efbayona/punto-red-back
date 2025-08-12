package com.project.punto_red.security.service;

import com.project.punto_red.user.entity.User;
import com.project.punto_red.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("usuario no registrado"));
        return UserDetailsImpl.create(user.getUserName(), user.getPassword());
    }
}
