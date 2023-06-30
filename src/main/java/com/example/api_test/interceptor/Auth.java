package com.example.api_test.interceptor;

import com.example.api_test.domain.Role;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Auth {

    Role role() default Role.USER;
}
