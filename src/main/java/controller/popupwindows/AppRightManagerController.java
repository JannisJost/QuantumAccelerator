package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.customControls.ToggleSwitch;
import ch.dragxfly.quantumaccelerator.executors.AppRightsManager;
import ch.dragxfly.quantumaccelerator.views.ThemeableWindow;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import shell.RegistryEditor;

/**
 *
 * @author jannis
 */
public class AppRightManagerController extends ThemeableWindow implements Initializable {

    @FXML
    private Button btnClose;
    @FXML
    private GridPane gridSettings;
    @FXML
    private Label lblCameraAccess;
    @FXML
    private Label lblLocationAccess;
    @FXML
    private Label lblNotificationAccess;
    @FXML
    private Button btnApply;
    @FXML
    private Button btnCancel;
    @FXML
    private Label lblLetApps;
    @FXML
    private Label lblMicAccess;
    @FXML
    private Label lblAccountInfo;
    @FXML
    private Label lblAppRightsInstruction;
    //non FXML
    private double xOffset = 0;
    private double yOffset = 0;
    private static final String CAMERAKEYLOCATION = "HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{E5323777-F976-4f5b-9B55-B94699C46E44}";
    private static final String LOCATIONKEYLOCATION = "HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{BFA794E4-F964-4FDB-90F6-51056BFE4B44}";
    private static final String NOTIFICATIONKEYLOCATION = "HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{52079E78-A92B-413F-B213-E8FE35712E72}";
    private static final String MICROPHONEKEYLOCATION = "HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{2EEF81BE-33FA-4800-9670-1CD474972C3F}";
    private static final String ACCOUNTINFOKEYLOCATION = "HKEY_CURRENT_USER\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{C1D23ACC-752B-43E5-8448-8D0E519CD6D6}";
    private final ToggleSwitch tglCamera = new ToggleSwitch();
    private final ToggleSwitch tglNotifications = new ToggleSwitch();
    private final ToggleSwitch tglLocation = new ToggleSwitch();
    private final ToggleSwitch tglMic = new ToggleSwitch();
    private final ToggleSwitch tglAccountInfo = new ToggleSwitch();
    private final RegistryEditor regedit = new RegistryEditor();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        gridSettings.add(tglCamera, 1, 0);
        gridSettings.add(tglLocation, 1, 1);
        gridSettings.add(tglNotifications, 1, 2);
        gridSettings.add(tglMic, 1, 3);
        gridSettings.add(tglAccountInfo, 1, 4);
        tglCamera.setActivated(regedit.readKey(CAMERAKEYLOCATION, "Value").equals("Allow"));
        tglLocation.setActivated(regedit.readKey(LOCATIONKEYLOCATION, "Value").equals("Allow"));
        tglNotifications.setActivated(regedit.readKey(NOTIFICATIONKEYLOCATION, "Value").equals("Allow"));
        tglMic.setActivated(regedit.readKey(MICROPHONEKEYLOCATION, "Value").equals("Allow"));
        tglAccountInfo.setActivated(regedit.readKey(ACCOUNTINFOKEYLOCATION, "Value").equals("Allow"));
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

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.popup", locale);
        lblAppRightsInstruction.setText(bundle.getString("lblAppRightsInstruction"));
        lblLetApps.setText(bundle.getString("lblLetApps"));
        lblCameraAccess.setText(bundle.getString("lblCameraAccess"));
        lblLocationAccess.setText(bundle.getString("lblLocationAccess"));
        lblNotificationAccess.setText(bundle.getString("lblNotificationAccess"));
        lblMicAccess.setText(bundle.getString("lblMicAccess"));
        lblAccountInfo.setText(bundle.getString("lblAccountInfo"));
        btnApply.setText(bundle.getString("btnApply"));
        btnCancel.setText(bundle.getString("btnCancel"));
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void applyOptions(ActionEvent event) {
        new AppRightsManager().setRights(tglCamera.isActivated(), tglLocation.isActivated(), tglNotifications.isActivated(), tglMic.isActivated(), tglAccountInfo.isActivated());
        btnClose.fire();
    }

    @FXML
    private void cancel(ActionEvent event) {
        btnClose.fire();
    }
}
