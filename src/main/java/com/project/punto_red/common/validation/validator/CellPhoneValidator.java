package com.project.punto_red.common.validation.validator;

import com.project.punto_red.common.validation.annotation.ValidatorPhone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CellPhoneValidator implements ConstraintValidator<ValidatorPhone, String> {

    @Override
    public void initialize(ValidatorPhone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cellPhone, ConstraintValidatorContext context) {
        if (cellPhone == null) {
            return false;
        }

        if (cellPhone.length() != 10) {
            return false;
        }

        return cellPhone.startsWith("3");
    }
}
