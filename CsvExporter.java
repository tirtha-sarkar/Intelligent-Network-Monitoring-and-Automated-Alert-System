package inmas;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public final class CsvExporter {
    private CsvExporter() {}

    public static void export(File file, List<HostStatus> statuses) throws IOException {
        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write("Host,Address,Port,State,Latency(ms),PortOpen,Detail,Timestamp\n");
            for (HostStatus s : statuses) {
                writer.write(csv(s.getTarget().getName()) + ","
                        + csv(s.getTarget().getHost()) + ","
                        + s.getTarget().getPort() + ","
                        + s.getState() + ","
                        + s.getLatencyMs() + ","
                        + s.isPortOpen() + ","
                        + csv(s.getDetail()) + ","
                        + s.getTimestamp() + "\n");
            }
        }
    }

    private static String csv(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
