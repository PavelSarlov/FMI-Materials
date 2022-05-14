package com.fmi.materials.vo;

public enum ErrorMessage {
    ALREADY_EXISTS("%s with %s = '%s' already exists"), 
    LOGIN_INVALID("Login invalid"),
    NOT_FOUND("%s with %s = '%s' not found");

    private String format;

    private ErrorMessage(String format) {
        this.format = format;
    }

    public <T extends Object> String getFormattedMessage(T... args) {
        return String.format(this.format, args);
    }
}
