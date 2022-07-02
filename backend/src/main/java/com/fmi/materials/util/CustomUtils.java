package com.fmi.materials.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.fmi.materials.exception.InvalidArgumentException;
import com.fmi.materials.model.User;
import com.fmi.materials.vo.ExceptionMessage;

import org.springframework.security.core.context.SecurityContextHolder;

public class CustomUtils {

    public static void authenticateCurrentUser(Long userId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        try {
            User user = (User) authentication.getPrincipal();
            if (user.getId() != userId)
                throw new Exception();
        } catch (Exception e) {
            throw new InvalidArgumentException(ExceptionMessage.UNAUTHORIZED.getFormattedMessage());
        }
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
