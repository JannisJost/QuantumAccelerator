package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

import java.io.File;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author janni
 */
public class FileSizeCalculator {

    /**
     *
     * @param folders list of folders you need size in mb from
     * @return combined size of all folders in list
     */
    public double getFileSizeInMbytes(List<String> folders) {
        int sizeInBytes = getFolderSizeInBytes(folders);
        return bytesToMBytes(sizeInBytes);
    }

    /**
     *
     * @param folders list of the folders you need the size of
     * @return combined folder size in Bytes
     */
    private int getFolderSizeInBytes(List<String> folders) {
        int size = 0;
        for (String path : folders) {
            File f = new File(path);
            size += FileUtils.sizeOfDirectory(f);
        }
        return size;
    }

    /**
     *
     * @param sizeInBytes file/folder size in bytes
     * @return size in mbytes
     */
    private double bytesToMBytes(int sizeInBytes) {
        return sizeInBytes / 1048576;
    }
}
