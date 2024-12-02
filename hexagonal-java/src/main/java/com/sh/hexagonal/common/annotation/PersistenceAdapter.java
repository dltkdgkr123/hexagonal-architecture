package com.sh.hexagonal.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

@SuppressWarnings("checkstyle:MissingJavadocType")
@Target({ElementType.TYPE}) // scope : class, interface, enum
@Retention(RetentionPolicy.RUNTIME) //  default RetentionPolicy : CLASS
@Documented
@Component
public @interface PersistenceAdapter {

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  @AliasFor(annotation = Component.class)
  String value() default "";
}

