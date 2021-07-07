package ch.dragxfly.quantumaccelerator.executors;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.deleter.FileDeleter;
import ch.dragxfly.quantumaccelerator.notifications.LoadingScreen;
import java.io.File;

/**
 *
 * @author jannis
 */
public class StorageExecutor {

    private final FileDeleter deleter = new FileDeleter();

    public void run(boolean deleteDownloadInstallers, boolean deleteExplorerThumbnails) {
        LoadingScreen loadingScreen = new LoadingScreen("Applying storage options");
        loadingScreen.showLoading();
        if (deleteDownloadInstallers) {
            deleteInstallerFromDownloads();
        }
        if (deleteExplorerThumbnails) {
            deleteExplorerThumbnails();
        }
        loadingScreen.closeScene();
    }

    private void deleteInstallerFromDownloads() {
        try {
            String osDrive = System.getenv("SystemDrive");
            String userName = System.getProperty("user.name");
            File folder = new File(osDrive + "\\Users\\" + userName + "\\Downloads");
            File[] filesInDownload = folder.listFiles();
            for (File file : filesInDownload) {
                if (file.getName().contains("Installer") || file.getName().contains("install") || file.getName().contains("installer") || file.getName().contains("setup") && file.canExecute()) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    private void deleteExplorerThumbnails() {
        String appdataFolder = System.getenv("APPDATA").replace("Roaming", "");
        String thumbnailCacheLocation = appdataFolder + "\\Local\\Microsoft\\Windows\\Explorer";
        File[] thumbnailCache = new File(thumbnailCacheLocation).listFiles();
        for (int i = 0; i <= thumbnailCache.length - 1; i++) {
            if (thumbnailCache[i].getName().contains("thumbcache")) {
                deleter.deleteFile(thumbnailCache[i].getAbsolutePath());
            }
        }
    }

}
