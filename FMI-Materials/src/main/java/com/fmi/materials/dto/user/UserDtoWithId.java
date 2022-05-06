package com.fmi.materials.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDtoWithId extends UserDto{
    private Long id;

    public UserDtoWithId(Long id, String name, String passwordHash, String email) {
        super(name, passwordHash, email);
        this.id = id;
    }
}
