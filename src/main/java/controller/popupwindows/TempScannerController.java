package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.models.TempfileModel;
import ch.dragxfly.quantumaccelerator.views.ThemeableWindow;
import ch.dragxfly.quantumaccelerator.views.ViewOpener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.BackingStoreException;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner.SearchEngine;
import controller.popupwindows.warning.InfoDecisionWindow;
import java.util.LinkedList;
import java.util.Locale;
import javafx.scene.paint.Color;

/**
 *
 * @author janni
 */
public class TempScannerController extends ThemeableWindow implements Initializable {

    @FXML
    private CheckBox chkSearchTemp;
    @FXML
    private CheckBox chkSearchCache;
    @FXML
    private Button btnStartScan;
    @FXML
    private VBox vboxBeforeScan;
    @FXML
    private VBox vboxAfterScan;
    @FXML
    private Label lblSize;
    @FXML
    private Button btnClose;
    @FXML
    private Label lblStatus;
    @FXML
    private ProgressBar progressTask;
    @FXML
    private Button btnCancelScan;
    @FXML
    private RadioButton chkQuickScan;
    @FXML
    private RadioButton chkDeepScan;
    @FXML
    private Button btnMinimize;
    @FXML
    private Button btnEditBlacklist;
    //non FXML
    private boolean quickScan;
    private double xOffset = 0;
    private double yOffset = 0;
    private Task taskSearch;
    private Thread searchThread;
    TempfileModel model = new TempfileModel();
    SearchEngine engine = new SearchEngine();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        ToggleGroup gp = new ToggleGroup();
        chkDeepScan.setToggleGroup(gp);
        chkQuickScan.setToggleGroup(gp);
    }

    @FXML
    private void startScan(ActionEvent event) throws InterruptedException {
        lblStatus.setText("Scanning...");
        startScanViewChanges();
        progressTask.setVisible(true);
        taskSearch = getTaskSearchEngine();
        progressTask.progressProperty().bind(taskSearch.progressProperty());
        searchThread = new Thread(taskSearch);
        searchThread.setDaemon(false);
        searchThread.setPriority(Thread.MAX_PRIORITY);
        searchThread.start();
        taskSearch.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                scanDone();
            }
        });
    }

    private void startScanViewChanges() {
        chkDeepScan.setVisible(false);
        chkQuickScan.setVisible(false);
        btnCancelScan.setVisible(true);
        chkSearchTemp.setVisible(false);
        chkSearchCache.setVisible(false);
        taskSearch = getTaskSearchEngine();
        btnClose.setDisable(true);
        btnStartScan.setDisable(true);
        btnEditBlacklist.setDisable(true);
    }

    /**
     * Opens the scan done window
     */
    private void scanDone() {
        btnCancelScan.setVisible(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TempFileScanDone.fxml"));
            Parent root = loader.load();
            TempFileScanDoneController controller = loader.getController();
            controller.setModel(model);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Scan done");
            stage.show();
            controller.setTheme();
            Stage current = (Stage) btnCancelScan.getScene().getWindow();
            current.close();
            controller.loadTempfilesList();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    private Task getTaskSearchEngine() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                LinkedList<String> tempFiles = new LinkedList<>();
                boolean temp = chkSearchTemp.isSelected();
                boolean cache = chkSearchCache.isSelected();
                quickScan = chkQuickScan.isSelected();
                if (quickScan) {
                    //insert logic for quickscan
                } else {
                    if (cache == true && temp == true) {
                        tempFiles = engine.searchFoldersContaining("C:\\", new String[]{"temp", "tmp", "cache", ".cache"});
                    } else if (temp == true) {
                        tempFiles = engine.searchFoldersContaining("C:\\", "temp");
                    } else {
                        tempFiles = engine.searchFoldersContaining("C:\\", "cache");
                    }
                }
                model.setTempFiles(tempFiles);
                model.setAccessDeniedFolders(engine.getAccessDenied());
                return null;
            }
        };
        return task;
    }

    /**
     * Cancels the scan on cancel button pressed
     *
     * @param event
     */
    @FXML
    private void cancelScan(ActionEvent event) {
        btnCancelScan.setDisable(true);
        btnClose.setDisable(false);
        lblStatus.setText("Canceled scan");
        progressTask.setVisible(false);
        if (taskSearch.isRunning()) {
            taskSearch.cancel();
        }

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

    @Override
    public void setTheme() {
        try {
            super.getPref().sync();
        } catch (BackingStoreException e) {
            System.err.println(e);
        }
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }

    @FXML
    private void minimize(ActionEvent event) {
        Stage s = (Stage) btnClose.getScene().getWindow();
        s.setIconified(true);
    }

    @FXML
    private void showBlacklist(ActionEvent event) {
        new ViewOpener().openThemeableView("/fxml/BlacklistTempCacheFiles.fxml", "Blacklist", false);
    }

    @FXML
    private void showExperiencedUserOnlyInfo(ActionEvent event) {
        if (chkSearchCache.isSelected()) {
            boolean isKeepSelected = new InfoDecisionWindow().ShowInfoWindow("Searching and deleting cache will probably harm your system, please only continue if you know what you are doing!");
            chkSearchCache.setSelected(isKeepSelected);
        }
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.popup", locale);
        chkSearchTemp.setText(bundle.getString("chkSearchTemp"));
        chkSearchCache.setText(bundle.getString("chkSearchCache"));
        btnStartScan.setText(bundle.getString("btnStartScan"));
        btnCancelScan.setText(bundle.getString("btnCancel"));
        lblStatus.setText(bundle.getString("folderscan"));
        chkQuickScan.setText(bundle.getString("chkQuickScan"));
        chkDeepScan.setText(bundle.getString("chkDeepScan"));
        btnEditBlacklist.setText(bundle.getString("btnEditBlacklist"));
    }
}
