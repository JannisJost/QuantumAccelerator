package ch.dragxfly.quantumaccelerator.ViewManager;

import java.io.File;
import java.util.prefs.Preferences;

public abstract class ThemeableWindow {

    static final String CURRENTTHEME = "currentTheme";
    private final Preferences pref = Preferences.userRoot();
    private final File file = new File("");

    public abstract void setTheme();

    public static String getCURRENTTHEME() {
        return CURRENTTHEME;
    }

    public Preferences getPref() {
        return pref;
    }
    
}
