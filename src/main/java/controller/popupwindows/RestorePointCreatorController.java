package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.views.ThemeableWindow;
import ch.dragxfly.quantumaccelerator.tasks.RestorepointTasks;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author janni
 */
public class RestorePointCreatorController extends ThemeableWindow implements Initializable {
    
    @FXML
    private Button btnAccept;
    @FXML
    private Button btnDenie;
    @FXML
    private ProgressIndicator progCreatingRestore;
    @FXML
    private Label lblStatus;
    @FXML
    private HBox vboxButtons;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
    }
    
    @FXML
    private void createRestorepoint(ActionEvent event) {
        btnAccept.setVisible(false);
        btnDenie.setVisible(false);
        progCreatingRestore.setVisible(true);
        Task task = new RestorepointTasks().getTaskCreateRestorePoint();
        new Thread(task).start();
        lblStatus.setText("Creating restore point (may take a while)");
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                vboxButtons.getChildren().remove(btnAccept);
                lblStatus.setText("Created restore point");
                progCreatingRestore.setVisible(false);
                btnDenie.setVisible(true);
                btnDenie.setText("Close");
            }
        });
    }
    
    @FXML
    private void close(ActionEvent event) {
        Stage currentStage = (Stage) btnDenie.getScene().getWindow();
        currentStage.close();
    }
    
    @Override
    public void setTheme() {
        try {
            super.getPref().sync();
        } catch (BackingStoreException e) {
        }
        Scene scene = btnDenie.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }
    
    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.popup", locale);
        lblStatus.setText(bundle.getString("lblRestoreStatus"));
        btnAccept.setText(bundle.getString("btnAccept"));
        btnDenie.setText(bundle.getString("btnDenie"));
    }
    
}
