package ch.dragxfly.quantumaccelerator.hardware.benchmark;

import java.util.TimerTask;

/**
 *
 * @author janni
 */
public class TimerTaskCheckForCPULoad extends TimerTask {

    private final CPUBenchmark benchmark;
    private boolean isRunning = false;

    public TimerTaskCheckForCPULoad(CPUBenchmark benchmark) {
        this.benchmark = benchmark;
    }

    @Override
    public void run() {
        isRunning = true;
        benchmark.checkIfCPUIsStressed();
        benchmark.checkIfMaxTemp();
    }

    public void stop() {
        if (isRunning) {
            this.cancel();
            isRunning = false;
        }
    }
}
