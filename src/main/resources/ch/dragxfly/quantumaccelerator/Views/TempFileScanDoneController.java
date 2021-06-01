package ch.dragxfly.quantumaccelerator.Views;

import Models.TempfileModel;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner.TempFilesHolder;

/**
 * FXML Controller class
 *
 * @author janni
 */
public class TempFileScanDoneController implements Initializable {

    @FXML
    private Button btnDeleteSelectedFiles;
    @FXML
    private Button btnCancel;
    private TempfileModel model;
    private List<CheckBox> chkboxes;
    @FXML
    private ListView<CheckBox> lstTempFiles;
    private TempFilesHolder tempFilesHolder;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tempFilesToList();
    }

    public void setTempFilesHolder(TempFilesHolder tempFilesHolder) {
        this.tempFilesHolder = tempFilesHolder;
    }

    /**
     *Adds all the temp folders to the displayed listview
     */
    private void tempFilesToList() {
        for (String s : model.getTempFilesList()) {
            chkboxes.add(new CheckBox(s));
        }
        for (CheckBox chk : chkboxes) {
            chk.setSelected(true);
        }
        lstTempFiles.getItems().addAll(chkboxes);
    }

    private Task getTaskTempToList() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                return null;
            }
        };
        return task;
    }

    @FXML
    private void cancel(ActionEvent event) {
    }
}
