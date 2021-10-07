package ch.dragxfly.quantumaccelerator.executors;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FileOperations;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.deleter.FileDeleter;
import controller.popupwindows.warning.InfoWindow;
import java.io.File;
import javafx.concurrent.Task;

/**
 *
 * @author jannis
 */
public class StorageExecutor {

    private final FileDeleter deleter = new FileDeleter();

    public void run(boolean deleteDownloadInstallers, boolean deleteExplorerThumbnails) {
        Task t1 = new Task() {
            @Override
            protected Object call() throws Exception {
                if (deleteDownloadInstallers) {
                    deleteInstallerFromDownloads();
                }
                if (deleteExplorerThumbnails) {
                    deleteExplorerThumbnails();
                }
                return null;
            }
        };
        new Thread(t1).start();
        t1.setOnSucceeded(event -> {
            new InfoWindow().ShowInfoWindow("Performed all selected");
        });
    }

    private void deleteInstallerFromDownloads() {
        try {
            String osDrive = System.getenv("SystemDrive");
            String userName = System.getProperty("user.name");
            File downloadsLocation = new File(osDrive + "\\Users\\" + userName + "\\Downloads");
            File[] filesInDownload = downloadsLocation.listFiles();
            for (File file : filesInDownload) {
                if ("msi".equals(new FileOperations().getExtension(file.getAbsolutePath())) || file.getName().toLowerCase().contains("installer") || file.getName().toLowerCase().contains("install") || file.getName().toLowerCase().contains("setup") && file.canExecute()) {
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
            if (thumbnailCache[i].getName().toLowerCase().contains("thumbcache")) {
                deleter.deleteFile(thumbnailCache[i].getAbsolutePath());
            }
        }
    }

}
