package controller.main;

import ch.dragxfly.quantumaccelerator.Executors.PrivacyExecutor;
import ch.dragxfly.quantumaccelerator.tasks.PrivacyTasks;
import ch.dragxfly.quantumaccelerator.Style.CustomToolTip;
import ch.dragxfly.quantumaccelerator.ViewManager.ViewOpener;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class PrivacyController implements Initializable {

    @FXML
    private CheckBox chkDeleteDNSCache;
    @FXML
    private CheckBox chkDeleteCookies;
    @FXML
    private CheckBox chkDeleteBrowserHistory;
    @FXML
    private CheckBox chkSelectAll;
    @FXML
    private Button btnDeactivateCam;
    private final PrivacyTasks tasks = new PrivacyTasks();
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
    private final static String CAMISACTIVE = "camera";
    boolean camIsActive;
    private Task t;
    @FXML
    private VBox vboxCheckboxes;
    private final CustomToolTip toolTip = new CustomToolTip();
    @FXML
    private Button btnFileShredder;
    @FXML
    private Button btnTelemetryOptions;
    @FXML
    private Button btnRun;
    private PrivacyExecutor executor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        camIsActive = prefs.getBoolean(CAMISACTIVE, true);
    }

    @FXML
    private void activateOrDeactivateCam(ActionEvent event) {
        if (camIsActive) {
            t = tasks.getDeactivateCamTask();
        } else {
            t = tasks.getActivateCamTask();
        }
        new Thread(t).start();
        camIsActive = !camIsActive;
        prefs.putBoolean(CAMISACTIVE, camIsActive);
        try {
            prefs.flush();
        } catch (BackingStoreException e) {

        }
        t.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t1) {
                setText();
            }
        });
    }

    private void setText() {
        btnDeactivateCam.setText(camIsActive ? "Deactivate cam" : "Activate cam");
    }

    public void TextOnStart() {
        setText();
    }

    @FXML
    private void selectAll(ActionEvent event) {
        boolean checked = chkSelectAll.isSelected();
        vboxCheckboxes.getChildren().stream().filter(item -> item instanceof CheckBox).map(item -> (CheckBox) item).filter(item -> item.isSelected() != checked)
                .forEach(item -> item.setSelected(checked));
    }

    @FXML
    private void showToolTipDNSCache(MouseEvent event) {
        toolTip.showToolTip("The DNS cache stores data locally on your Computer, based on the websites you visit the most, to make the DNS queries from a server unnecessary and speeds up your requests.\n"
                + "This may sound nice at first, but if someone hacks your computer he can read your DNS-cache and in this way collect data about you. Also if a website has a new IP but your computer\n"
                + "still uses the outdated local information stored on your computer you might not be able to reach your beloved website even tho it is online.", event);
    }

    @FXML
    private void showTelemetryOptions(ActionEvent event) {
        new ViewOpener().openThemeableView("/fxml/TelemetryOptions.fxml", "Telemetry options", false);
    }

    @FXML
    private void RunSelectedPrivacyOptions(ActionEvent event) {
        boolean deleteDNSCache = chkDeleteDNSCache.isSelected();
    }

}
