package inmas;

public class HostTarget {
    private String name;
    private String host;
    private int port;
    private boolean enabled = true;

    public HostTarget(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    public String getName() { return name; }
    public String getHost() { return host; }
    public int getPort() { return port; }
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @Override
    public String toString() {
        return name + " (" + host + ":" + port + ")";
    }
}
