package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.CustomControls.CustomToolTip;
import ch.dragxfly.quantumaccelerator.CustomControls.ToolTipTexts;
import ch.dragxfly.quantumaccelerator.Executors.PasswordGenerator;
import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class PasswordGeneratorController extends ThemeableWindow implements Initializable {

    @FXML
    private Button btnCopyPassword;
    @FXML
    private Button btnClose;
    private double xOffset = 0;
    private double yOffset = 0;
    private double mouseX;
    private double mouseY;
    private boolean isMouseOnRectangle = false;
    private PasswordGenerator generator;
    @FXML
    private TextField txtPassword;
    @FXML
    private Slider sldPwLength;
    @FXML
    private Label lblPWLength;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnHelpTrulyRandom;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sldPwLength.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (oldValue != newValue) {
                    btnClear.fire();
                }
            }
        });
        sldPwLength.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            lblPWLength.setText("Password length: " + newValue);
        });
        generator = new PasswordGenerator(this);
    }

    @FXML
    private void getMouseCoordinates(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    @FXML
    private void copyPassword(ActionEvent event) {
        writeToClipboard(txtPassword.getText(), null);
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    @FXML
    private void setMouseOnRectangleFalse(MouseEvent event) {
        isMouseOnRectangle = false;
    }

    @FXML
    private void setMouseOnRectangleTrue(MouseEvent event) {
        if (!generator.isIsRunning()) {
            new Thread(getTaskGeneratePassword()).start();
        } else {
            //do nothing
        }
        isMouseOnRectangle = true;
    }

    public boolean isIsMouseOnRectangle() {
        return isMouseOnRectangle;
    }

    public void setPasswordText(String pw) {
        Platform.runLater(() -> {
            txtPassword.setText(pw);
        });
    }

    private Task getTaskGeneratePassword() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                generator.startGenerating((int) sldPwLength.getValue());
                return null;
            }
        };
        return task;
    }

    public void writeToClipboard(String s, ClipboardOwner clipOwner) {
        java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection(s);
        clipboard.setContents(transferable, clipOwner);
    }

    @FXML
    private void clearPassword(ActionEvent event) {
        txtPassword.clear();
        generator.reset();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void moveWindow(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        xOffset = currentStage.getX() - event.getScreenX();
        yOffset = currentStage.getY() - event.getScreenY();
    }

    @FXML
    private void moveWindowSecond(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.setX(event.getScreenX() + xOffset);
        currentStage.setY(event.getScreenY() + yOffset);
    }

    @Override
    public void setTheme() {
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }

    @FXML
    private void showToolTipTrulyRandom(MouseEvent event) {
        new CustomToolTip().showToolTip(new ToolTipTexts().getTrulyRandom(), event);
    }

}
