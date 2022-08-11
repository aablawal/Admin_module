package com.unionbankng.future.authorizationserver.customannotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileValidator.class})
public @interface ValidFile {
    String message() default "Only PDF,PNG or JPG images are allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}