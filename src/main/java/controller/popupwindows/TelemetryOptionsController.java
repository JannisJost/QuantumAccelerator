package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.CustomControls.ToggleSwitch;
import ch.dragxfly.quantumaccelerator.Executors.TelemetryBlocker;
import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class TelemetryOptionsController extends ThemeableWindow implements Initializable {

    @FXML
    private Button btnClose;
    private double xOffset = 0;
    private double yOffset = 0;
    private final ToggleSwitch tglTrackingService = new ToggleSwitch();
    private final ToggleSwitch tglPushService = new ToggleSwitch();
    private final ToggleSwitch tglCEIPTasks = new ToggleSwitch();
    private final ToggleSwitch tglMRTTelemetry = new ToggleSwitch();
    private static final String KEY_TRACKING_SERVICES = "trackingservices";
    private static final String KEY_PUSH_SERVICE = "pushservice";
    private static final String KEY_CEIP = "CEIP";
    private static final String KEY_MRT = "MRT";

    @FXML
    private Button btnSaveAndApply;
    @FXML
    private Button btnCancel;
    @FXML
    private GridPane gridSettings;
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tglTrackingService.setSwitchedOn(prefs.getBoolean(KEY_TRACKING_SERVICES, false));
        tglPushService.setSwitchedOn(prefs.getBoolean(KEY_PUSH_SERVICE, false));
        tglCEIPTasks.setSwitchedOn(prefs.getBoolean(KEY_CEIP, false));
        tglMRTTelemetry.setSwitchedOn(prefs.getBoolean(KEY_MRT, false));
        gridSettings.add(tglMRTTelemetry, 1, 0);
        gridSettings.add(tglCEIPTasks, 1, 1);
        gridSettings.add(tglTrackingService, 1, 2);
        gridSettings.add(tglPushService, 1, 3);
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void setTheme() {
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }

    @FXML
    private void moveWindowSecond(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.setX(event.getScreenX() + xOffset);
        currentStage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    private void moveWindow(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        xOffset = currentStage.getX() - event.getScreenX();
        yOffset = currentStage.getY() - event.getScreenY();
    }

    @FXML
    private void cancel(ActionEvent event) {
    }

    @FXML
    private void saveAndApply(ActionEvent event) {
        prefs.putBoolean(KEY_TRACKING_SERVICES, tglTrackingService.isActivated());
        prefs.putBoolean(KEY_MRT, tglMRTTelemetry.isActivated());
        prefs.putBoolean(KEY_PUSH_SERVICE, tglPushService.isActivated());
    }

}
