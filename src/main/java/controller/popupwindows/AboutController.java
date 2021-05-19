package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import animatefx.animation.AnimationFX;
import animatefx.animation.LightSpeedIn;
import animatefx.animation.Pulse;
import ch.dragxfly.quantumaccelerator.Style.Animations.ButtonAnimator;
import ch.dragxfly.quantumaccelerator.Web.WebsiteOpener;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class AboutController extends ThemeableWindow implements Initializable {

    @FXML
    private Button btnGithub;
    @FXML
    private Button btnSupport;
    @FXML
    private Button btnDonate;
    @FXML
    private Button btnClose;
    private double xOffset;
    private double yOffset;
    @FXML
    private ImageView imgIcons8;
    @FXML
    private Label lblTitle;
    private ButtonAnimator animator = new ButtonAnimator();
    WebsiteOpener webopener = new WebsiteOpener();
    @FXML
    private Label lblVersion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
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

    @FXML
    private void openGithub(ActionEvent event) {
        animator.animate(btnGithub);
        webopener.openWebsite("https://github.com/JannisJost");
    }

    @FXML
    private void openSupport(ActionEvent event) {
        animator.animate(btnSupport);
        Desktop desktop;
        if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            try {
                URI mailto = new URI("mailto:quantumaccelerwin10@gmail.com?subject=Support%20request");
                desktop.mail(mailto);
            } catch (URISyntaxException | IOException ex) {
                System.out.println("Error while opening mail: " + ex);
            }
        }
    }

    @FXML
    private void openDonate(ActionEvent event) {
        animator.animate(btnDonate);
    }

    @FXML
    private void openIconsEight(MouseEvent event) {
        webopener.openWebsite("https://icons8.com/");
    }

    @Override
    public void setTheme() {
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(super.getCURRENTTHEME(), ""));
    }

}
