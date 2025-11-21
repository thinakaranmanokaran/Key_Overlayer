package com.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyLogger implements NativeKeyListener {

    private final OverlayWindow overlayWindow;

    public KeyLogger(OverlayWindow overlayWindow) {
        this.overlayWindow = overlayWindow;
    }

    public void startListening() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try {
            GlobalScreen.registerNativeHook();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        overlayWindow.displayKey(NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    @Override public void nativeKeyReleased(NativeKeyEvent e) { }
    @Override public void nativeKeyTyped(NativeKeyEvent e) { }
}
