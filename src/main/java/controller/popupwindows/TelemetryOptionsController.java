package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.customControls.ToggleSwitch;
import ch.dragxfly.quantumaccelerator.executors.TelemetryBlocker;
import ch.dragxfly.quantumaccelerator.views.ThemeableWindow;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
    @FXML
    private Button btnSaveAndApply;
    @FXML
    private Button btnCancel;
    @FXML
    private GridPane gridSettings;
    @FXML
    private ProgressIndicator progApplyingOptions;
    @FXML
    private Label lblInstruction;
    @FXML
    private Label lblMRT;
    @FXML
    private Label lblCEIP;
    @FXML
    private Label lblDiagTrack;
    @FXML
    private Label lblDmwapp;
    @FXML
    private Label lblWifiSense;
    @FXML
    private Label lblMalwareSamples;

    //non FXML
    private double xOffset = 0;
    private double yOffset = 0;
    private static final String KEY_CEIP = "CEIP";
    private static final String KEY_MRT = "MRT";
    private static final String KEY_PUSH_SERVICE = "pushservice";
    private static final String KEY_TRACKING_SERVICES = "trackingservices";
    private static final String KEY_WIFI_SENSE = "wifisense";
    private static final String SEND_MALEWARE_SAMPLES = "sendmalewaresamples";

    // toggle switches
    private final ToggleSwitch tglTrackingService = new ToggleSwitch();
    private final ToggleSwitch tglPushService = new ToggleSwitch();
    private final ToggleSwitch tglCEIPTasks = new ToggleSwitch();
    private final ToggleSwitch tglMRTTelemetry = new ToggleSwitch();
    private final ToggleSwitch tglWifiSense = new ToggleSwitch();
    private final ToggleSwitch tglSendMalewareSamples = new ToggleSwitch();
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        tglTrackingService.setActivated(prefs.getBoolean(KEY_TRACKING_SERVICES, true));
        tglPushService.setActivated(prefs.getBoolean(KEY_PUSH_SERVICE, true));
        tglCEIPTasks.setActivated(prefs.getBoolean(KEY_CEIP, true));
        tglMRTTelemetry.setActivated(prefs.getBoolean(KEY_MRT, true));
        tglWifiSense.setActivated(prefs.getBoolean(KEY_WIFI_SENSE, true));
        tglSendMalewareSamples.setActivated(prefs.getBoolean(SEND_MALEWARE_SAMPLES, true));
        gridSettings.add(tglMRTTelemetry, 1, 0);
        gridSettings.add(tglCEIPTasks, 1, 1);
        gridSettings.add(tglTrackingService, 1, 2);
        gridSettings.add(tglPushService, 1, 3);
        gridSettings.add(tglWifiSense, 3, 0);
        gridSettings.add(tglSendMalewareSamples, 3, 1);
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
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    /**
     * Saves and applies the chosen telemetry option
     *
     * @param event
     */
    @FXML
    private void saveAndApply(ActionEvent event) {
        setApplyingState(true);
        prefs.putBoolean(SEND_MALEWARE_SAMPLES, tglSendMalewareSamples.isActivated());
        prefs.putBoolean(KEY_TRACKING_SERVICES, tglTrackingService.isActivated());
        prefs.putBoolean(KEY_MRT, tglMRTTelemetry.isActivated());
        prefs.putBoolean(KEY_PUSH_SERVICE, tglPushService.isActivated());
        prefs.putBoolean(KEY_CEIP, tglCEIPTasks.isActivated());
        prefs.putBoolean(KEY_WIFI_SENSE, tglWifiSense.isActivated());
        TelemetryBlocker blocker = new TelemetryBlocker();
        blocker.blockTelemetry(this, tglMRTTelemetry.isActivated(), tglCEIPTasks.isActivated(), tglTrackingService.isActivated(), tglPushService.isActivated(), tglWifiSense.isActivated(), tglSendMalewareSamples.isActivated());
    }

    public void setApplyingState(boolean isApplying) {
        progApplyingOptions.setVisible(isApplying);
        gridSettings.setDisable(isApplying);
        btnClose.setDisable(isApplying);
        btnCancel.setDisable(isApplying);
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.popup", locale);
        lblInstruction.setText(bundle.getString("lblInstruction"));
        lblMRT.setText(bundle.getString("lblMRT"));
        lblCEIP.setText(bundle.getString("lblCEIP"));
        lblDiagTrack.setText(bundle.getString("lblDiagTrack"));
        lblDmwapp.setText(bundle.getString("lblDmwapp"));
        lblWifiSense.setText(bundle.getString("lblWifiSense"));
        lblMalwareSamples.setText(bundle.getString("lblMalwareSamples"));
        btnCancel.setText(bundle.getString("btnCancel"));
        btnSaveAndApply.setText(bundle.getString("btnSaveAndApply"));
    }
}
