package ch.dragxfly.quantumaccelerator.fileAndFolderManagement;

import java.io.File;

/**
 *
 * @author janni
 */
public class DesktopOrganizer {

    public void organizeDesktop() {
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        new File(desktopPath).list();
    }
}
