package com.example.InternetShop.services.impl;

import com.example.InternetShop.models.User;
import com.example.InternetShop.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username);

        if (user != null) {
            Set<GrantedAuthority> authorities = new HashSet<>();
            if (Objects.equals(username, "admin")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }else {
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            log.info(String.format("User with name: %s and password: %s created.", user.getUsername(), user.getPassword()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }else{
            throw new UsernameNotFoundException("User " + username + " not found!");
        }
    }


}