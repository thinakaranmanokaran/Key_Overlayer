package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OverlayWindow {
    private final JFrame frame;
    private final JLabel label;

    // For dragging
    private Point clickPoint;

    public OverlayWindow() {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);

        // Transparent background
        frame.setBackground(new Color(0, 0, 0, 120));

        label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 42));
        label.setForeground(Color.WHITE);

        frame.add(label);
        frame.setSize(300, 120);

        // Position top-center
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                screen.width / 2 - frame.getWidth() / 2,
                30
        );

        // ---------- MAKE IT DRAGGABLE ----------
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
                        curr.y - clickPoint.y
                );
            }
        });
    }

    public void showOverlay() {
        frame.setVisible(true);
    }

    public void displayKey(String key) {
        SwingUtilities.invokeLater(() -> label.setText(key));
    }
}
