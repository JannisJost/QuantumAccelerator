package controller.popupwindows.fileandfolder;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FileFilter;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FileOperations;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.chooser.FileChooser;
import ch.dragxfly.quantumaccelerator.views.ThemeableWindow;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

public class FileChooserController extends ThemeableWindow implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnDesktop;
    @FXML
    private Button btnDownloads;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnUpADirectory;
    @FXML
    private TextField txtSelectedFile;
    @FXML
    private ListView<Label> lstFilesAndFolders;
    @FXML
    private Button btnToRoot;
    @FXML
    private Button btnToDocuments;
    //non FXML
    private double xOffset = 0;
    private double yOffset = 0;
    private String currentDirectory = "C:\\";
    private final List<Observer> observers = new LinkedList<>();
    private FileChooser invocator;
    private FileFilter filter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setInvocator(FileChooser invocator) {
        this.invocator = invocator;
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void moveToDesktop(ActionEvent event) {
        currentDirectory = System.getProperty("user.home") + "\\Desktop";
        refresh();
    }

    @FXML
    private void moveToDownloads(ActionEvent event) {
        currentDirectory = System.getProperty("user.home") + "\\Downloads";
        refresh();
    }

    @FXML
    private void cancel(ActionEvent event) {
        btnClose.fire();
    }

    @FXML
    private void select(ActionEvent event) {
        if (filter.isFolderOnly()) {
            invocator.fileHasBeenChoosen(txtSelectedFile.getText());
        } else if (new File(txtSelectedFile.getText()).isFile() && new FileOperations().getExtension(txtSelectedFile.getText()).equals(filter.getAllowedEnding())) {
            invocator.fileHasBeenChoosen(txtSelectedFile.getText());
        }
    }

    @FXML
    private void moveToParentDirectory(ActionEvent event) {
        if (currentDirectory.length() > 3) {
            currentDirectory = currentDirectory.substring(0, currentDirectory.lastIndexOf("\\"));
            System.out.println(currentDirectory);
            refresh();
        }
    }

    private void openNewDirectory(String directory) {
        if (new File(directory).exists() && new File(directory).isDirectory()) {
            currentDirectory = directory;
            refresh();
        }
    }

    public void refresh() {
        lstFilesAndFolders.getItems().clear();
        txtSelectedFile.setText(currentDirectory);
        List<String> filesAndFolders = Arrays.asList(new File(currentDirectory).list());
        for (String file : filesAndFolders) {
            Label l = new Label("");
            if (new File(currentDirectory + "\\" + file).isDirectory()) {
                l = new Label(file, new ImageView("/styles/icons/file/folder.png"));
            } else {
                if (!filter.isFolderOnly()) {
                    if (!"".equals(filter.getAllowedEnding()) && new FileOperations().getExtension(file).equals(filter.getAllowedEnding())) {
                        l = new Label(file, new ImageView("/styles/icons/file/file.png"));
                    }
                }
            }
            if (!file.startsWith("$") && !"".equals(l.getText())) {
                lstFilesAndFolders.getItems().add(l);
            }
            l.setMaxWidth(Double.MAX_VALUE);
            l.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Label label = (Label) event.getSource();
                    if (event.getClickCount() == 1) {
                        txtSelectedFile.setText(currentDirectory + "\\" + label.getText());
                    } else if (event.getClickCount() == 2) {
                        openNewDirectory(currentDirectory + "\\" + label.getText());
                    }
                }
            });
        }
    }

    @FXML
    private void moveToRoot(ActionEvent event) {
        currentDirectory = "C:\\";
        refresh();
    }

    @FXML
    private void moveToDocuments(ActionEvent event) {
        currentDirectory = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        refresh();
    }

    @Override
    public void setTheme() {
        Scene scene = btnClose.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(super.getPref().get(ThemeableWindow.getCURRENTTHEME(), ""));
    }

    @Override
    public void setLanguage(String lang) {
    }

    @FXML
    private void moveWindow(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        xOffset = currentStage.getX() - event.getScreenX();
        yOffset = currentStage.getY() - event.getScreenY();
    }

    @FXML
    private void moveWindowSecond(MouseEvent event) {
        Stage currentStage = (Stage) btnClose.getScene().getWindow();
        currentStage.setX(event.getScreenX() + xOffset);
        currentStage.setY(event.getScreenY() + yOffset);
    }

    public void setFilter(FileFilter filter) {
        this.filter = filter;
    }

}
