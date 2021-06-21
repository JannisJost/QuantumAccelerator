package controller.main;

import ch.dragxfly.quantumaccelerator.CustomControls.ToggleSwitch;
import ch.dragxfly.quantumaccelerator.Executors.errorhandling.ErrorWindow;
import ch.dragxfly.quantumaccelerator.ViewManager.MultilingualView;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author janni
 */
public class ApplicationSettingsController extends MultilingualView implements Initializable {

    @FXML
    private Label lblLanguage;
    @FXML
    private ChoiceBox<String> choiseLanguage;
    @FXML
    private GridPane gridGeneral;
    @FXML
    private GridPane gridPerformance;
    @FXML
    private Button btnApplyLanguage;
    @FXML
    private Label lblGeneral;
    @FXML
    private Label lblAutostart;
    @FXML
    private Label lblPerfomance;
    @FXML
    private Label lblPlayAnimations;
    @FXML
    private Label lblAppearance;
    ToggleSwitch tglswAutostart = new ToggleSwitch();
    ToggleSwitch tglswPlayAnimations = new ToggleSwitch();
    //non FXML
    private final Preferences pref = Preferences.userRoot();
    private static final String ANIMATIONSACTIVE = "playanimations";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        gridGeneral.add(tglswAutostart, 1, 0);
        gridPerformance.add(tglswPlayAnimations, 1, 0);
        tglswPlayAnimations.isSwitchedOn().addListener(event -> {
            changePlayAnimations();
        });
        choiseLanguage.getItems().add("English");
        choiseLanguage.getItems().add("Deutsch");
        choiseLanguage.getItems().add("Français");
        selectCurrentLanguage();
    }

    private void selectCurrentLanguage() {
        int index;
        switch (super.getLanguage()) {
            case "en":
                index = 0;
                break;
            case "de":
                index = 1;
                break;
            case "fr":
                index = 2;
                break;
            default:
                index = 0;
        }
        choiseLanguage.getSelectionModel().select(index);
    }

    private void changePlayAnimations() {
        boolean playAnimations = tglswPlayAnimations.isActivated();
        pref.putBoolean(ANIMATIONSACTIVE, playAnimations);
        try {
            pref.flush();
        } catch (BackingStoreException ex) {
        }
    }

    public void setPlayAnimations(boolean selected) {
        tglswPlayAnimations.setActivated(selected);
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.lang", locale);
        lblGeneral.setText(bundle.getString("lblGeneral"));
        lblPerfomance.setText(bundle.getString("lblPerfomance"));
        lblPlayAnimations.setText(bundle.getString("lblPlayAnimations"));
        lblLanguage.setText(bundle.getString("lblLanguage"));
        btnApplyLanguage.setText(bundle.getString("btnApplySettings"));
        lblAppearance.setText(bundle.getString("lblAppearance"));
        lblAutostart.setText(bundle.getString("lblAutostart"));
    }

    @FXML
    private void applyLanguage(ActionEvent event) {
        new ErrorWindow().showErrorWindow("Changes will be applied after restart");
        switch (choiseLanguage.getValue()) {
            case "English":
                super.setNewLanguage("en");
                break;
            case "Deutsch":
                super.setNewLanguage("de");
                break;
            case "Français":
                super.setNewLanguage("fr");
                break;
            default:
                super.setNewLanguage("en");
        }
    }
}
