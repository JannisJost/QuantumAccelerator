package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.executors.errorhandling.ErrorWindow;
import ch.dragxfly.quantumaccelerator.views.ThemeableWindow;
import ch.dragxfly.quantumaccelerator.executors.DesktopOrganizer;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author jannis
 */
public class DesktopOrganizerController extends ThemeableWindow implements Initializable {
    
    @FXML
    private Button btnClose;
    @FXML
    private Label lblCreateFoldersFor;
    @FXML
    private CheckBox chkTxtFiles;
    @FXML
    private CheckBox chkPictures;
    @FXML
    private CheckBox chkExe;
    @FXML
    private Button btnOrganize;
    @FXML
    private RadioButton radioDefaultTxt;
    @FXML
    private RadioButton radioCustomTxt;
    @FXML
    private TextField txtText;
    @FXML
    private ToggleGroup grText;
    @FXML
    private RadioButton radioDefaultPictures;
    @FXML
    private ToggleGroup grPicture;
    @FXML
    private RadioButton radioCustomPictures;
    @FXML
    private TextField txtPictures;
    @FXML
    private RadioButton radioDefaultExecutables;
    @FXML
    private ToggleGroup grExecutables;
    @FXML
    private RadioButton radioCustomExecutables;
    @FXML
    private TextField txtExecutables;
    @FXML
    private HBox hboxTextOptions;
    @FXML
    private HBox hboxPictureOptions;
    @FXML
    private HBox hboxExecutableOptions;
    @FXML
    private Label lblFolderName;
    //non FXML
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        hboxTextOptions.disableProperty().bind(chkTxtFiles.selectedProperty().not());
        hboxPictureOptions.disableProperty().bind(chkPictures.selectedProperty().not());
        hboxExecutableOptions.disableProperty().bind(chkExe.selectedProperty().not());
        txtText.disableProperty().bind(radioCustomTxt.selectedProperty().not());
        txtPictures.disableProperty().bind(radioCustomPictures.selectedProperty().not());
        txtExecutables.disableProperty().bind(radioCustomExecutables.selectedProperty().not());
        setLanguage(super.getLanguage());
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
    private void organizeDesktop(ActionEvent event) {
        if (chkTxtFiles.isSelected() && radioCustomTxt.isSelected() && txtText.getText().isEmpty()) {
            new ErrorWindow().showErrorWindow("You must enter a folder name");
        } else if (chkPictures.isSelected() && radioCustomPictures.isSelected() && txtPictures.getText().isEmpty()) {
            new ErrorWindow().showErrorWindow("You must enter a folder name");
        } else if (chkExe.isSelected() && radioCustomExecutables.isSelected() && txtExecutables.getText().isEmpty()) {
            new ErrorWindow().showErrorWindow("You must enter a folder name");
        } else {
            new DesktopOrganizer(chkTxtFiles.isSelected(), radioCustomTxt.isSelected() ? txtText.getText() : "Texts",
                    chkPictures.isSelected(), radioCustomPictures.isSelected() ? txtPictures.getText() : "Pictures",
                    chkExe.isSelected(), radioCustomExecutables.isSelected() ? txtExecutables.getText() : "Executables").organizeDesktop();
        }
    }
    
    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.popup", locale);
        lblCreateFoldersFor.setText(bundle.getString("lblCreateFoldersFor"));
        chkTxtFiles.setText(bundle.getString("chkTxtFiles"));
        chkPictures.setText(bundle.getString("chkPictures"));
        chkExe.setText(bundle.getString("chkExe"));
        lblFolderName.setText(bundle.getString("lblFolderName"));
        btnOrganize.setText(bundle.getString("btnOrganize"));
        radioDefaultTxt.setText(bundle.getString("default"));
        radioDefaultPictures.setText(bundle.getString("default"));
        radioDefaultExecutables.setText(bundle.getString("default"));
        radioCustomTxt.setText(bundle.getString("custom"));
        radioCustomPictures.setText(bundle.getString("custom"));
        radioCustomExecutables.setText(bundle.getString("custom"));
    }
    
    @Override
    public void setTheme() {
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }
    
    @FXML
    private void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }
    
}
