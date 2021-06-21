package controller.main;

import ch.dragxfly.quantumaccelerator.ViewManager.MultilingualView;
import controller.popupwindows.TempScannerController;
import ch.dragxfly.quantumaccelerator.ViewManager.ViewOpener;
import controller.popupwindows.RemoveLogsController;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author janni
 */
public class StorageController extends MultilingualView implements Initializable {

    @FXML
    private Button btnApply;
    @FXML
    private Button btnRemoveTempNCache;
    @FXML
    private CheckBox chkDeleteInstallerDownload;
    @FXML
    private CheckBox chkSelectAll;
    @FXML
    private Button btnRemoveLogs;
    @FXML
    private VBox vboxCheckboxes;
    @FXML
    private CheckBox chkDoubleFiles;
    //non FXML
    private final ViewOpener viewOpener = new ViewOpener();
    private final Preferences pref = Preferences.userNodeForPackage(MainViewController.class);
    private final String LIGHTTHEME_IS_ACTIVE = "gameboosteractive";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
    }

    @FXML
    private void showTempNCache(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scanner/TempScanner.fxml"));
            Parent root = loader.load();
            TempScannerController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Scanner");
            controller.setTheme();
            stage.show();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @FXML
    private void showViewRemoveLogs(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scanner/RemoveLogs.fxml"));
            Parent root = loader.load();
            RemoveLogsController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Remove logs");
            stage.show();
            controller.setTheme();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @FXML
    private void selectAll(ActionEvent event) {
        boolean checked = chkSelectAll.isSelected();
        vboxCheckboxes.getChildren().stream().filter(item -> item instanceof CheckBox).map(item -> (CheckBox) item).filter(item -> item.isSelected() != checked)
                .forEach(item -> item.setSelected(checked));
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.lang", locale);
        btnApply.setText(bundle.getString("btnApply"));
        btnRemoveTempNCache.setText(bundle.getString("btnRemoveTempNCache"));
        chkSelectAll.setText(bundle.getString("chkSelectAll"));
        chkDeleteInstallerDownload.setText(bundle.getString("chkDeleteInstallerDownload"));
        btnRemoveLogs.setText(bundle.getString("btnRemoveLogs"));
        chkDoubleFiles.setText(bundle.getString("chkDoubleFiles"));
    }

}
