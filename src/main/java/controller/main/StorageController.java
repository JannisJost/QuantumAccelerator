package controller.main;

import ch.dragxfly.quantumaccelerator.views.MultilingualView;
import ch.dragxfly.quantumaccelerator.views.ViewOpener;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author jannis
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
    }

    @FXML
    private void showTempNCache(ActionEvent event) {
        viewOpener.openThemeableView("/fxml/scanner/TempScanner.fxml", "Scanner", false);
    }

    @FXML
    private void showViewRemoveLogs(ActionEvent event) {
        viewOpener.openThemeableView("/fxml/scanner/RemoveLogs.fxml", "Scanner", false);
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
