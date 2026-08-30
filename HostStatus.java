package inmas;

public class HostStatus {
    public enum State { ONLINE, DEGRADED, OFFLINE, UNKNOWN }

    private final HostTarget target;
    private final State state;
    private final long latencyMs;
    private final boolean portOpen;
    private final String detail;
    private final long timestamp;

    public HostStatus(HostTarget target, State state, long latencyMs,
                      boolean portOpen, String detail) {
        this.target = target;
        this.state = state;
        this.latencyMs = latencyMs;
        this.portOpen = portOpen;
        this.detail = detail;
        this.timestamp = System.currentTimeMillis();
    }

    public HostTarget getTarget() { return target; }
    public State getState() { return state; }
    public long getLatencyMs() { return latencyMs; }
    public boolean isPortOpen() { return portOpen; }
    public String getDetail() { return detail; }
    public long getTimestamp() { return timestamp; }
}
