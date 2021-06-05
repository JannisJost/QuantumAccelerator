package ch.dragxfly.quantumaccelerator.fileAndFolderManagement.deleter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileDeleteStrategy;

/**
 *
 * @author janni
 */
public class FileDeleter {

    /**
     * Deletes the given file
     *
     * @param path path pointing to the file to delete
     */
    public void deleteFile(String path) {
        File tempFile = new File(path);
        try {
            FileDeleteStrategy.FORCE.delete(tempFile);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    /**
     * Deletes all the given files
     *
     * @param path list of paths pointing to the files to delete
     */
    public void deleteFiles(List<String> path) {
        for (int i = 0; i < path.size(); i++) {
            File tempFile = new File(path.get(i));
            try {
                FileDeleteStrategy.FORCE.delete(tempFile);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        {
        }
    }
}
