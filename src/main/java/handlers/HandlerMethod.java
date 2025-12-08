package handlers;

import java.lang.reflect.Method;

/**
 *
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
 */

public record HandlerMethod(Object bean, Method method) { }