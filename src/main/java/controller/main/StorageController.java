package controller.main;

import ch.dragxfly.quantumaccelerator.customControls.CustomToolTip;
import ch.dragxfly.quantumaccelerator.customControls.ToolTipTexts;
import ch.dragxfly.quantumaccelerator.executors.StorageExecutor;
import ch.dragxfly.quantumaccelerator.views.MultilingualView;
import ch.dragxfly.quantumaccelerator.views.ViewOpener;
import controller.popupwindows.warning.InfoWindow;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
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
    private CheckBox chkDeleteThumbnailCache;
    @FXML
    private Button btnFindDoubleFiles;
    @FXML
    private VBox vboxCheckboxes;
    //non FXML
    private final ViewOpener viewOpener = new ViewOpener();
    private final CustomToolTip toolTip = CustomToolTip.getInstance();
    
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
        btnFindDoubleFiles.setText(bundle.getString("btnFindDoubleFiles"));
        chkDeleteThumbnailCache.setText(bundle.getString("chkDeleteThumbnailCache"));
    }
    
    @FXML
    private void showViewDoubleFilesFinder(ActionEvent event) {
        viewOpener.openThemeableView("/fxml/DoubleFileFinder.fxml", "Double finder", false);
    }
    
    @FXML
    private void apply(ActionEvent event) {
        boolean deleteInstallers = chkDeleteInstallerDownload.isSelected();
        boolean clearThumbnailCache = chkDeleteThumbnailCache.isSelected();
        new StorageExecutor().run(deleteInstallers, clearThumbnailCache);
        new InfoWindow().ShowInfoWindow("Performed all selected");
        
    }
    
    @FXML
    private void showToolTipThumbnailCache(MouseEvent event) {
        toolTip.showToolTip(new ToolTipTexts().getThumbnailCache(), event);
    }
    
}
