package uk.co.ivandimitrov;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * Listens for keys ALT, I, P being pressed together.
 */
public final class KeypressListener implements NativeKeyListener {

    private static final int KEY_CODE_ALT = 56;
    private static final int KEY_CODE_I = 23;
    private static final int KEY_CODE_P = 25;
    private static final Clipboard CLIPBOARD = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static final KeypressListener INSTANCE = new KeypressListener();

    private boolean isPressedAlt;
    private boolean isPressedI;
    private boolean isPressedP;
    private boolean isPressedOther;

    private KeypressListener() {
    }

    public static KeypressListener getInstance() {
        return INSTANCE;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        this.setKeyPressed(e.getKeyCode(), true);
        if (isPressedAlt && isPressedI && isPressedP && !isPressedOther) {
            this.setClipboardContentsToCurrentPublicIp();
        }
    }

    private void setKeyPressed(int keyCode, boolean isPressed) {
        switch (keyCode) {
            case KEY_CODE_ALT:
                this.isPressedAlt = isPressed;
                break;
            case KEY_CODE_I:
                this.isPressedI = isPressed;
                break;
            case KEY_CODE_P:
                this.isPressedP = isPressed;
                break;
            default:
                this.isPressedOther = isPressed;
                break;
        }
    }

    private void setClipboardContentsToCurrentPublicIp() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader((new URL("http://bot.whatismyipaddress.com")).openStream()))) {
            String ipAddress = br.readLine().trim();
            CLIPBOARD.setContents(new StringSelection(ipAddress), null);
            System.out.println("Public IP address: " + ipAddress);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        this.setKeyPressed(e.getKeyCode(), false);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent arg0) {// Not used...
    }

}
