package controller.main;

import ch.dragxfly.quantumaccelerator.ViewManager.ViewOpener;
import ch.dragxfly.quantumaccelerator.Hardware.Benchmark.CPUBenchmark;
import ch.dragxfly.quantumaccelerator.Style.CustomToolTip;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FolderCreator;
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
    private final CustomToolTip toolTip = new CustomToolTip();
    @FXML
    private Button btnCreateCPULoad;
    private CPUBenchmark benchmark;

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
    private void openHelpZipBomb(MouseEvent event) {
        toolTip.showToolTip("A so-called zip bomb is a normal zipped (compressed) file, which in its zipped form isn’t malicious at all."
                + "\nBut if you decide to unzip a zip bomb, a nasty surprise awaits you, because all or a lot of your storage will be filled."
                + "\nThis happens because zip bombs contain files that get compressed down to a minimum of their unzipped size. This tool helps you to identify such files without unzipping them.", event);
    }

    @FXML
    private void generateCPULoad(ActionEvent event) {
        new ViewOpener().openThemeableView("/fxml/CPUStresstest.fxml", "CPU stress test", false);
    }

    @FXML
    private void enableGodmode(ActionEvent event) {
        String godmodePath = System.getProperty("user.home") + "\\Desktop\\Godmode.{ED7BA470-8E54-465E-825C-99712043E01C}";
        FolderCreator folderCreator = new FolderCreator();
        folderCreator.createFolder(godmodePath);
    }

}
