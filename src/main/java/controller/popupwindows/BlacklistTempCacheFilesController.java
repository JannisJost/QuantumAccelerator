package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.Executors.TempfilesBlacklistManager;
import ch.dragxfly.quantumaccelerator.ViewManager.ThemeableWindow;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.chooser.FolderChooser;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author janni
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
    private double xOffset = 0;
    private double yOffset = 0;
    private FolderChooser folderChooser;
    private final TempfilesBlacklistManager manager = new TempfilesBlacklistManager();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        folderChooser.show();
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
        if (o.getClass().equals(FolderChooser.class)) {
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

}
