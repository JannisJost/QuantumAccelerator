package ch.dragxfly.quantumaccelerator.tasks;

import java.util.Date;
import javafx.concurrent.Task;
import shell.CMD;

/**
 *
 * @author janni
 */
public class RestorepointTasks {

    public Task getTaskCreateRestorePoint() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Date d = new Date();
                String date = Integer.toString(d.getMonth() + d.getDay() + d.getMinutes());
                String command = "wmic.exe /Namespace:\\\\root\\default Path SystemRestore Call CreateRestorePoint \"<QuantumAccelerator" + date + ">\", 100, 7";
                CMD cmd = new CMD();
                cmd.executeCommand(command);
                return null;
            }
        };
        return task;
    }
}
