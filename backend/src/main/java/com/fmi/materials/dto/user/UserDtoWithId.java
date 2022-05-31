package com.fmi.materials.dto.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserDtoWithId extends UserDto{
    private Long id;

    public UserDtoWithId(Long id, String name, String password, String email, List<String> roles) {
        super(name, password, email, roles);
        this.id = id;
    }
}
