package ch.dragxfly.quantumaccelerator.Executors;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.creator.FolderCreator;
import java.io.File;

/**
 *
 * @author janni
 */
public class ExtrasExecutor {

    public void createGodModeFolder() {
        FolderCreator folderCreator = new FolderCreator();
        String godmodePath = System.getProperty("user.home") + "\\Desktop\\Godmode.{ED7BA470-8E54-465E-825C-99712043E01C}";
        if (new File(godmodePath).exists()) {
        } else {
            godmodePath = System.getProperty("user.home") + "OneDrive\\Desktop\\Godmode.{ED7BA470-8E54-465E-825C-99712043E01C}";
        }
        folderCreator.createFolder(godmodePath);
    }
}
