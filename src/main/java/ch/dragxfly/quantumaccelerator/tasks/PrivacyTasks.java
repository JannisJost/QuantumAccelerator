package ch.dragxfly.quantumaccelerator.tasks;

import javafx.concurrent.Task;
import shell.PowerShell;

/**
 *
 * @author jannis
 */
public class PrivacyTasks {

    public Task getDeactivateCamTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new PowerShell().executeCommand("Disable-PnpDevice -InstanceId (Get-PnpDevice -FriendlyName *webcam* -Class Camera -Status OK).InstanceId -Confirm:$false");
                return null;
            }
        };
        return task;
    }

    public Task getActivateCamTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new PowerShell().executeCommand("Enable-PnpDevice -InstanceId (Get-PnpDevice -FriendlyName *webcam* -Class Camera ).InstanceId -Confirm:$false");
                return null;
            }
        };
        return task;
    }
}
