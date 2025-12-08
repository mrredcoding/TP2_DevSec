package exceptions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseStatus {
    Integer value();
    String reason() default "Server Error";
}