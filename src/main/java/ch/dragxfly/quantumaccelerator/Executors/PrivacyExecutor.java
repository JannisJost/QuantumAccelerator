package ch.dragxfly.quantumaccelerator.Executors;

import ch.dragxfly.quantumaccelerator.Executors.errorhandling.ErrorWindow;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author janni
 */
public class PrivacyExecutor {

    VBox vbox = new VBox();
    Label lblStatus;

    public PrivacyExecutor() {

    }

    public void run(boolean isDeleteDNSCache, boolean isDeleteCookies, boolean isDeleteBrowserHistory) {
        showStatusPanel();
        if (isDeleteDNSCache) {
            deleteDNSCache();
        }
        if (isDeleteCookies) {
            deleteCookies();
        }
        if (isDeleteBrowserHistory) {
            deleteBrowserHistory();
        }
    }

    /**
     * Deletes the DNS cache via the terminal
     */
    private void deleteDNSCache() {
        lblStatus.setText("Deleting DNS cache");
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(new String[]{"ipconfig", "/flushdns"});
        } catch (IOException e) {
            new ErrorWindow().showErrorWindow("Could not delete DNS-cache \n "
                    + "Error: " + e);
        }
    }

    private void deleteCookies() {
        lblStatus.setText("Deleting cookies");
    }

    private void deleteBrowserHistory() {
        lblStatus.setText("Deleting browser history");

    }

    /**
     * graphically shows to user what its doing
     */
    private void showStatusPanel() {
        vbox.getChildren().add(lblStatus);
        Scene scene = new Scene(vbox);
        Stage s = new Stage();
        s.setScene(scene);
    }
}
