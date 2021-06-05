package controller.popupwindows.warning;

import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Error window
 *
 * @author janni
 */
public class ErrorController extends ThemeableWindow implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private Label lblErrorText;
    @FXML
    private Button btnOk;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    public void setErrorMessage(String errorText) {
        lblErrorText.setText(errorText);
    }

    @Override
    public void setTheme() {
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }
}
