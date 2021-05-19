package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

import java.io.File;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author janni
 */
public class FileSizeCalculator {

    public double getFileSize(List<String> folders) {
        int sizeInBytes = getFolderSizeInBytes(folders);
        return getSizeInMBytes(sizeInBytes);
    }

    private int getFolderSizeInBytes(List<String> folders) {
        int size = 0;
        for (String path : folders) {
            File f = new File(path);
            size += FileUtils.sizeOfDirectory(f);
        }
        return size;
    }

    private double getSizeInMBytes(int sizeInBytes) {
        return sizeInBytes / 1048576;
    }
}
