package controller.main;

import ch.dragxfly.quantumaccelerator.Executors.ExtrasExecutor;
import ch.dragxfly.quantumaccelerator.ViewManager.ViewOpener;
import ch.dragxfly.quantumaccelerator.Hardware.Benchmark.CPUBenchmark;
import ch.dragxfly.quantumaccelerator.CustomControls.CustomToolTip;
import ch.dragxfly.quantumaccelerator.CustomControls.ToolTipTexts;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class ExtrasController implements Initializable {

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
    //non FXML
    private CPUBenchmark benchmark;
    private final CustomToolTip toolTip = new CustomToolTip();
    private final ExtrasExecutor executor = new ExtrasExecutor();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void openZipBombIdentifier(ActionEvent event) {
        new ViewOpener().openThemeableView("/fxml/ZipBombIdentifier.fxml", "Zip bomb identifier", false);
    }

    @FXML
    private void organizeDesktop(ActionEvent event) {
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

}
