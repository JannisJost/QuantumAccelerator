package controller.popupwindows;

import ch.dragxfly.quantumaccelerator.customControls.ButtonTableCell;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.DoubleFileFinder;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner.FileDuplicatePair;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.chooser.FolderChooser;
import ch.dragxfly.quantumaccelerator.views.ThemeableWindow;
import controller.popupwindows.warning.InfoDecisionWindow;
import controller.popupwindows.warning.InfoWindow;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import shell.CMD;

/**
 *
 * @author jannis
 */
public class DoubleFileFinderController extends ThemeableWindow implements Initializable, Observer {

    @FXML
    private Button btnClose;
    @FXML
    private CheckBox chkEqualLastModified;
    @FXML
    private Label lblOptions;
    @FXML
    private Label lblStartPath;
    @FXML
    private TextField txtStartPath;
    @FXML
    private Button btnSelectStartPath;
    @FXML
    private Label lblFilter;
    @FXML
    private CheckBox chkEqualSize;
    @FXML
    private CheckBox chkEqualContent;
    @FXML
    private TableColumn colCheckBox;
    @FXML
    private TableColumn colFile1;
    @FXML
    private TableColumn colFile2;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnCancel;
    @FXML
    private TableView<FileDuplicatePair> tblDuplicates;
    @FXML
    private ProgressIndicator progIndicatorIsSearching;
    //non FXML
    private double xOffset = 0;
    private double yOffset = 0;
    private FolderChooser folderChooser;
    private DoubleFileFinder finder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage(super.getLanguage());
        finder = new DoubleFileFinder(this);
        progIndicatorIsSearching.visibleProperty().bind(btnSearch.disableProperty());
        colCheckBox.setCellFactory(ButtonTableCell.<FileDuplicatePair>forTableColumn("Explorer", (FileDuplicatePair pair) -> {
            CMD cmd = new CMD();
            String command1 = "explorer.exe /select, " + pair.getFile1();
            String command2 = "explorer.exe /select, " + pair.getFile2();
            cmd.executeCmdCommand(command1);
            cmd.executeCmdCommand(command2);
            return pair;
        }));
        btnCancel.disableProperty().bind(btnSearch.disableProperty().not());
        folderChooser = new FolderChooser("Start path", this);
        colFile1.setCellValueFactory(new PropertyValueFactory<>("file1"));
        colFile2.setCellValueFactory(new PropertyValueFactory<>("file2"));
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        finder.cancelScan();
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void selectStartPath(ActionEvent event) {
        folderChooser.show();
    }

    @FXML
    private void searchDoubleFiles(ActionEvent event) {
        Locale locale = new Locale(super.getLanguage());
        ResourceBundle bundle = ResourceBundle.getBundle("languages.warnings.warnings", locale);
        if (txtStartPath.getText().isEmpty()) {
            new InfoWindow().ShowInfoWindow(bundle.getString("noPath"));
        } else if (txtStartPath.getText().length() < 20) {
            boolean doContinue = new InfoDecisionWindow().ShowInfoWindow(bundle.getString("shortPath"));
            if (doContinue) {
                startDoubleScan();
            }
        } else {
            startDoubleScan();
        }

    }

    private void startDoubleScan() {
        btnSearch.setDisable(true);
        tblDuplicates.getItems().clear();
        finder.findDoubleFiles(txtStartPath.getText(), chkEqualSize.isSelected(), chkEqualContent.isSelected(), chkEqualLastModified.isSelected());
    }

    @Override
    public void setTheme() {
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }

    @Override
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("languages.popup", locale);
        lblOptions.setText(bundle.getString("lblOptions"));
        lblStartPath.setText(bundle.getString("lblStartPath"));
        btnSearch.setText(bundle.getString("btnSearch"));
        btnCancel.setText(bundle.getString("btnCancel"));
        btnSelectStartPath.setText(bundle.getString("btnSelectStartPath"));
        lblFilter.setText(bundle.getString("lblFilter"));
        chkEqualSize.setText(bundle.getString("chkEqualSize"));
        chkEqualContent.setText(bundle.getString("chkEqualContent"));
        chkEqualLastModified.setText(bundle.getString("chkEqualLastModified"));
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
    public void update(Observable o, Object arg) {
        if (o.getClass().equals(FolderChooser.class)) {
            txtStartPath.setText((String) arg);
        }
    }

    public void addDuplicate(FileDuplicatePair duplicate) {
        tblDuplicates.getItems().add(duplicate);
    }

    @FXML
    private void cancelScan(ActionEvent event) {
        if (finder != null) {
            finder.cancelScan();
        }
    }

    public void scanDone() {
        btnSearch.setDisable(false);
    }

}
