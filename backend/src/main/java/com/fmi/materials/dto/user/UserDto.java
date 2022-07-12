package com.fmi.materials.dto.user;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.materials.validator.SizeByteString;
import com.sun.istack.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    @NotNull
    @SizeByteString(min = 4, max = 50, message = "User name should be between 4 and 50 bytes.")
    private String name;

    @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain at least one digit.")
    @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain at least one lowercase letter.")
    @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain at least one upper letter.")
    @Pattern(regexp = "^[a-zA-Z\\d]{8,}$", message = "Password should be at least 8 characters long.")
    private String password;

    @Email(message = "The email is not a valid one.")
    @SizeByteString(max = 50, message = "Email should be maximum 50 bytes.")
    private String email;

    private List<String> roles;

    public UserDto(String name, String password, String email, List<String> roles) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }
}
