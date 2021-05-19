package Hardware.Benchmark;

import java.util.Random;

/**
 *
 * @author janni
 */
public class CPULoadGeneratorThread extends Thread {

    private final Random rnd = new Random();
    private double moreCalculation;
    private boolean running = true;
    private boolean started = false;

    public CPULoadGeneratorThread() {
        this.moreCalculation = 1;
    }

    public void stopLoad() {
        this.stop();
        running = false;
    }

    @Override
    public void run() {
        started = true;
        while (running) {
            double random = rnd.nextFloat();
            double someCalculation =Math.sin(Math.cos(Math.sin(Math.cos(random))));
            moreCalculation *= someCalculation;
        }
    }

    public boolean isStarted() {
        return started;
    }

}
