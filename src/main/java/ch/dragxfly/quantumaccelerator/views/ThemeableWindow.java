package ch.dragxfly.quantumaccelerator.views;

import java.util.prefs.Preferences;

public abstract class ThemeableWindow extends MultilingualView{

    static final String CURRENTTHEME = "currentTheme";
    private final Preferences pref = Preferences.userRoot();

    public abstract void setTheme();

    public static String getCURRENTTHEME() {
        return CURRENTTHEME;
    }

    public Preferences getPref() {
        return pref;
    }
    
}
