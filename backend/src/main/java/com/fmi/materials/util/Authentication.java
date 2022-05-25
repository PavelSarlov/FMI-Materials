package com.fmi.materials.util;

import com.fmi.materials.exception.InvalidArgumentException;
import com.fmi.materials.model.CustomUserDetails;
import com.fmi.materials.vo.ExceptionMessage;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authentication {

    public static void authenticateCurrentUser(Long userId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        Long loggedUserId = userDetails.getId();

        if(loggedUserId != userId) {
            throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
        }
    }
}
