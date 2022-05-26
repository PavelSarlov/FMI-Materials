package com.fmi.materials.controller;

import com.fmi.materials.dto.response.ResponseDto;
import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoRegistration;
import com.fmi.materials.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @PostMapping("login")
    public ResponseEntity<ResponseDto> login(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<ResponseDto>(
                this.userService.loginUser(userDto),
                HttpStatus.OK);
    }

    @GetMapping("logout")
    public ResponseEntity<ResponseDto> logout(HttpServletResponse resp) {
//        Cookie session = new Cookie("JSESSION", null);
//        session.setMaxAge(0);
//        resp.addCookie(session);

        return new ResponseEntity<ResponseDto>(
                this.userService.logoutUser(),
                HttpStatus.OK);
    }
}
