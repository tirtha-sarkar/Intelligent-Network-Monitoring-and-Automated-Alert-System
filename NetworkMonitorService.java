package inmas;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class NetworkMonitorService {
    private final ScheduledExecutorService executor =
            Executors.newScheduledThreadPool(6);
    private volatile boolean running;

    public void start(List<HostTarget> targets, int intervalSeconds,
                      Consumer<HostStatus> callback) {
        stop();
        running = true;
        for (HostTarget target : targets) {
            if (!target.isEnabled()) continue;
            executor.scheduleAtFixedRate(
                () -> check(target, callback), 0, intervalSeconds, TimeUnit.SECONDS);
        }
    }

    private void check(HostTarget target, Consumer<HostStatus> callback) {
        if (!running) return;
        long start = System.nanoTime();
        boolean reachable = false;
        boolean portOpen = false;
        String detail;

        try {
            InetAddress address = InetAddress.getByName(target.getHost());
            reachable = address.isReachable(1200);
        } catch (Exception ignored) {}

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(target.getHost(), target.getPort()), 900);
            portOpen = true;
        } catch (Exception ignored) {}

        long latency = (System.nanoTime() - start) / 1_000_000L;

        HostStatus.State state;
        if (!reachable && !portOpen) {
            state = HostStatus.State.OFFLINE;
            detail = "Host unreachable and service port closed";
        } else if (latency > 800 || !portOpen) {
            state = HostStatus.State.DEGRADED;
            detail = portOpen ? "Reachable, but latency is high" : "Host reachable, service port unavailable";
        } else {
            state = HostStatus.State.ONLINE;
            detail = "Host and monitored service are responding";
        }

        callback.accept(new HostStatus(target, state, latency, portOpen, detail));
    }

    public boolean isRunning() { return running; }

    public void stop() {
        running = false;
    }

    public void shutdown() {
        running = false;
        executor.shutdownNow();
    }
}
