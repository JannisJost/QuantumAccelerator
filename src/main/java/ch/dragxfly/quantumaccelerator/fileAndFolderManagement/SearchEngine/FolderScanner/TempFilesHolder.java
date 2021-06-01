package ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author janni
 */
public class TempFilesHolder {

    private final static TempFilesHolder INSTANCE = new TempFilesHolder();
    private List<String> tempFiles = new ArrayList<>();

    TempFilesHolder() {
    }

    public TempFilesHolder getInstance() {
        return INSTANCE;
    }

    public void setTempFiles(List<String> tempFiles) {
        this.tempFiles = tempFiles;
    }

    public List<String> getTempFiles() {
        return tempFiles;
    }

    public int getNumberOfTempFiles() {
        return tempFiles.size();
    }
}
