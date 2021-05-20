package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.CustomControls.ToggleSwitch;
import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private Button btnStopDiagnosticTracking;
    @FXML
    private Button btnStopPushService;
    private double xOffset = 0;
    private double yOffset = 0;
    private ToggleSwitch tglTrackingService = new ToggleSwitch();
    private ToggleSwitch tglPushService = new ToggleSwitch();
    @FXML
    private GridPane gridTrackingService;
    @FXML
    private GridPane gridPushService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tglTrackingService.setSwitchedOn(true);
        tglPushService.setSwitchedOn(true);
        gridTrackingService.add(tglTrackingService, 1, 0);
        gridPushService.add(tglPushService, 1, 0);
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void stopDiagnosticTracking(ActionEvent event) {
    }

    @FXML
    private void stopPushService(ActionEvent event) {
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

}
