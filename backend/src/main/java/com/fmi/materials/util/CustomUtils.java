package com.fmi.materials.util;

import com.fmi.materials.exception.InvalidArgumentException;
import com.fmi.materials.model.User;
import com.fmi.materials.vo.ExceptionMessage;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CustomUtils {

    public static void authenticateCurrentUser(Long userId) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            User user = (User) authentication.getPrincipal();
            assert user.getId() == userId;
        } catch (Exception e) {
            throw new InvalidArgumentException(ExceptionMessage.INVALID_OPERATION.getFormattedMessage());
        }
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
