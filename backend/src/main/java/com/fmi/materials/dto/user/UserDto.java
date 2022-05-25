package com.fmi.materials.dto.user;

import com.sun.istack.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto {
    @NotNull
    @Size(min=4, max = 50, message = "Username should be between 4 and 50 characters.")
    private String name;
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must contain at least one digit."),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must contain at least one lowercase letter."),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must contain at least one upper letter."),
            @Pattern(regexp = "^[a-zA-Z\\d]{8,}$", message = "Password should be at least 8 characters long.")
    })
    private String password;
    @Email(message = "The email is not a valid one.")
    private String email;
    private List<String> roles;

    public UserDto(String name, String password, String email, List<String> roles) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }
}
