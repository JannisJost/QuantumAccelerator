package ch.dragxfly.quantumaccelerator.customControls;

import ch.dragxfly.quantumaccelerator.views.MultilingualView;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Written with some help of Arial
 *
 * @author jannis
 */
public final class ToolTipTexts extends MultilingualView {

    private final Locale locale;
    private final ResourceBundle bundle;

    public ToolTipTexts() {
        locale = new Locale(super.getLanguage());
        bundle = ResourceBundle.getBundle("languages.descriptions.descriptions", locale);
    }

    //Privacy view
    public final String getDeleteDNSCache() {
        return bundle.getString("dnsCache");
    }

    public final String getCam() {
        return bundle.getString("cam");
    }

    public final String getTelemetry() {
        return bundle.getString("telemetry");
    }

    public final String getPwGen() {
        return bundle.getString("pwGen");
    }

    public final String getBrowserCache() {
        return bundle.getString("BrowserCache");
    }

    //Extras view
    public final String getZipBombIdentifier() {
        return bundle.getString("zipBomb");
    }

    public final String getEnableGodMode() {
        return bundle.getString("godmode");
    }

    public final String getOrganizeDesktop() {
        return bundle.getString("organizeDesktop");
    }

    public final String getRestorepoint() {
        return bundle.getString("restorePoint");
    }

    public final String getStressTest() {
        return bundle.getString("stressTest");
    }

    //password gen
    public final String getTrulyRandom() {
        return bundle.getString("trulyRandom");
    }

    public final String getBrowserHistory() {
        return bundle.getString("browserHistory");
    }

    public final String getBrowserCookies() {
        return bundle.getString("browserCookies");
    }

    public final String getThumbnailCache() {
        return bundle.getString("thumbnailCache");
    }

    @Override
    public void setLanguage(String lang) {
    }
}
