package com.fmi.materials.controller;

import com.fmi.materials.dto.user.UserDto;
import com.fmi.materials.dto.user.UserDtoWithId;
import com.fmi.materials.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> findCourseById(@PathVariable Long id) {
        try {
            return new ResponseEntity(
                    this.userService.findUserById(id),
                    HttpStatus.FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto)
    {
        try {
            return new ResponseEntity(
                    this.userService.createUser(userDto),
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    userDto,
                    HttpStatus.CONFLICT
            );
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUserById(@PathVariable Long id) {
        try {
            this.userService.deleteUserById(id);

            return new ResponseEntity(
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping
    public ResponseEntity<UserDtoWithId> updateCourse(@RequestBody UserDtoWithId userDtoWithId) {
        try {
            return new ResponseEntity(
                    this.userService.updateUser(userDtoWithId),
                    HttpStatus.FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity(
                    userDtoWithId,
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
