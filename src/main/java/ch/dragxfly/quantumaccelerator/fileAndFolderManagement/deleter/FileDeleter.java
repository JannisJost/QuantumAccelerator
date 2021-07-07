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
        File fileToDelete = new File(path);
        try {
            if (fileToDelete.exists()) {
                FileDeleteStrategy.FORCE.delete(fileToDelete);
            }
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
            File fileToDelete = new File(path.get(i));
            try {
                if (fileToDelete.exists()) {
                    FileDeleteStrategy.FORCE.delete(fileToDelete);
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        }
        {
        }
    }

    public void deleteAllFilesInDirectory(String pathToDirectory) {
        if (new File(pathToDirectory).exists()) {
            String[] filesToDelete = new File(pathToDirectory).list();
            for (int i = 0; i <= filesToDelete.length - 1; i++) {
                deleteFile(pathToDirectory + "\\" + filesToDelete[i]);
            }
        }
    }
}
