package controller.main;

import ch.dragxfly.quantumaccelerator.executors.DebloatExecutor;
import ch.dragxfly.quantumaccelerator.views.MultilingualView;
import ch.dragxfly.quantumaccelerator.views.ViewOpener;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import shell.RegistryEditor;

/**
 *
 * @author jannis
 */
public class DelWindowsAppsController extends MultilingualView implements Initializable {

    @FXML
    private CheckBox chkDelCoartana;
    @FXML
    private CheckBox chkDelCandyCrush;
    @FXML
    private Button btnApply;
    @FXML
    private CheckBox chkDelMaps;
    @FXML
    private CheckBox chkDelFeedbackHub;
    @FXML
    private CheckBox chkDelCamera;
    @FXML
    private Button btnRevokeRights;
    @FXML
    private Group groupCheckboxes;
    @FXML
    private CheckBox chkSelectAll;
    @FXML
    private CheckBox chkDeleteNews;
    @FXML
    private CheckBox chkDelSolitaire;
    @FXML
    private CheckBox chkDelWeather;
    @FXML
    private VBox vboxCheckboxes;
    @FXML
    private CheckBox chkDelDefaultMail;
    @FXML
    private CheckBox checkDelPrint3D;
    @FXML
    private CheckBox chkXboxGamebar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
    }

    @FXML
    private void selectAll(ActionEvent event) {
        boolean checked = chkSelectAll.isSelected();
        //Selects all checkboxes in vbox
        vboxCheckboxes.getChildren().stream().filter(item -> item instanceof CheckBox).map(item -> (CheckBox) item).filter(item -> item.isSelected() != checked)
                .forEach(item -> item.setSelected(checked));
    }

    @FXML
    private void applyDelApps(ActionEvent event) {
        List<String> toDelete = new LinkedList<>();
        vboxCheckboxes.getChildren().stream()
                .filter(item -> item instanceof CheckBox)
                .map(item -> (CheckBox) item)
                .filter(item -> item.isSelected() == true && item.getAccessibleText() != null)
                .forEach(item -> toDelete.add(item.getAccessibleText()));
        DebloatExecutor debloater = new DebloatExecutor(toDelete);
        debloater.startDebloating();
    }

    @FXML
    private void showAppRightManager(ActionEvent event) {
        new RegistryEditor().readKey("HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeviceAccess\\Global\\{E5323777-F976-4f5b-9B55-B94699C46E44}", "Value");
        new ViewOpener().openThemeableView("/fxml/AppRightManager.fxml", "App right manager", false);
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.lang", locale);
        chkSelectAll.setText(bundle.getString("chkSelectAll"));
        chkDelCoartana.setText(bundle.getString("chkDelCoartana"));
        chkDelCandyCrush.setText(bundle.getString("chkDelCandyCrush"));
        btnApply.setText(bundle.getString("btnApply"));
        chkDelMaps.setText(bundle.getString("chkDelMaps"));
        chkDelFeedbackHub.setText(bundle.getString("chkDelFeedbackHub"));
        chkDelCamera.setText(bundle.getString("chkDelCamera"));
        btnRevokeRights.setText(bundle.getString("btnRevokeRights"));
        chkDeleteNews.setText(bundle.getString("chkDeleteNews"));
        chkDelSolitaire.setText(bundle.getString("chkDelSolitaire"));
        chkDelWeather.setText(bundle.getString("chkDelWeather"));
        chkDelDefaultMail.setText(bundle.getString("chkDelDefaultMail"));
        checkDelPrint3D.setText(bundle.getString("checkDelPrint3D"));
        chkXboxGamebar.setText(bundle.getString("chkXboxGamebar"));
    }

}
