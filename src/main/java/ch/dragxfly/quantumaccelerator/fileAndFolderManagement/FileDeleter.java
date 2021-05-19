package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileDeleteStrategy;

/**
 *
 * @author janni
 */
public class FileDeleter {

    public void deleteFile(String path) {
        File tempFile = new File(path);
        try {
            FileDeleteStrategy.FORCE.delete(tempFile);
        } catch (IOException e) {
            System.err.println(e);
        }
        System.out.println("File " + path + " deleted successfully");
    }

    public void deleteFile(List<String> path) {
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
