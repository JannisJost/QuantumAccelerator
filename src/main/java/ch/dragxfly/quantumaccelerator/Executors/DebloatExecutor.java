package ch.dragxfly.quantumaccelerator.Executors;

import ch.dragxfly.quantumaccelerator.notifications.LoadingScreen;
import com.profesorfalken.jpowershell.PowerShell;
import java.util.LinkedList;
import java.util.List;
import javafx.concurrent.Task;

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
        for (String appName : appsToDelete) {
            String command = "Get-AppxPackage -Name " + appName + "| Remove-AppxPackage";
            PowerShell.openSession().executeCommand(command);
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
