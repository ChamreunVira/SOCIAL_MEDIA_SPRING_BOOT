package com.kh.chamreunvira.springboot.security;

import com.kh.chamreunvira.springboot.model.User;
import com.kh.chamreunvira.springboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(() ->  new UsernameNotFoundException("Email not found with email: " + email));

        return new org.springframework.security.core.userdetails
                .User(user.getUsername() , user.getPassword() , user.getAuthorities());
    }
}
