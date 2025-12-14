package security.services;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiterService {

    private static final RateLimiterService INSTANCE = new RateLimiterService();
    private static final int LIMIT = 3;
    private static final int WINDOW_SECONDS = 60;

    private final Map<String, Window> buckets = new ConcurrentHashMap<>();

    private RateLimiterService() {}

    public static RateLimiterService getInstance() {
        return INSTANCE;
    }

    public static int getLimitRate() {
        return LIMIT;
    }

    public static String getWindow() {
        return WINDOW_SECONDS + "s";
    }

    public boolean tryConsume(String key) {
        var now = Instant.now().getEpochSecond();
        var window = buckets.computeIfAbsent(key, k -> new Window(now));

        synchronized (window) {
            if (now - window.startTime >= WINDOW_SECONDS) {
                window.startTime = now;
                window.counter.set(0);
            }
            if (window.counter.incrementAndGet() > LIMIT)
                return false;
        }
        return true;
    }

    private static class Window {
        long startTime;
        AtomicInteger counter = new AtomicInteger(0);

        Window(long startTime) {
            this.startTime = startTime;
        }
    }
}
