package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.executors.TempfilesBlacklistManager;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FileFilter;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.chooser.FileChooser;
import ch.dragxfly.quantumaccelerator.views.ThemeableWindow;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.chooser.FolderChooser;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author jannis
 */
public class BlacklistTempCacheFilesController extends ThemeableWindow implements Initializable, Observer {

    @FXML
    private ListView<String> lstBlacklistedItems;
    @FXML
    private Button btnRemoveItem;
    @FXML
    private Button btnAddItem;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;
    @FXML
    private Label lblBlacklistInfo;
    //non FXML
    private double xOffset = 0;
    private double yOffset = 0;
    private FolderChooser folderChooser;
    private final TempfilesBlacklistManager manager = new TempfilesBlacklistManager();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        folderChooser = new FolderChooser("Folder to blacklist", this);
        lstBlacklistedItems.getSelectionModel().selectedItemProperty().addListener(listener -> {
            enableBtnRemove();
        });
        try {
            lstBlacklistedItems.getItems().addAll(manager.getBlacklist());
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void setTheme() {
        Scene scene = btnSave.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        close();
    }

    @FXML
    private void moveWindowSecond(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.setX(event.getScreenX() + xOffset);
        currentStage.setY(event.getScreenY() + yOffset);
    }

    /**
     * Moves the Window
     *
     * @param event
     */
    @FXML
    private void moveWindow(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        xOffset = currentStage.getX() - event.getScreenX();
        yOffset = currentStage.getY() - event.getScreenY();
    }

    /**
     * Shows the folder chooser
     *
     * @param event
     */
    @FXML
    private void addBlacklistedFolder(ActionEvent event) {
        FileChooser chooser = new FileChooser(this);
        FileFilter filter = new FileFilter(true);
        chooser.setFileFilter(filter);
        chooser.show();
    }

    private void close() {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void cancel(ActionEvent event) {
        close();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass().equals(FileChooser.class)) {
            lstBlacklistedItems.getItems().add((String) arg);
        }
    }

    private void enableBtnRemove() {
        if (!lstBlacklistedItems.getItems().isEmpty()) {
            btnRemoveItem.setDisable(false);
        }
    }

    @FXML
    private void saveBlacklist(ActionEvent event) {
        try {
            manager.writeToBlacklistFile(lstBlacklistedItems.getItems());
            close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private void removeSelectedItem(ActionEvent event) {
        String selectedItem = lstBlacklistedItems.getSelectionModel().getSelectedItem();
        lstBlacklistedItems.getItems().remove(selectedItem);
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.popup", locale);
        btnRemoveItem.setText(bundle.getString("btnRemoveItem"));
        btnAddItem.setText(bundle.getString("btnAddItem"));
        btnCancel.setText(bundle.getString("btnCancel"));
        btnSave.setText(bundle.getString("btnSave"));
        lblBlacklistInfo.setText(bundle.getString("lblBlacklistInfo"));
    }
}
