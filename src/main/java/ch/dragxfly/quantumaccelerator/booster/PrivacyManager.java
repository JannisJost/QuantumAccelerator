
package ch.dragxfly.quantumaccelerator.booster;

/**
 *
 * @author janni
 */
public class PrivacyManager {

    public void applyPrivacy(boolean[] checked) {

    }

    private boolean deleteDNSCache() {
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(new String[]{"ipconfig", "/flushdns"});
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
