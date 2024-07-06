package com.example.hamro_barber.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageFileValidator.class)
public @interface ValidImageFile {
    Class<? extends Payload> [] payload() default{};
    Class<?>[] groups() default {};
    String message() default "Only jpeg,jpg files are allowed";
}
