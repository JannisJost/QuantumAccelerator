package controller.popupwindows.warning;

import ch.dragxfly.quantumaccelerator.views.ThemeableWindow;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
    //non FXML
    private double xOffset = 0;
    private double yOffset = 0;

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

    @Override
    public void setLanguage(String language) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
