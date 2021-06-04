package ch.dragxfly.quantumaccelerator.fileAndFolderManagement.creator;

import java.io.File;

/**
 *
 * @author janni
 */
public class FolderCreator {
/**
 * Creates all folders if they dont already exist with a given path
 * @param path 
 */
    public void createFolder(String path) {
        File toCreate = new File(path);
        if (!toCreate.exists()) {
            toCreate.mkdirs();
        }
    }
}
