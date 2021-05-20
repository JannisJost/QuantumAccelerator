package ch.dragxfly.quantumaccelerator.tasks;

import com.profesorfalken.jpowershell.PowerShell;
import javafx.concurrent.Task;

/**
 *
 * @author janni
 */
public class PrivacyTasks {

    public Task getDeactivateCamTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                PowerShell.executeSingleCommand("Disable-PnpDevice -InstanceId (Get-PnpDevice -FriendlyName *webcam* -Class Camera -Status OK).InstanceId -Confirm:$false");
                return null;
            }
        };
        return task;
    }

    public Task getActivateCamTask() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                PowerShell.executeSingleCommand("Enable-PnpDevice -InstanceId (Get-PnpDevice -FriendlyName *webcam* -Class Camera ).InstanceId -Confirm:$false");
                return null;
            }
        };
        return task;
    }
}
