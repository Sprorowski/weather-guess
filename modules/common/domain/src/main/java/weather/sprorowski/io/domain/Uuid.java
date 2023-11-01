package weather.sprorowski.io.domain;

import java.util.UUID;

public class Uuid {
    private Uuid() {
        throw new IllegalStateException("Utility class");
    }

    public static String create() {
        return UUID.randomUUID().toString();
    }
}
