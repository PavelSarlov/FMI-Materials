package com.fmi.materials.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDtoRegistration extends UserDto {
    private String repeatedPassword;

    public UserDtoRegistration(String repeatedPassword, String name, String passwordHash, String email) {
        super(name, passwordHash, email);
        this.repeatedPassword = repeatedPassword;
    }
}