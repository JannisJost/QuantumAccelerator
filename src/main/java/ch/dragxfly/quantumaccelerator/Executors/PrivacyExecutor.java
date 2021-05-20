package ch.dragxfly.quantumaccelerator.Executors;

import java.io.IOException;

/**
 *
 * @author janni
 */
public class PrivacyExecutor {

    public PrivacyExecutor() {

    }

    public void run(boolean deleteDNSCache, boolean deleteCookies, boolean deleteBrowserHistory) {
        showStatusPanel();
        if (deleteDNSCache) {
            deleteDNSCache();
        }
        if (deleteCookies) {
            deleteCookies();
        }
        if (deleteBrowserHistory) {
            deleteBrowserHistory();
        }
    }

    private void deleteDNSCache() {
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(new String[]{"ipconfig", "/flushdns"});
        } catch (IOException e) {
            System.err.println("could not delete DNS cache: " + e);
        }
    }

    private void deleteCookies() {

    }

    private void deleteBrowserHistory() {

    }
//Shows info with information on what is being executed

    private void showStatusPanel() {

    }
}
