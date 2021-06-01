package controller.popupwindows;

import Models.TempfileModel;
import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.deleter.FileDeleter;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FileSizeCalculator;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner.Model.ScanDoneModel;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class ScanDoneController extends ThemeableWindow implements Initializable {

    @FXML
    private ListView<CheckBox> lstTempFiles;
    @FXML
    private Button btnClose;
    private double xOffset;
    private double yOffset;
    @FXML
    private Button btnDelete;
    @FXML
    private ProgressIndicator loadLoading;
    List<CheckBox> chksTempFiles = new ArrayList<>();
    @FXML
    private Label lblStatus;
    private final ScanDoneModel model = new ScanDoneModel();
    @FXML
    private Label lblSelectedTempFiles;
    private TempfileModel tempFileModel;
    @FXML
    private Label lblAccessDenied;
    @FXML
    private HBox hBoxLabels;
    @FXML
    private Label lblSize;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setModel(TempfileModel model) {
        this.tempFileModel = model;
    }

    @FXML
    private void close(ActionEvent event) {
        chksTempFiles.clear();
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
        System.gc();
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

    public void loadList() {
        Task taskGetList = getTaskLoadList();
        Thread t1 = new Thread(taskGetList);
        t1.start();
        taskGetList.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                showList();
            }
        });
    }

    private Task getTaskLoadList() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                System.gc();
                List<String> tempFiles = tempFileModel.getTempFilesList();
                double sizeInMBytes = new FileSizeCalculator().getFileSize(tempFiles);
                List<CheckBox> boxes = new ArrayList<>();
                for (String tempFile : tempFiles) {
                    CheckBox chk = new CheckBox();
                    chk.setText(tempFile);
                    chk.setSelected(true);
                    boxes.add(chk);
                }
                model.setSizeInMBytes(sizeInMBytes);
                model.setCheckBoxes(boxes);
                return null;
            }
        };
        return task;
    }

    private void showList() {
        for (CheckBox chk : model.getCheckBoxes()) {
            lstTempFiles.getItems().add(chk);
        }
        lblSize.setText("You can free " + model.getSizeInMBytes() + " MB");
        lblAccessDenied.setText("Could not search " + tempFileModel.getAccessDenied().size() + " folders");
        lblSelectedTempFiles.setText("Found " + tempFileModel.getTempFilesList().size() + " folders");
        hBoxLabels.setVisible(true);
        lblStatus.setVisible(false);
        loadLoading.setVisible(false);
        lstTempFiles.setVisible(true);
        btnDelete.setVisible(true);
    }

    @FXML
    private void deleteTempFiles(ActionEvent event) {
        lblSelectedTempFiles.setVisible(false);
        lblStatus.setText("Deleting temp files");
        lblStatus.setVisible(true);
        loadLoading.setVisible(true);
        lstTempFiles.setVisible(false);
        btnDelete.setVisible(false);
        List<CheckBox> allCheckBoxes = lstTempFiles.getItems();
        model.setCheckedCheckBoxes(allCheckBoxes);
        Task deleteTempFiles = getTaskDeleteTempFiles();
        Thread t2 = new Thread(deleteTempFiles);
        t2.start();
        deleteTempFiles.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                DeleteDone();
            }
        });
    }

    private Task getTaskDeleteTempFiles() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                List<String> toDelete = new ArrayList<>();
                FileDeleter deleter = new FileDeleter();
                for (CheckBox chk : model.getCheckedCheckBoxes()) {
                    if (chk.isSelected()) {
                        toDelete.add(chk.getText());
                    }
                }
                deleter.deleteFile(toDelete);
                return null;
            }
        };
        return task;
    }

    private void DeleteDone() {
        loadLoading.setVisible(false);
        lblStatus.setText("All temp files deleted");
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

}
