package ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

/**
 *
 * @author jannis
 */
public class FileDuplicatePair {

    SimpleStringProperty firstFile;
    SimpleStringProperty secondFile;
    Button btnFirstFile, btnSecondFile;

    public FileDuplicatePair(String file1, String file2) {
        this.firstFile = new SimpleStringProperty(file1);
        this.secondFile = new SimpleStringProperty(file2);
    }

    public String getFirstFile() {
        return firstFile.get();
    }

    public String getSecondFile() {
        return secondFile.get();
    }

}
