package com.fmi.materials.util;

import com.fmi.materials.exception.InvalidArgumentException;
import com.fmi.materials.model.User;
import com.fmi.materials.vo.ExceptionMessage;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authentication {

    public static void authenticateCurrentUser(Long userId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            User user = (User) authentication.getPrincipal();
            assert user.getId() == userId;
        } catch (Exception e) {
            throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
        }
    }
}
