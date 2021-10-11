package controller.main;

import animatefx.animation.AnimationFX;
import animatefx.animation.Jello;
import animatefx.animation.RubberBand;
import ch.dragxfly.quantumaccelerator.executors.web.WebsiteOpener;
import ch.dragxfly.quantumaccelerator.views.MultilingualView;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author jannis
 */
public class FeaturesController extends MultilingualView implements Initializable {

    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnPatchnotes;
    @FXML
    private Button btnDonate;
    @FXML
    private ImageView imgDonateButton;
    @FXML
    private Label lblHeader;
    @FXML
    private Label lblFreeStorage;
    @FXML
    private Label lblDisableBgApps;
    @FXML
    private Label lblManagePrivacy;
    @FXML
    private Label lblGameBooster;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        new RubberBand(imgLogo).play();
        AnimationFX donateAttentionAnimation = new Jello(imgDonateButton);
        donateAttentionAnimation.setCycleCount(-1);
        donateAttentionAnimation.setSpeed(0.5);
        donateAttentionAnimation.play();
    }

    @FXML
    private void triggerAnimationLogo(MouseEvent event) {
        new RubberBand(imgLogo).play();
    }

    @FXML
    private void openPatchnotes(ActionEvent event) {
        new WebsiteOpener().openWebsite("https://github.com/JannisJost/QuantumAccelerator/releases");
    }

    @FXML
    private void openDonate(ActionEvent event) {
        new WebsiteOpener().openWebsite("https://www.buymeacoffee.com/JannisJost");
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.lang", locale);
        btnDonate.setText(bundle.getString("btnDonate"));
        btnPatchnotes.setText(bundle.getString("btnPatchnotes"));
        lblHeader.setText(bundle.getString("lblHeader"));
        lblFreeStorage.setText(bundle.getString("lblFreeStorage"));
        lblDisableBgApps.setText(bundle.getString("lblDisableBgApps"));
        lblManagePrivacy.setText(bundle.getString("lblManagePrivacy"));
        lblGameBooster.setText(bundle.getString("lblGameBooster"));
    }

}
