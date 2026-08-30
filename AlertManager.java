package inmas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AlertManager {
    private final Map<String, HostStatus.State> previous = new HashMap<>();
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Consumer<String> logger;

    public AlertManager(Consumer<String> logger) {
        this.logger = logger;
    }

    public void process(HostStatus status) {
        String key = status.getTarget().getHost() + ":" + status.getTarget().getPort();
        HostStatus.State old = previous.put(key, status.getState());

        if (old == null) {
            logger.accept(time() + " | INFO | " + status.getTarget().getName()
                    + " initial state: " + status.getState());
            return;
        }

        if (old != status.getState()) {
            String level = status.getState() == HostStatus.State.OFFLINE ? "CRITICAL" : "ALERT";
            logger.accept(time() + " | " + level + " | "
                    + status.getTarget().getName() + " changed "
                    + old + " -> " + status.getState());
        }
    }

    private String time() {
        return LocalDateTime.now().format(fmt);
    }
}
