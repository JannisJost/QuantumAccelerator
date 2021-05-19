package winbooster.SearchEngine.FolderScanner.Model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.CheckBox;

/**
 *
 * @author janni
 */
public class ScanDoneModel {

    private List<CheckBox> checkBoxes = new ArrayList<>();
    private List<CheckBox> checkedCheckBoxes = new ArrayList<>();
    private double sizeInMBytes = 0;

    public List<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public void setCheckBoxes(List<CheckBox> checkBoxes) {
        this.checkBoxes = checkBoxes;
    }

    public List<CheckBox> getCheckedCheckBoxes() {
        return checkedCheckBoxes;
    }

    public void setCheckedCheckBoxes(List<CheckBox> checkedCheckBoxes) {
        this.checkedCheckBoxes = checkedCheckBoxes;
    }

    public double getSizeInMBytes() {
        return sizeInMBytes;
    }

    public void setSizeInMBytes(double sizeInMBytes) {
        this.sizeInMBytes = sizeInMBytes;
    }

}
