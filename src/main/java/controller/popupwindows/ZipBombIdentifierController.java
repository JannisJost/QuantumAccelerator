package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.views.ThemeableWindow;
import animatefx.animation.Shake;
import ch.dragxfly.quantumaccelerator.executors.errorhandling.ErrorWindow;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FileFilter;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FileOperations;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.chooser.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
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
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author jannis
 */
public class ZipBombIdentifierController extends ThemeableWindow implements Initializable, Observer {

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
        setLanguage(super.getLanguage());
    }

    @FXML
    private void chooseZip(ActionEvent event) {
        FileChooser chooser = new FileChooser(this);
        FileFilter filter = new FileFilter("zip");
        chooser.setFileFilter(filter);
        chooser.show();
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

    private Task getTaskScanZip() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                List<String> zipFiles;
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
                        zip.close();
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
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }

    @FXML
    private void dragAndDropZip(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            File f = db.getFiles().get(0);
            if (new FileOperations().getExtension(f.getName()).equalsIgnoreCase("zip") && !lstZipFolders.getItems().contains(f.getAbsolutePath())) {
                listNotEmpty();
                lstZipFolders.getItems().add(f.getAbsolutePath());
            } else {
                new Shake(lstZipFolders).play();
            }
        }
    }

    @FXML
    private void dragOverList(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
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

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.popup", locale);
        btnChooseZIP.setText(bundle.getString("btnChooseZIP"));
        btnScan.setText(bundle.getString("btnScan"));
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass().equals(FileChooser.class)) {
            lstZipFolders.getItems().add((String) arg);
        }
    }
}
