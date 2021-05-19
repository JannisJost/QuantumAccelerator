package ch.dragxfly.quantumaccelerator.Views.tasks;

import shellscripts.PowerShell;
import javafx.concurrent.Task;

/**
 *
 * @author janni
 */
public class PrivacyTasks {

    private final PowerShell ps = new PowerShell();

    public Task getDeactivateCamTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ps.runPowershellScript("deactivateCamera");
                return null;
            }
        };
        return task;
    }

    public Task getActivateCamTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                ps.runPowershellScript("activateCamera");
                return null;
            }
        };
        return task;
    }
}
