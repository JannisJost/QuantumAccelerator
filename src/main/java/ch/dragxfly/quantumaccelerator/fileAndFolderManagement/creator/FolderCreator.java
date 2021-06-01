package ch.dragxfly.quantumaccelerator.fileAndFolderManagement.creator;

import java.io.File;

/**
 *
 * @author janni
 */
public class FolderCreator {

    public void createFolder(String path) {
        File toCreate = new File(path);
        if (!toCreate.exists()) {
            toCreate.mkdirs();
        }
    }
}
