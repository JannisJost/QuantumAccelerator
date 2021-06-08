package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

/**
 *
 * @author janni
 */
public class FileOperations {

    public String getExtension(String path) {
        return path.substring(path.lastIndexOf('.') + 1);
    }
}
