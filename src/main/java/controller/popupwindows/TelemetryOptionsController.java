package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.CustomControls.ToggleSwitch;
import ch.dragxfly.quantumaccelerator.Executors.TelemetryBlocker;
import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import java.net.URL;
import java.util.ResourceBundle;
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

    @FXML
    private Button btnSaveAndApply;
    @FXML
    private Button btnCancel;
    @FXML
    private GridPane gridSettings;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tglTrackingService.setSwitchedOn(true);
        tglPushService.setSwitchedOn(true);
        tglCEIPTasks.setSwitchedOn(true);
        tglMRTTelemetry.setSwitchedOn(true);
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

    }

}
