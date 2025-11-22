package com.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URI;

public class SystemTrayManager {

    public static void initTray() {
        if (!SystemTray.isSupported()) {
            System.err.println("System tray not supported!");
            return;
        }

        try {
            // Load tray icon
            Image icon = ImageIO.read(
                    SystemTrayManager.class.getResourceAsStream("/icons/tray_icon.png"));

            PopupMenu menu = new PopupMenu();

            // --- Author Button ---
            MenuItem author = new MenuItem("Author");
            author.addActionListener((ActionEvent e) -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://thinakaran.dev/"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            menu.add(author);

            // --- Exit Button ---
            MenuItem exit = new MenuItem("Exit");
            exit.addActionListener((ActionEvent e) -> {
                System.exit(0);
            });
            menu.add(exit);

            // Tray icon
            TrayIcon tray = new TrayIcon(icon, "Key Overlayer", menu);
            tray.setImageAutoSize(true);

            SystemTray.getSystemTray().add(tray);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
