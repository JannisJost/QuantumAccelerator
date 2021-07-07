package ch.dragxfly.quantumaccelerator.hardware.benchmark;

import ch.dragxfly.quantumaccelerator.hardware.benchmark.CPULoadGeneratorThread;
import controller.popupwindows.CPUStresstestController;
import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

/**
 *
 * @author jannis
 */
public class CPUBenchmark {

    private final double minLoad;
    private final List<CPULoadGeneratorThread> cpuThreads = new ArrayList<>();
    private final OperatingSystemMXBean osmb = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private TimerTaskCheckForCPULoad task;
    private final Timer timer = new Timer("Timer");
    private final boolean stopIfMaxTemp;
    private Components components;
    private Cpu cpu;
    private double lastMeasuredTemp = 0;
    private final double maxTemp;
    private Task taskTemp;
    private boolean tempTaskIsRunning = false;
    private final CPUStresstestController stressTestController;

    /**
     *
     * @param minLoad value in percent at which the load should stay at
     * @param maxTemp selected maximum temp at which the stress test stops
     * @param stopIfMaxTemp defines if has to stop if "maxTemp" is reached
     * @param stressTestController
     */
    public CPUBenchmark(double minLoad, double maxTemp, boolean stopIfMaxTemp, CPUStresstestController stressTestController) {
        this.stressTestController = stressTestController;
        this.maxTemp = maxTemp;
        this.stopIfMaxTemp = stopIfMaxTemp;
        this.minLoad = minLoad;
    }

    /**
     * Runs the cpu stress test
     */
    public void runCPUBenchmark() {
        task = new TimerTaskCheckForCPULoad(this);
        timer.schedule(task, 0, 250);
        new Thread(task).start();
    }

    /**
     * checks if the cpu has the requested load if not it adjusts it by adding
     * new Threads
     */
    public void checkIfCPUIsStressed() {
        if (osmb.getSystemCpuLoad() < minLoad - 0.05) {
            stressTestController.setStatus("Adding Thread(s)");
            cpuThreads.add(new CPULoadGeneratorThread());
            startNotRunningThread();
        } else if (osmb.getSystemCpuLoad() > minLoad + 0.05) {
            stressTestController.setStatus("Removing Thread(s)");
            if (!cpuThreads.isEmpty()) {
                cpuThreads.get(0).stopLoad();
                cpuThreads.remove(0);
            }
        }
        stressTestController.adjustStressTestStats(cpuThreads.size(), lastMeasuredTemp);
    }

    public void checkIfMaxTemp() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (!tempTaskIsRunning) {
                    taskTemp = getTaskMeasureTemp();
                    new Thread(taskTemp).start();
                    tempTaskIsRunning = true;
                }
            }
        });
    }

    /**
     * Stops the cpu stress load
     */
    public void stopLoad() {
        if (task != null) {
            task.stop();
        }
        for (CPULoadGeneratorThread t : cpuThreads) {
            t.stopLoad();
        }
        cpuThreads.clear();
        System.out.println("Stopped load");
    }

    private void startNotRunningThread() {
        try {
            cpuThreads.get(cpuThreads.size() - 1).start();
        } catch (IllegalThreadStateException e) {

        }
    }

    private Task getTaskMeasureTemp() {
        Task<Void> taskTemp1 = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                components = JSensors.get.components();
                cpu = components.cpus.get(0);
                List<Temperature> temps = cpu.sensors.temperatures;
                lastMeasuredTemp = temps.get(temps.size() - 1).value;
                return null;
            }
        };
        taskTemp1.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                System.out.println("Checked CPU temp: " + lastMeasuredTemp);
                tempTaskIsRunning = false;
                if (lastMeasuredTemp >= maxTemp && stopIfMaxTemp) {
                    stressTestController.overheated();
                    stopLoad();
                }
            }
        });
        return taskTemp1;
    }
}
