package controller.popupwindows;

import Models.LogFilesModel;
import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FileDeleter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import winbooster.SearchEngine.FolderScanner.SearchEngine;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class RemoveLogsController extends ThemeableWindow implements Initializable {

    @FXML
    private Label lblStatus;
    @FXML
    private ProgressBar progressTask;
    @FXML
    private Button btnStartScan;
    private final LogFilesModel model = new LogFilesModel();
    @FXML
    private ListView<CheckBox> lstLogFiles;
    @FXML
    private Button btnDelLogFiles;
    @FXML
    private Button btnClose;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void startScan(ActionEvent event) {
        btnStartScan.setDisable(true);
        Task searchLogFiles = getTaskSearchLogFiles();
        Task loadList = getTaskLoadList();
        progressTask.progressProperty().bind(searchLogFiles.progressProperty());
        Thread t1 = new Thread(searchLogFiles);
        Thread t2 = new Thread(loadList);
        t1.start();
        lblStatus.setText("Searching for log files");
        searchLogFiles.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                lblStatus.setText("Loading list");
                t2.start();
            }
        });
        loadList.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                progressTask.setVisible(false);
                lblStatus.setVisible(false);
                lstLogFiles.getItems().addAll(model.getCheckBoxes());
                lstLogFiles.setVisible(true);
            }
        });
    }

    private Task getTaskSearchLogFiles() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                SearchEngine engine = new SearchEngine();
                engine.searchForFilesWithExtension("C:\\", "log");
                model.setLogFiles(engine.getRequested());
                return null;
            }
        };
        return task;
    }

    private Task getTaskLoadList() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                List<String> logFiles = model.getLogFiles();
                List<CheckBox> boxes = new ArrayList<>();
                for (String logFile : logFiles) {
                    btnStartScan.setVisible(false);
                    btnDelLogFiles.setVisible(true);
                    CheckBox chk = new CheckBox();
                    chk.setText(logFile);
                    chk.setSelected(true);
                    boxes.add(chk);
                }
                model.setCheckBoxes(boxes);
                return null;
            }
        };
        return task;
    }

    @FXML
    private void delLogFiles(ActionEvent event) {
        lstLogFiles.setVisible(false);
        lblStatus.setText("Deleting log files");
        lblStatus.setVisible(true);
        btnDelLogFiles.setDisable(true);
        List<String> toDelete = new ArrayList<>();
        for (CheckBox chk : lstLogFiles.getItems()) {
            if (chk.isSelected()) {
                toDelete.add(chk.getText());
            }
        }
        lstLogFiles.getItems().clear();
        Task delLog = getTaskDeleteLog(toDelete);
        new Thread(delLog).start();
        delLog.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                lblStatus.setText("All log files deleted");
                lblStatus.setStyle("-fx-text-fill: green;");
                lblStatus.setVisible(true);
            }
        });
    }

    @FXML
    private void close(ActionEvent event) {
        Stage s = (Stage) btnClose.getScene().getWindow();
        s.close();
    }

    @Override
    public void setTheme() {
        try {
            super.getPref().sync();
        } catch (Exception e) {

        }
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(super.getCURRENTTHEME(), ""));
    }

    @FXML
    private void moveWindowSecond(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.setX(event.getScreenX() + xOffset);
        currentStage.setY(event.getScreenY() + yOffset);
    }

    @FXML
    private void moveWindow(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        xOffset = currentStage.getX() - event.getScreenX();
        yOffset = currentStage.getY() - event.getScreenY();
    }

    public Task getTaskDeleteLog(List<String> toDelete) {
        Task<Void> task = new Task<Void>() {
            SearchEngine engine = new SearchEngine();

            @Override
            protected Void call() throws Exception {
                FileDeleter deleter = new FileDeleter();
                deleter.deleteFile(toDelete);
                return null;
            }
        };
        return task;
    }
}
