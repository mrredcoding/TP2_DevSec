package handlers;

import com.sun.net.httpserver.HttpExchange;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
 */

public class HandlerAdapter {
    public Object handle(HandlerMethod hm, HttpExchange exchange) throws Exception {
        Method method = hm.method();
        Object bean = hm.bean();
        try {
            return method.invoke(bean, exchange);
        } catch (InvocationTargetException ite) {
            Throwable cause = ite.getCause();
            if (cause instanceof Exception) throw (Exception) cause;
            throw new RuntimeException(cause);
        }
    }
}