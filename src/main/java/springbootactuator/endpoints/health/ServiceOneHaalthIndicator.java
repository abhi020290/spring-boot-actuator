package springbootactuator.endpoints.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component("serviceOne")
public class ServiceOneHaalthIndicator implements HealthIndicator {
    private final String message_key = "Service A";

    @Override
    public Health health() {
        if (isRunningServiceA()) {
            return Health.down().withDetail(message_key, "Not Available").build();
        }
        return Health.up().withDetail(message_key, "Available").build();
    }
    private Boolean isRunningServiceA() {
        Boolean isRunning = Thread.currentThread().isAlive();
        // Logic Skipped
        return isRunning;
    }
}
