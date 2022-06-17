package com.fmi.materials.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizeByteStringValidator implements ConstraintValidator<SizeByteString, String> {
    SizeByteString constraintAnnotation;

    @Override public void initialize(SizeByteString constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override public boolean isValid(String given, ConstraintValidatorContext constraintValidatorContext) {
        if(given == null) {
            return true;
        }
        return given.getBytes().length >= constraintAnnotation.min() && given.getBytes().length <= constraintAnnotation.max();
    }
}
