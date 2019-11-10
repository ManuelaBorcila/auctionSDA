package com.sda.auction.controller;

import com.sda.auction.dto.UserDto;
import com.sda.auction.model.User;
import com.sda.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/register")
public class Registration {

    @Autowired
    UserService userService;
    @GetMapping
    public ResponseEntity<String> get() {
        return new ResponseEntity("heloo world", HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> post(@Valid @RequestBody UserDto userDto) {
        if(emailAlreadyRegistred(userDto.getEmail())) {
           // return error;
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        UserDto userDtoResult = userService.addUser(userDto);
        return new ResponseEntity<>(userDtoResult, HttpStatus.OK);
    }

    private boolean emailAlreadyRegistred(String email) {
        User user = userService.findByEmail(email);
        return user != null;
    }
}
