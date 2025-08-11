package com.project.punto_red.common.validation.annotation;

import com.project.punto_red.common.validation.validator.CellPhoneValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CellPhoneValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorPhone {
    String message() default "El número de celular debe tener 10 dígitos y comenzar con 3";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}