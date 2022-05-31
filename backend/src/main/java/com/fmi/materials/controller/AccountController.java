package com.fmi.materials.controller;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AccountController {

    private UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid UserDtoRegistration userDtoRegistration) {
        return new ResponseEntity<UserDto>(
                this.userService.createUser(userDtoRegistration),
                HttpStatus.CREATED);
    }

    @PostMapping("authenticate")
    public ResponseEntity<UserDto> authenticate(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<UserDto>(
                this.userService.authenticateUser(userDto),
                HttpStatus.OK);
    }
}
