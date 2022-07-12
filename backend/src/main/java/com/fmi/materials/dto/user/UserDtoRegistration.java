package com.fmi.materials.dto.user;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserDtoRegistration extends UserDto {
    private String repeatedPassword;

    public UserDtoRegistration(String repeatedPassword, String name, String password, String email,
            List<String> roles) {
        super(name, password, email, roles);
        this.repeatedPassword = repeatedPassword;
    }
}
