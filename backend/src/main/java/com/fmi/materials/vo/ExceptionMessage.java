package com.fmi.materials.vo;

public enum ExceptionMessage {
    ALREADY_EXISTS("%s with %s = '%s' already exists"),
    LOGIN_INVALID("User with given email has different password"),
    NOT_FOUND("%s with %s = '%s' not found"),
    PASSWORDS_NOT_EQUAL("The inputted passwords are not equal"),
    INVALID_OPERATION("Invalid user request"),
    UNAUTHORIZED("User authentication failed");

    private String format;

    private ExceptionMessage(String format) {
        this.format = format;
    }

    public <T extends Object> String getFormattedMessage(T... args) {
        return String.format(this.format, args);
    }
}
