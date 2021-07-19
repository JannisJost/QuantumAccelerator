package ch.dragxfly.quantumaccelerator.executors;

import ch.dragxfly.quantumaccelerator.executors.errorhandling.ErrorWindow;
import ch.dragxfly.quantumaccelerator.executors.web.Browser.Brave;
import ch.dragxfly.quantumaccelerator.executors.web.Browser.Browser;
import ch.dragxfly.quantumaccelerator.executors.web.Browser.Chrome;
import ch.dragxfly.quantumaccelerator.executors.web.Browser.Edge;
import ch.dragxfly.quantumaccelerator.executors.web.Browser.Opera;
import controller.popupwindows.warning.InfoWindow;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javafx.concurrent.Task;

/**
 *
 * @author jannis
 */
public class PrivacyExecutor {

    List<Browser> browsers = new LinkedList<>();

    public PrivacyExecutor() {
        browsers.add(new Opera());
        browsers.add(new Brave());
        browsers.add(new Chrome());
        browsers.add(new Edge());
    }

    public void run(boolean deleteDNSCache, boolean deleteBrowserCache, boolean deleteBrowserHistory, boolean deleteBrowserCookies) {
        Task t1 = new Task() {
            @Override
            protected Object call() throws Exception {

                for (Browser browser : browsers) {
                    browser.kill();
                }
                if (deleteDNSCache) {
                    deleteDNSCache();
                }
                if (deleteBrowserCache) {
                    DeleteBrowserCache();
                }
                if (deleteBrowserHistory) {
                    deleteBrowserHistory();
                }
                if (deleteBrowserCookies) {
                    deleteBrowserCookies();
                }
                return null;
            }
        };
        new Thread(t1).start();
        t1.setOnSucceeded(event -> {
            new InfoWindow().ShowInfoWindow("Successfully performed all selected");
        });
    }

    /**
     * Deletes the DNS cache via the terminal
     */
    private void deleteDNSCache() {
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(new String[]{"ipconfig", "/flushdns"});
        } catch (IOException e) {
            new ErrorWindow().showErrorWindow("Could not delete DNS-cache \n "
                    + "Error: " + e);
        }
    }

    private void DeleteBrowserCache() {
        for (Browser browser : browsers) {
            browser.clearCache();
        }
    }

    private void deleteBrowserHistory() {
        for (Browser browser : browsers) {
            browser.clearHistory();
        }
    }

    private void deleteBrowserCookies() {
        for (Browser browser : browsers) {
            browser.clearCookies();
        }
    }
}
