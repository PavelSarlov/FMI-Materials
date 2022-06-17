package com.fmi.materials.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizeByteValidator implements ConstraintValidator<SizeByte, byte[]> {
    SizeByte constraintAnnotation;

    @Override public void initialize(SizeByte constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override public boolean isValid(byte[] given, ConstraintValidatorContext constraintValidatorContext) {
        if(given == null) {
            return true;
        }
        return given.length >= constraintAnnotation.min() && given.length <= constraintAnnotation.max();
    }
}
