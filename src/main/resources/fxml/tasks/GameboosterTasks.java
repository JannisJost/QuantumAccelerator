package ch.dragxfly.quantumaccelerator.Views.tasks;

import shellscripts.CMD;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.FileDeleter;
import java.util.List;
import javafx.concurrent.Task;
import winbooster.SearchEngine.FolderScanner.SearchEngine;

/**
 *
 * @author janni
 */
public class GameboosterTasks {

    public Task getTaskClearStandbyRAM() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String executablePath = System.getProperty("user.dir") + "\\src\\EmptyStandbyListBy_WenJia_Liu";
                String command = "cd " + executablePath + " && .\\EmptyStandbyList.exe standbylist && exit";
                new CMD().executeCmdCommandCMDVisible(command);
                return null;
            }
        };
        return task;
    }

    public Task getTaskDeleteInstallerFromDownload() {
        Task<Void> task = new Task<Void>() {
            SearchEngine engine = new SearchEngine();

            @Override
            protected Void call() throws Exception {
                String downloadsPath = System.getProperty("user.home") + "/Downloads";
                String[] installerNames = {"installer", "setup", ".msi"};
                engine.searchForFilesContaining(downloadsPath, installerNames);
                List<String> installers = engine.getRequested();
                FileDeleter fDeleter = new FileDeleter();
                fDeleter.deleteFile(installers);
                return null;
            }
        };
        return task;
    }
}
