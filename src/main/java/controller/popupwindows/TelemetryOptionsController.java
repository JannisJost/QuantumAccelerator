
package controller.popupwindows;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class TelemetryOptionsController implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private CheckBox chkDisableDiagnosticTracking;
    @FXML
    private Button btnStopDiagnosticTracking;
    @FXML
    private CheckBox chkDisablePushService;
    @FXML
    private Button btnStopPushService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void closeWindow(ActionEvent event) {
    }

    @FXML
    private void stopDiagnosticTracking(ActionEvent event) {
    }

    @FXML
    private void stopPushService(ActionEvent event) {
    }
    
}
