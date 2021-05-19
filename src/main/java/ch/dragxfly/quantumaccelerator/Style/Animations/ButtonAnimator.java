package ch.dragxfly.quantumaccelerator.Style.Animations;

import animatefx.animation.AnimationFX;
import animatefx.animation.Pulse;
import java.util.prefs.Preferences;
import javafx.scene.control.Button;

/**
 *
 * @author janni
 */
public class ButtonAnimator {

    private final Preferences pref = Preferences.userRoot();
    private static final String ANIMATIONSACTIVE = "playanimations";

    public void animate(Button btn) {
        if (pref.getBoolean(ANIMATIONSACTIVE, true)) {
            AnimationFX animation = new Pulse(btn);
            animation.setSpeed(2);
            animation.play();
        }
    }
}
