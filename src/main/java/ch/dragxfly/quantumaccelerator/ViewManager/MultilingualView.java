package ch.dragxfly.quantumaccelerator.ViewManager;

import java.util.prefs.Preferences;

/**
 *
 * @author janni
 */
public abstract class MultilingualView {

    private final Preferences pref = Preferences.userRoot();
    private final String keyLanguage = "language";

    public abstract void setLanguage(String lang);

    public String getLanguage() {
        return pref.get(keyLanguage, "en");
    }
    public void setNewLanguage(String language) {
        pref.put(keyLanguage, language);
    }
}
