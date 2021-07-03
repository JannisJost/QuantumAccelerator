package controller.main;

import ch.dragxfly.quantumaccelerator.Executors.PrivacyExecutor;
import ch.dragxfly.quantumaccelerator.tasks.PrivacyTasks;
import ch.dragxfly.quantumaccelerator.CustomControls.CustomToolTip;
import ch.dragxfly.quantumaccelerator.CustomControls.ToolTipTexts;
import ch.dragxfly.quantumaccelerator.ViewManager.MultilingualView;
import ch.dragxfly.quantumaccelerator.ViewManager.ViewOpener;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 *
 * @author jannis
 */
public class PrivacyController extends MultilingualView implements Initializable {

    @FXML
    private CheckBox chkDeleteDNSCache;
    @FXML
    private CheckBox chkDeleteCookies;
    @FXML
    private CheckBox chkDeleteBrowserHistory;
    @FXML
    private CheckBox chkSelectAll;
    @FXML
    private Button btnDeactivateCam;
    @FXML
    private VBox vboxCheckboxes;
    @FXML
    private Button btnFileShredder;
    @FXML
    private Button btnTelemetryOptions;
    @FXML
    private Button btnRun;
    @FXML
    private Button btnPasswordGenerator;
    @FXML
    private ProgressIndicator progChangingCamState;
    @FXML
    private Button btnHelpTelemetry;
    @FXML
    private Button btnHelpPwGen;
    @FXML
    private Button btnHelpCam;
    //non FXML
    private Task t;
    boolean camIsActive;
    private PrivacyExecutor executor;
    private final static String CAMISACTIVE = "camera";
    private final PrivacyTasks tasks = new PrivacyTasks();
    private final CustomToolTip toolTip = CustomToolTip.getInstance();
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        camIsActive = prefs.getBoolean(CAMISACTIVE, true);
    }

    @FXML
    private void activateOrDeactivateCam(ActionEvent event) {
        progChangingCamState.setVisible(true);
        if (camIsActive) {
            t = tasks.getDeactivateCamTask();
        } else {
            t = tasks.getActivateCamTask();
        }
        new Thread(t).start();
        camIsActive = !camIsActive;
        prefs.putBoolean(CAMISACTIVE, camIsActive);
        try {
            prefs.flush();
        } catch (BackingStoreException e) {

        }
        t.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t1) {
                progChangingCamState.setVisible(false);
                setCameraButtonText();
            }
        });
    }

    private void setCameraButtonText() {
        btnDeactivateCam.setText(camIsActive ? "Deactivate cam" : "Activate cam");
    }

    public void setDefaultCameraButtonText() {
        setCameraButtonText();
    }

    @FXML
    private void selectAll(ActionEvent event) {
        boolean checked = chkSelectAll.isSelected();
        vboxCheckboxes.getChildren().stream().filter(item -> item instanceof CheckBox).map(item -> (CheckBox) item).filter(item -> item.isSelected() != checked)
                .forEach(item -> item.setSelected(checked));
    }

    @FXML
    private void showToolTipDNSCache(MouseEvent event) {
        toolTip.showToolTip(new ToolTipTexts().getDeleteDNSCache(), event);
    }

    @FXML
    private void showTelemetryOptions(ActionEvent event) {
        new ViewOpener().openThemeableView("/fxml/TelemetryOptions.fxml", "Telemetry options", false);
    }

    @FXML
    private void applyPrivacyOptions(ActionEvent event) {
        boolean deleteDNSCache = chkDeleteDNSCache.isSelected();
    }

    @FXML
    private void showPasswordGenerator(ActionEvent event) {
        new ViewOpener().openThemeableView("/fxml/popup/passwordgenerator/PasswordGenerator.fxml", "Password generator", false);
    }

    @FXML
    private void showToolTipTelemetry(MouseEvent event) {
        toolTip.showToolTip(new ToolTipTexts().getTelemetry(), event);
    }

    @FXML
    private void showToolTipPwGen(MouseEvent event) {
        toolTip.showToolTip(new ToolTipTexts().getPwGen(), event);
    }

    @FXML
    private void showToolTipCam(MouseEvent event) {
        toolTip.showToolTip(new ToolTipTexts().getCam(), event);
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.lang", locale);
        chkSelectAll.setText(bundle.getString("chkSelectAll"));
        chkDeleteDNSCache.setText(bundle.getString("chkDeleteDNSCache"));
        chkDeleteCookies.setText(bundle.getString("chkDeleteCookies"));
        chkDeleteBrowserHistory.setText(bundle.getString("chkDeleteBrowserHistory"));
        btnFileShredder.setText(bundle.getString("btnFileShredder"));
        btnTelemetryOptions.setText(bundle.getString("btnTelemetryOptions"));
        btnRun.setText(bundle.getString("btnRun"));
        btnPasswordGenerator.setText(bundle.getString("btnPasswordGenerator"));
    }

}
