package com.example;

public class Main {
    public static void main(String[] args) {
        OverlayWindow overlay = new OverlayWindow();
        overlay.showOverlay();

        SystemTrayManager.initTray();

        KeyLogger keyLogger = new KeyLogger(overlay);
        keyLogger.startListening();
    }
}
