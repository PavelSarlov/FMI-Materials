package com.fmi.materials.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto {
    private String name;
    private String passwordHash;
    private String email;

    public UserDto(String name, String password, String email) {
        this.name = name;
        // to hash
        this.passwordHash = password;
        this.email = email;
    }
}
