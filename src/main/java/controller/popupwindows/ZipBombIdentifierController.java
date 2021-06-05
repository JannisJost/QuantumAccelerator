package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import animatefx.animation.Shake;
import ch.dragxfly.quantumaccelerator.Executors.errorhandling.ErrorWindow;
import controller.main.MainViewController;
import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FilenameUtils;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class ZipBombIdentifierController extends ThemeableWindow implements Initializable {

    @FXML
    private Button btnChooseZIP;
    @FXML
    private ListView<String> lstZipFolders;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnScan;
    @FXML
    private ProgressIndicator progScan;
    @FXML
    private Label lblScanDone;
    @FXML
    private HBox hboxLabel;
    @FXML
    private Label lblHint;
    //non FXML
    private double xOffset;
    private double yOffset;
    private final List<String> zipBombs = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void chooseZip(ActionEvent event) {
        Task chooseZip = getTaskGetZipFiles();
        new Thread(chooseZip).start();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
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

    @FXML
    private void scanZip(ActionEvent event) {
        btnScan.setVisible(false);
        btnChooseZIP.setVisible(false);
        lstZipFolders.setVisible(false);
        progScan.setVisible(true);
        Task t = getTaskScanZip();
        new Thread(t).start();
        t.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                lstZipFolders.getItems().clear();
                progScan.setVisible(false);
                hboxLabel.setVisible(true);
                if (zipBombs.isEmpty()) {
                    lblScanDone.setStyle("-fx-text-fill: green;");
                    lblScanDone.setText("No zip bomb detected");
                } else {
                    lstZipFolders.getItems().addAll(zipBombs);
                    lstZipFolders.setVisible(true);
                    lblScanDone.setText("Found zip bomb!");
                    lblScanDone.setStyle("-fx-text-fill: red;");
                }
            }
        });
    }

    private Task getTaskGetZipFiles() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "ZIP files", "zip");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION && !lstZipFolders.getItems().contains(chooser.getSelectedFile().getAbsolutePath())) {
                    listNotEmpty();
                    lstZipFolders.getItems().add(chooser.getSelectedFile().getAbsolutePath());
                }
                return null;
            }
        };
        return task;
    }

    private Task getTaskScanZip() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                List<String> zipFiles = new ArrayList<>();
                long uncompressedSize = 0;
                long compressedSize = 0;
                zipFiles = lstZipFolders.getItems();
                try {
                    for (String zipPath : zipFiles) {
                        ZipFile zip = new ZipFile(zipPath);
                        Enumeration e = zip.entries();
                        while (e.hasMoreElements()) {
                            ZipEntry ze = (ZipEntry) e.nextElement();
                            uncompressedSize = ze.getSize();
                            compressedSize = ze.getCompressedSize();
                        }
                        if (compressedSize * 10 < uncompressedSize) {
                            zipBombs.add(zipPath);
                        }
                    }
                } catch (IOException e) {
                    new ErrorWindow().showErrorWindow("Error while scanning zip files");
                }
                return null;
            }
        };
        return task;
    }

    @Override

    public void setTheme() {
        try {
            super.getPref().sync();
        } catch (BackingStoreException e) {

        }
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }

    @FXML
    private void dragAndDropZip(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            File f = db.getFiles().get(0);
            if (FilenameUtils.getExtension(f.getName()).equalsIgnoreCase("zip") && !lstZipFolders.getItems().contains(f.getAbsolutePath())) {
                listNotEmpty();
                lstZipFolders.getItems().add(f.getAbsolutePath());
            } else {
                new Shake(lstZipFolders).play();
            }
        }
    }

    @FXML
    private void dragOverList(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        Dragboard db = event.getDragboard();
        if (event.getDragboard().hasFiles()) {
            File f = db.getFiles().get(0);
            if (FilenameUtils.getExtension(f.getName()).equalsIgnoreCase("zip")) {
                lstZipFolders.setStyle("-fx-background-color: #2ea664;");
            } else {
                lstZipFolders.setStyle("-fx-background-color: red;");
            }
        } else {
            lstZipFolders.setStyle("-fx-background-color: red;");
        }
        event.consume();
    }

    @FXML
    private void dragEnded(DragEvent event) {
        lstZipFolders.setStyle("");
    }

    private void listNotEmpty() {
        btnScan.setDisable(false);
        lblHint.setVisible(false);
    }
}
