package controller.main;

import ch.dragxfly.quantumaccelerator.executors.ExtrasExecutor;
import ch.dragxfly.quantumaccelerator.views.ViewOpener;
import ch.dragxfly.quantumaccelerator.hardware.benchmark.CPUBenchmark;
import ch.dragxfly.quantumaccelerator.customControls.CustomToolTip;
import ch.dragxfly.quantumaccelerator.customControls.ToolTipTexts;
import ch.dragxfly.quantumaccelerator.views.MultilingualView;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author jannis
 */
public class ExtrasController extends MultilingualView implements Initializable {

    @FXML
    private Button btnEnableGodmode;
    @FXML
    private Button btnCreateRestorepoint;
    @FXML
    private Button btnOrganizeDesktop;
    @FXML
    private Button btnZipBombIdentifier;
    @FXML
    private Button btnCreateCPULoad;
    @FXML
    private Button btnHelpZipBombIdentifier;
    @FXML
    private Button btnHelpOrganizeDesktop;
    @FXML
    private Button btnHelpGodmode;
    @FXML
    private Button btnHelpRestorepoint;
    @FXML
    private Button btnHelpStressTest;
    @FXML
    private Label lblSecurity;
    @FXML
    private Label lblTesting;
    //non FXML
    private CPUBenchmark benchmark;
    private final CustomToolTip toolTip = CustomToolTip.getInstance();
    private final ExtrasExecutor executor = new ExtrasExecutor();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
    }

    @FXML
    private void openZipBombIdentifier(ActionEvent event) {
        new ViewOpener().openThemeableView("/fxml/ZipBombIdentifier.fxml", "Zip bomb identifier", false);
    }

    @FXML
    private void organizeDesktop(ActionEvent event) {
        new ViewOpener().openThemeableView("/fxml/DesktopOrganizer.fxml", "Desktop organizer", false);
    }

    @FXML
    private void openCreateRestorePoint(ActionEvent event) {
        new ViewOpener().openThemeableView("/fxml/RestorePointCreator.fxml", "Restore", false);
    }

    @FXML
    private void generateCPULoad(ActionEvent event) {
        new ViewOpener().openThemeableView("/fxml/CPUStresstest.fxml", "CPU stress test", false);
    }

    @FXML
    private void enableGodmode(ActionEvent event) {
        executor.createGodModeFolder();
    }

    @FXML
    private void showToolTipZipBomb(MouseEvent event) {
        toolTip.showToolTip(new ToolTipTexts().getZipBombIdentifier(), event);
    }

    @FXML
    private void showToolTipOrganizeDesktop(MouseEvent event) {
        toolTip.showToolTip(new ToolTipTexts().getOrganizeDesktop(), event);
    }

    @FXML
    private void ShowToolTipGodmode(MouseEvent event) {
        toolTip.showToolTip(new ToolTipTexts().getEnableGodMode(), event);
    }

    @FXML
    private void showToolTipRestorepoint(MouseEvent event) {
        toolTip.showToolTip(new ToolTipTexts().getRestorepoint(), event);
    }

    @FXML
    private void showToolTipStressTest(MouseEvent event) {
        toolTip.showToolTip(new ToolTipTexts().getStressTest(), event);

    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.lang", locale);
        lblSecurity.setText(bundle.getString("lblSecurity"));
        btnEnableGodmode.setText(bundle.getString("btnEnableGodmode"));
        btnCreateRestorepoint.setText(bundle.getString("btnCreateRestorepoint"));
        btnOrganizeDesktop.setText(bundle.getString("btnOrganizeDesktop"));
        lblTesting.setText(bundle.getString("lblTesting"));
        btnZipBombIdentifier.setText(bundle.getString("btnZipBombIdentifier"));
        btnCreateCPULoad.setText(bundle.getString("btnCreateCPULoad"));
    }

}
