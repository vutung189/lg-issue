package com.lg.service;

import com.lg.model.User;
import com.lg.repository.RoleRepository;
import com.lg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

//@Service
//public class UserServiceImpl implements UserService {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Override
//    public void save(User user) {
//
//    }
//
//    @Autowired
////    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
////    @Override
////    public void save(User user) {
////        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
////        user.setRoles(new HashSet<>(roleRepository.findAll()));
////        userRepository.save(user);
////    }
//
//    @Override
//    public User findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//}
