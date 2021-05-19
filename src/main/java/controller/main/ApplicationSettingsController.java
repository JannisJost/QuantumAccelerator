package controller.main;

import ch.dragxfly.quantumaccelerator.CustomControls.ToggleSwitch;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class ApplicationSettingsController implements Initializable {

    @FXML
    private Label lblLanguage;
    private ResourceBundle bundle;
    private Locale locale;
    @FXML
    private ChoiceBox<String> choiseLanguage;
    private Slider sldNotificationSound;
    @FXML
    private Button btnApplySettings;
    private CheckBox chkPlayAnimations;
    private final Preferences pref = Preferences.userRoot();
    private static final String ANIMATIONSACTIVE = "playanimations";
    @FXML
    private GridPane gridGeneral;
    @FXML
    private GridPane gridPerformance;
    ToggleSwitch tglswAutostart = new ToggleSwitch();
    ToggleSwitch tglswPlayAnimations = new ToggleSwitch();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gridGeneral.add(tglswAutostart, 1, 0);
        gridPerformance.add(tglswPlayAnimations, 1, 0);
        tglswPlayAnimations.getPropertySwitchedOn().addListener(event->{
            changePlayAnimations();
        });
        choiseLanguage.getItems().add("English");
        choiseLanguage.getItems().add("Deutsch");

    }

    private void loadLang(String lang) {
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("languages.lang", locale);
        lblLanguage.setText(bundle.getString("lblLanguage"));
    }

    private void adjustLanguage(ActionEvent event) {
        MainViewController docCon = new MainViewController();
        String language = choiseLanguage.getValue();
        if ("English".equals(language)) {
            loadLang("en");
            docCon.loadLang("en");
        } else if ("Deutsch".equals(language)) {
            loadLang("de");
            docCon.loadLang("de");
        }
    }

    private void switchNotificationSoundSld(MouseEvent event) {
        double state = sldNotificationSound.getValue();
        if (state == 0) {
            sldNotificationSound.setValue(1);
        } else {
            sldNotificationSound.setValue(0);
        }
    }

    @FXML
    private void applySettings(ActionEvent event) {
    }

    private void changePlayAnimations() {
        boolean playAnimations = tglswPlayAnimations.getIsActivated();
        pref.putBoolean(ANIMATIONSACTIVE, playAnimations);
        try {
            pref.flush();
        } catch (BackingStoreException ex) {
        }
    }

    public void setPlayAnimations(boolean selected) {
        tglswPlayAnimations.setSwitchedOn(selected);
    }
}
