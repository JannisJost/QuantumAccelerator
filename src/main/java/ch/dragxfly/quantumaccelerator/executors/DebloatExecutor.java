package ch.dragxfly.quantumaccelerator.executors;

import ch.dragxfly.quantumaccelerator.notifications.LoadingScreen;
import java.util.LinkedList;
import java.util.List;
import javafx.concurrent.Task;
import shell.PowerShell;

/**
 *
 * @author janni
 */
public class DebloatExecutor {

    private List<String> appsToDelete = new LinkedList<>();

    public DebloatExecutor(List<String> appsToDelete) {
        this.appsToDelete = appsToDelete;
    }

    public void startDebloating() {
        LoadingScreen loadingScreen = new LoadingScreen("Debloating...");
        loadingScreen.showLoading();
        Task debloatTask = getDebloatingTask();
        debloatTask.setOnSucceeded(event -> {
            loadingScreen.closeScene();
        });
        new Thread(debloatTask).start();
    }

    private void deleteApps() {
        PowerShell ps = new PowerShell();
        for (String appName : appsToDelete) {
            String command = "Get-AppxPackage -Name " + appName + "| Remove-AppxPackage";
            ps.executeCommand(command);
        }
    }

    private Task getDebloatingTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                deleteApps();
                return null;
            }
        };
        return task;
    }

}
