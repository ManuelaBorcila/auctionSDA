package com.sda.auction.service.impl;

import com.sda.auction.dto.UserDto;
import com.sda.auction.mapper.UserMapper;
import com.sda.auction.model.User;
import com.sda.auction.repository.UserRepository;
import com.sda.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        //convertin DTO in Entity
        User user = userMapper.convert(userDto);

       encodePassword(user);

        //persistam in baza de date
        User savedUser = userRepository.save(user);

        //convertim entitatea persistata inapoi in DTO pt a o intoarce catre requester
        return userMapper.convert(savedUser);

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private void encodePassword(User user) {
        //encode user's password and put it in passwordEncored variable
        String passwordEncored = passwordEncoder.encode(user.getPassword());
        //set the
        user.setPassword(passwordEncored);
    }
}
