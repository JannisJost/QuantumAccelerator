package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

/**
 *
 * @author jannis
 */
public class ApplicationFilesHelper {

    public String getInstallationPath() {
        return System.getProperty("user.dir").replace("app", "");
    }
}
