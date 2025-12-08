package handlers;

/**
 *
 * @author Alonso Cédric
 * @author Gomez Guillaume
 * @version 1.0
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HandlerMapping {
    private final Map<String, HandlerMethod> map = new ConcurrentHashMap<>();

    public void register(String path, Object bean, Method method) {
        map.put(path, new HandlerMethod(bean, method));
    }

    public HandlerMethod getHandler(String path) {
        return map.get(path);
    }
}