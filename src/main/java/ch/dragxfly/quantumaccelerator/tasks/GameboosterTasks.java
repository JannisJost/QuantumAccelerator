package ch.dragxfly.quantumaccelerator.tasks;

import ch.dragxfly.quantumaccelerator.executors.Gamebooster;
import javafx.concurrent.Task;
import shell.CMD;

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
                new CMD().executeCommand(clearStandbyListCommand);
                return null;
            }
        };
        return task;
    }

    public Task getTaskActivatePerformancePlan() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new Gamebooster().setPerfomancePowerPlan();
                return null;
            }
        };
        return task;
    }

    public Task getTaskActivateStandardPlan() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new Gamebooster().setStandardPowerPlan();
                return null;
            }
        };
        return task;
    }

    public Task getTaskActivateEcoPlan() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                new Gamebooster().setEcoPowerPlan();
                return null;
            }
        };
        return task;
    }
}
