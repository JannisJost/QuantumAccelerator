package ch.dragxfly.quantumaccelerator.Style.Animations;

import java.util.prefs.Preferences;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author janni
 */
public class MainAnimations {

    private final RotateTransition rotateTransition = new RotateTransition();
    private final Preferences pref = Preferences.userRoot();
    private static final String ANIMATIONSACTIVE = "playanimations";

    public MainAnimations() {
        setupAnimations();
    }

    public void setupAnimations() {
        rotateTransition.setDuration(Duration.millis(800));
        rotateTransition.setByAngle(90);
    }

    public void animateSettings(ImageView img) {
        if (pref.getBoolean(ANIMATIONSACTIVE, true)) {
            rotateTransition.setNode(img);
            rotateTransition.play();
        }
    }
}
