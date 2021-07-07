package ch.dragxfly.quantumaccelerator.executors;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.creator.FolderCreator;
import java.io.File;

/**
 *
 * @author jannis
 */
public class ExtrasExecutor {

    public void createGodModeFolder() {
        FolderCreator folderCreator = new FolderCreator();
        String godmodeFolderPath = System.getProperty("user.home") + "\\Desktop\\Godmode.{ED7BA470-8E54-465E-825C-99712043E01C}";
        String normalDesktopPath = System.getProperty("user.home") + "\\OneDrive\\Desktop";

        String desktopOneDriveFolderPath = System.getProperty("user.home") + "\\OneDrive\\Desktop";
        if (new File(normalDesktopPath).exists()) {
            //do nothing
        } else if (new File(desktopOneDriveFolderPath).exists()) {
            godmodeFolderPath = System.getProperty("user.home") + "\\OneDrive\\Desktop\\Godmode.{ED7BA470-8E54-465E-825C-99712043E01C}";
        }
        folderCreator.createFolder(godmodeFolderPath);
    }
}
