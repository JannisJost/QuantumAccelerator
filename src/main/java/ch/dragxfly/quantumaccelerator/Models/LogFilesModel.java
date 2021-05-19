package Models;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.CheckBox;

/**
 *
 * @author janni
 */
public class LogFilesModel {

    private List<String> logFiles = new ArrayList<>();
    private List<CheckBox> checkBoxes = new ArrayList<>();

    public List<String> getLogFiles() {
        return logFiles;
    }

    public void setLogFiles(List<String> logFiles) {
        this.logFiles = logFiles;
    }

    public List<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public void setCheckBoxes(List<CheckBox> checkBoxes) {
        this.checkBoxes = checkBoxes;
    }

}
