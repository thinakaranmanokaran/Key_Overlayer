package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OverlayWindow {
    private final JFrame frame;
    private final JLabel label;

    // For dragging window
    private Point clickPoint;

    public OverlayWindow() {
        frame = new JFrame();

        // 🔥 Makes it NOT show in taskbar
        frame.setType(Window.Type.UTILITY);

        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);

        // Semi-transparent background
        frame.setBackground(new Color(0, 0, 0, 120));

        label = new JLabel("", SwingConstants.CENTER);

        try {
            Font interFont = Font.createFont(
                    Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/fonts/InterTight-Medium.ttf")).deriveFont(Font.BOLD, 42);

            label.setFont(interFont);

        } catch (Exception e) {
            e.printStackTrace();
            label.setFont(new Font("Arial", Font.BOLD, 42)); // fallback
        }

        label.setForeground(Color.WHITE);

        frame.add(label);
        frame.setSize(300, 120);

        // Default: top-center
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                screen.width / 2 - frame.getWidth() / 2,
                30);

        // 🔻--- Make Window Draggable ---🔻
        frame.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                clickPoint = e.getPoint();
            }
        });

        frame.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point curr = e.getLocationOnScreen();
                frame.setLocation(
                        curr.x - clickPoint.x,
                        curr.y - clickPoint.y);
            }
        });
    }

    // Show overlay window
    public void showOverlay() {
        frame.setVisible(true);
    }

    // Update displayed key safely
    public void displayKey(String key) {
        SwingUtilities.invokeLater(() -> label.setText(key));
    }
}
