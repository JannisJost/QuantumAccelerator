package ch.dragxfly.quantumaccelerator.CustomControls;

import ch.dragxfly.quantumaccelerator.ViewManager.MultilingualView;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Written with help from Arial
 *
 * @author janni
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
    
    @Override
    public void setLanguage(String lang) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
