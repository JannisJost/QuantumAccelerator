package ch.dragxfly.quantumaccelerator.hardware;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.profesorfalken.jsensors.model.sensors.Temperature;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 *
 * @author jannis
 */
public class HardwareObserver extends Observable {

    private Cpu cpu;
    private Timer timer;
    private double cpuTemp = 0, cpuUsage = 0, memoryUsage = 0;
    private Components components;
    private TimerTask observerTask;
    private boolean isMeasureCPUTemp = false;
    private Thread timerThread = new Thread();
    private final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    public void startObserver() {
        observerTask = getObserveTask();
        timerThread = new Thread(observerTask);
        timerThread.start();
    }

    private TimerTask getObserveTask() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                cpuUsage = operatingSystemMXBean.getSystemCpuLoad();
                cpuUsage = new BigDecimal(Double.toString(cpuUsage)).setScale(2, RoundingMode.HALF_UP).doubleValue();
                memoryUsage = calculateMemoryUsage();
                if (cpuUsage > 1) {
                    cpuUsage = 1;
                }
                if (isMeasureCPUTemp) {
                    Platform.runLater(() -> {
                        measureCPUTemp();
                    });
                }
                setChanged();
                notifyObservers();
            }
        };
        timer = new Timer("Timer");
        long delay = 2000;
        timer.schedule(task, 0, delay);
        return task;
    }

    private double calculateMemoryUsage() {
        double freeMemory = operatingSystemMXBean.getFreePhysicalMemorySize();
        double totalMemory = operatingSystemMXBean.getTotalPhysicalMemorySize();
        double freeMemoryInPercent = 100 / totalMemory * freeMemory;
        return 1 - freeMemoryInPercent / 100;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public double getCPUTemp() {
        return cpuTemp;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public void setCheckSpeed(int speed) {
    }

    private void measureCPUTemp() {
        new Thread(getTaskMeasureCPUTemp()
        ).start();
    }

    private Task getTaskMeasureCPUTemp() {
        Task<Void> taskTemp = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                components = JSensors.get.components();
                cpu = components.cpus.get(0);
                List<Temperature> temps = cpu.sensors.temperatures;
                cpuTemp = temps.get(temps.size() - 1).value;
                return null;
            }
        };
        return taskTemp;
    }

    public void setMeasureCPUTemp(boolean measureCPUTemp) {
        this.isMeasureCPUTemp = measureCPUTemp;
    }
}
