package inmas;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LineChartPanel extends JPanel {
    private final List<Long> values = new ArrayList<>();

    public LineChartPanel() {
        setPreferredSize(new Dimension(500, 170));
        setBackground(new Color(248, 250, 252));
        setBorder(BorderFactory.createTitledBorder("Latency Trend (ms)"));
    }

    public void addValue(long value) {
        values.add(Math.min(value, 2000));
        if (values.size() > 50) values.remove(0);
        repaint();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (values.size() < 2) {
            g.setColor(Color.GRAY);
            g.drawString("Waiting for monitoring samples...", 20, 45);
            return;
        }
        int w = getWidth() - 45, h = getHeight() - 45;
        int x0 = 30, y0 = 15 + h;
        long max = 100;
        for (long v : values) max = Math.max(max, v);
        g.setColor(new Color(220, 226, 234));
        g.drawLine(x0, 15, x0, y0);
        g.drawLine(x0, y0, x0 + w, y0);

        g.setColor(new Color(37, 99, 235));
        for (int i = 1; i < values.size(); i++) {
            int x1 = x0 + (i - 1) * w / (values.size() - 1);
            int x2 = x0 + i * w / (values.size() - 1);
            int y1 = y0 - (int) (values.get(i - 1) * h / (double) max);
            int y2 = y0 - (int) (values.get(i) * h / (double) max);
            g.drawLine(x1, y1, x2, y2);
        }
        g.setColor(Color.DARK_GRAY);
        g.drawString("0", 8, y0 + 4);
        g.drawString(max + " ms", 3, 20);
    }
}
