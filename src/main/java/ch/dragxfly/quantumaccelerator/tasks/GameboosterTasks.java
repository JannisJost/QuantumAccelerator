package ch.dragxfly.quantumaccelerator.tasks;

import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.deleter.FileDeleter;
import java.util.List;
import javafx.concurrent.Task;
import shell.CMD;
import ch.dragxfly.quantumaccelerator.fileAndFolderManagement.SearchEngine.FolderScanner.SearchEngine;

/**
 *
 * @author jannis
 */
public class GameboosterTasks {

    public Task getTaskClearStandbyRAM() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String executablePath = System.getProperty("user.dir") + "EmptyStandbyListBy_WenJiaLiu";
                String clearStandbyListCommand = "cd " + executablePath.replace("app", "") + " && .\\EmptyStandbyList.exe standbylist && exit";
                new CMD().executeCmdCommandCMDVisible(clearStandbyListCommand);
                return null;
            }
        };
        return task;
    }

    public Task getTaskDeleteInstallerFromDownload() {
        Task<Void> task = new Task<Void>() {
            SearchEngine searchEngine = new SearchEngine();

            @Override
            protected Void call() throws Exception {
                String downloadsPath = System.getProperty("user.home") + "/Downloads";
                String[] installerNames = {"installer", "setup", ".msi"};
                List<String> installers = searchEngine.searchForFilesContaining(downloadsPath, installerNames);
                FileDeleter fDeleter = new FileDeleter();
                fDeleter.deleteFiles(installers);
                return null;
            }
        };
        return task;
    }
}
