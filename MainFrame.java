package inmas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainFrame extends JFrame {
    private final List<HostTarget> targets = new ArrayList<>();
    private final Map<String, HostStatus> latest = new LinkedHashMap<>();
    private final NetworkMonitorService monitor = new NetworkMonitorService();
    private final AlertManager alertManager = new AlertManager(this::appendAlert);

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Host", "Address", "Port", "Status", "Latency", "Service", "Details"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(tableModel);
    private final JTextArea alertArea = new JTextArea();
    private final JLabel online = cardValue("0");
    private final JLabel degraded = cardValue("0");
    private final JLabel offline = cardValue("0");
    private final JLabel total = cardValue("0");
    private final JLabel systemState = new JLabel("● Monitoring stopped");
    private final LineChartPanel chart = new LineChartPanel();
    private final JSpinner interval = new JSpinner(new SpinnerNumberModel(5, 2, 60, 1));

    public MainFrame() {
        setTitle("Intelligent Network Monitoring and Automated Alert System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 760);
        setMinimumSize(new Dimension(1050, 650));
        setLocationRelativeTo(null);
        buildTargets();
        buildUi();
        refreshTable();
    }

    private static JLabel cardValue(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 25));
        return l;
    }

    private void buildTargets() {
        targets.add(new HostTarget("Local Machine", "127.0.0.1", 80));
        targets.add(new HostTarget("Google DNS", "8.8.8.8", 53));
        targets.add(new HostTarget("Cloudflare DNS", "1.1.1.1", 53));
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        root.setBackground(new Color(241, 245, 249));

        // ---------- HEADER ----------
        JPanel header = new JPanel(new BorderLayout(15, 8));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(10, 12, 10, 12)));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Intelligent Network Monitoring & Automated Alert System");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Real-time host health  •  TCP service checks  •  automated alerts  •  CSV reporting");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(new Color(71, 85, 105));
        subtitle.setBorder(new EmptyBorder(4, 0, 0, 0));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        titlePanel.add(title);
        titlePanel.add(subtitle);
        header.add(titlePanel, BorderLayout.CENTER);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        controls.setOpaque(false);
        controls.add(new JLabel("Interval:"));
        controls.add(interval);
        JButton start = new JButton("Start Monitoring");
        JButton stop = new JButton("Stop");
        JButton add = new JButton("Add Host");
        JButton remove = new JButton("Remove");
        JButton export = new JButton("Export CSV");
        controls.add(start);
        controls.add(stop);
        controls.add(add);
        controls.add(remove);
        controls.add(export);
        header.add(controls, BorderLayout.SOUTH);
        root.add(header, BorderLayout.NORTH);

        // ---------- TABS IN CENTER ----------
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Live Dashboard", dashboardPanel());
        tabs.addTab("Alert Log", alertPanel());
        tabs.addTab("Project Information", projectPanel());
        root.add(tabs, BorderLayout.CENTER);

        setContentPane(root);

        start.addActionListener(e -> {
            monitor.start(targets, (Integer) interval.getValue(), this::handleStatus);
            systemState.setText("● Monitoring active");
            systemState.setForeground(new Color(22, 101, 52));
            appendAlert("Monitoring started with " + interval.getValue() + " second interval.");
        });

        stop.addActionListener(e -> {
            monitor.stop();
            systemState.setText("● Monitoring stopped");
            systemState.setForeground(Color.GRAY);
            appendAlert("Monitoring stopped.");
        });

        add.addActionListener(e -> addHost());
        remove.addActionListener(e -> removeHost());
        export.addActionListener(e -> exportCsv());
    }

    private JPanel card(String name, JLabel value) {
        JPanel p = new JPanel(new BorderLayout(4, 2));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(8, 12, 8, 12)));
        JLabel n = new JLabel(name);
        n.setFont(new Font("SansSerif", Font.BOLD, 11));
        n.setForeground(new Color(71, 85, 105));
        p.add(n, BorderLayout.NORTH);
        p.add(value, BorderLayout.CENTER);
        return p;
    }

    private JPanel dashboardPanel() {
        JPanel p = new JPanel(new BorderLayout(10, 10));
        p.setBorder(new EmptyBorder(10, 0, 0, 0));
        p.setBackground(Color.WHITE);

        // Cards stay above the table, not behind the header.
        JPanel cards = new JPanel(new GridLayout(1, 4, 10, 10));
        cards.setOpaque(false);
        cards.setBorder(new EmptyBorder(0, 0, 8, 0));
        cards.add(card("TOTAL HOSTS", total));
        cards.add(card("ONLINE", online));
        cards.add(card("DEGRADED", degraded));
        cards.add(card("OFFLINE / ALERT", offline));
        p.add(cards, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(8, 8));
        center.setOpaque(false);

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setOpaque(false);
        statusBar.add(systemState, BorderLayout.WEST);
        JLabel info = new JLabel("Intelligence score: latency + reachability + service availability");
        info.setForeground(new Color(71, 85, 105));
        statusBar.add(info, BorderLayout.EAST);
        center.add(statusBar, BorderLayout.NORTH);

        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.getTableHeader().setPreferredSize(new Dimension(0, 30));
        center.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(center, BorderLayout.CENTER);

        chart.setPreferredSize(new Dimension(500, 155));
        p.add(chart, BorderLayout.SOUTH);
        return p;
    }

    private JPanel alertPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(new EmptyBorder(10, 0, 0, 0));
        p.setBackground(Color.WHITE);
        alertArea.setEditable(false);
        alertArea.setLineWrap(true);
        alertArea.setWrapStyleWord(true);
        alertArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        p.add(new JScrollPane(alertArea), BorderLayout.CENTER);
        return p;
    }

    private JPanel projectPanel() {
        JPanel p = new JPanel(new BorderLayout(12, 12));
        p.setBorder(new EmptyBorder(15, 15, 15, 15));
        p.setBackground(Color.WHITE);
        JTextArea a = new JTextArea();
        a.setEditable(false);
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
        a.setFont(new Font("SansSerif", Font.PLAIN, 14));
        a.setText("""
PROJECT: Intelligent Network Monitoring and Automated Alert System

Objective:
Build a higher-level Java networking application that continuously checks network
hosts and important TCP services, calculates a health state, records events, and
automatically generates alerts when a host changes condition.

Core engineering components:
• Host reachability and latency measurement
• TCP port/service availability monitoring
• Multi-threaded scheduled monitoring
• State classification: ONLINE / DEGRADED / OFFLINE
• Automated state-change alerting
• Real-time dashboard and latency trend
• Host management and configurable monitoring interval
• CSV export for analysis and reporting
• Modular architecture using separate service, model, UI and utility classes

Complex Engineering Problem Mapping:
WP1: Networking, algorithms, concurrency and software engineering knowledge
WP2: Availability, latency, reliability, usability and future scalability requirements
WP3: No single obvious solution; health scoring and monitoring logic require analysis
WP5: Uses engineering/networking practices while addressing application-specific needs
WP6: Supports administrators, developers and end users with different priorities
WP7: UI, scheduler, network checks, alerting, logging and reporting are interdependent
""");
        p.add(new JScrollPane(a), BorderLayout.CENTER);
        return p;
    }

    private void handleStatus(HostStatus status) {
        SwingUtilities.invokeLater(() -> {
            String key = status.getTarget().getHost() + ":" + status.getTarget().getPort();
            latest.put(key, status);
            alertManager.process(status);
            chart.addValue(status.getLatencyMs());
            refreshTable();
        });
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        int on = 0, deg = 0, off = 0;
        for (HostTarget t : targets) {
            String key = t.getHost() + ":" + t.getPort();
            HostStatus s = latest.get(key);
            if (s == null) {
                tableModel.addRow(new Object[]{t.getName(), t.getHost(), t.getPort(),
                        "WAITING", "-", "-", "Awaiting first check"});
            } else {
                if (s.getState() == HostStatus.State.ONLINE) on++;
                if (s.getState() == HostStatus.State.DEGRADED) deg++;
                if (s.getState() == HostStatus.State.OFFLINE) off++;
                tableModel.addRow(new Object[]{t.getName(), t.getHost(), t.getPort(),
                        s.getState(), s.getLatencyMs() + " ms",
                        s.isPortOpen() ? "OPEN" : "CLOSED", s.getDetail()});
            }
        }
        total.setText(String.valueOf(targets.size()));
        online.setText(String.valueOf(on));
        degraded.setText(String.valueOf(deg));
        offline.setText(String.valueOf(off));
    }

    private void addHost() {
        JTextField name = new JTextField();
        JTextField host = new JTextField();
        JSpinner port = new JSpinner(new SpinnerNumberModel(80, 1, 65535, 1));
        JPanel p = new JPanel(new GridLayout(0, 2, 6, 6));
        p.add(new JLabel("Display name:")); p.add(name);
        p.add(new JLabel("Host/IP:")); p.add(host);
        p.add(new JLabel("TCP port:")); p.add(port);
        int result = JOptionPane.showConfirmDialog(this, p, "Add monitored host",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION && !name.getText().isBlank() && !host.getText().isBlank()) {
            targets.add(new HostTarget(name.getText().trim(), host.getText().trim(), (Integer) port.getValue()));
            refreshTable();
            appendAlert("Added host: " + name.getText().trim());
        }
    }

    private void removeHost() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a host row first.");
            return;
        }
        if (row < targets.size()) {
            HostTarget t = targets.remove(row);
            latest.remove(t.getHost() + ":" + t.getPort());
            refreshTable();
            appendAlert("Removed host: " + t.getName());
        }
    }

    private void exportCsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File("network-monitor-report.csv"));
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                CsvExporter.export(chooser.getSelectedFile(), new ArrayList<>(latest.values()));
                JOptionPane.showMessageDialog(this, "CSV report exported successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage());
            }
        }
    }

    private void appendAlert(String message) {
        SwingUtilities.invokeLater(() -> {
            alertArea.append(message + System.lineSeparator());
            alertArea.setCaretPosition(alertArea.getDocument().getLength());
        });
    }

    @Override public void dispose() {
        monitor.shutdown();
        super.dispose();
    }
}
