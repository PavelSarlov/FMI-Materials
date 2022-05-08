package com.fmi.materials.controller;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController("fmi-materials/")
public class AccountController {
    private final String LOGIN_INVALID = "Login Invalid";

    private UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<UserDto> register(@RequestBody UserDtoRegistration userDtoRegistration) {
        try {
            return new ResponseEntity(
                    this.userService.createUser(userDtoRegistration),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.CONFLICT
            );
        }
    }

    @PostMapping("login")
    public ResponseEntity<Long> login(@RequestBody UserDto userDto) {
        try {
            Long id = this.userService.existsUser(userDto);
            if (id > 0) {
                return new ResponseEntity(
                        this.userService.existsUser(userDto),
                        HttpStatus.CREATED
                );
            }
            else {
                return new ResponseEntity(
                        LOGIN_INVALID,
                        HttpStatus.BAD_REQUEST
                );
            }
        } catch (NoSuchElementException noEl) {
            return new ResponseEntity(
                    noEl.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.CONFLICT
            );
        }
    }
}
