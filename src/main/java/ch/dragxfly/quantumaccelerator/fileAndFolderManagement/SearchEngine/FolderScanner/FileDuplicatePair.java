package ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

/**
 *
 * @author jannis
 */
public class FileDuplicatePair {
    SimpleStringProperty file1;
    SimpleStringProperty file2;
    Button btnfile1;
    Button btnfile2;
    
    public FileDuplicatePair(String file1, String file2){
        this.file1 = new SimpleStringProperty(file1);
        this.file2 = new SimpleStringProperty(file2);
    }

    public String getFile1() {
        return file1.get();
    }

    public String getFile2() {
        return file2.get();
    }
    
}
