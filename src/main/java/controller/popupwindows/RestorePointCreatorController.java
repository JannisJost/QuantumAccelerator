package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import ch.dragxfly.quantumaccelerator.tasks.RestorepointTasks;
import java.net.URL;
import java.util.ResourceBundle;
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
 * FXML Controller class
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
        Stage stage = (Stage) btnDenie.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setTheme() {
        try {
            super.getPref().sync();
        } catch (Exception e) {
        }
        Scene scene = btnDenie.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(super.getCURRENTTHEME(), ""));
    }

}
