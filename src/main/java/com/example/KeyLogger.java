package com.example;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeyLogger implements NativeKeyListener {

    private final OverlayWindow overlayWindow;
    private final Set<String> activeModifiers = new HashSet<>();

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

    private boolean isModifier(int keyCode) {
        switch (keyCode) {
            case NativeKeyEvent.VC_SHIFT:
            case NativeKeyEvent.VC_CONTROL:
            case NativeKeyEvent.VC_META:
            case NativeKeyEvent.VC_ALT:
                return true;
            default:
                return false;
        }
    }

    private String getModifierName(int keyCode) {
        switch (keyCode) {
            case NativeKeyEvent.VC_SHIFT:
                return "Shift";
            case NativeKeyEvent.VC_CONTROL:
                return "Ctrl";
            case NativeKeyEvent.VC_META:
                return "Meta";
            case NativeKeyEvent.VC_ALT:
                return "Alt";
            default:
                return "";
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        int code = e.getKeyCode();

        // If modifier pressed
        if (isModifier(code)) {
            activeModifiers.add(getModifierName(code));
            return;
        }

        // Normal key pressed
        String key = NativeKeyEvent.getKeyText(code);

        if (!activeModifiers.isEmpty()) {
            overlayWindow.displayKey(String.join(" + ", activeModifiers) + " + " + key);
        } else {
            overlayWindow.displayKey(key);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        int code = e.getKeyCode();

        // If modifier released
        if (isModifier(code)) {
            activeModifiers.remove(getModifierName(code));
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}
