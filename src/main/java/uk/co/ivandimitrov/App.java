package uk.co.ivandimitrov;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 * Main class.
 */
public final class App {

    private App() {
    }

    /**
     * Entry point.
     * @param args none.
     */
    public static void main(String[] args) {
        turnOffLoggingForJNativeHook();
        registerNativeHook();
        addKeypressListener();
    }

    private static void addKeypressListener() {
        GlobalScreen.addNativeKeyListener(KeypressListener.getInstance());
    }

    private static void registerNativeHook() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    private static void turnOffLoggingForJNativeHook() {
        LogManager.getLogManager().reset();
        Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
    }

}
