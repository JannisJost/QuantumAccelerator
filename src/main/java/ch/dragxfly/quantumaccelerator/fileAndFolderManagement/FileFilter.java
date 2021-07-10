package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

/**
 *
 * @author jannis
 */
public class FileFilter {

    private boolean folderOnly;
    private String allowedEnding = "";

    public FileFilter() {

    }

    public FileFilter(boolean folderOnly) {
        this.folderOnly = folderOnly;
    }

    public FileFilter(String ending) {
        this.allowedEnding = ending;
    }

    public boolean isFolderOnly() {
        return folderOnly;
    }

    public String getAllowedEnding() {
        return allowedEnding;
    }

}
