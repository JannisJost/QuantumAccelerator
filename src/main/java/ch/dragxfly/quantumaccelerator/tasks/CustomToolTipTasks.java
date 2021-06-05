package ch.dragxfly.quantumaccelerator.tasks;

import ch.dragxfly.quantumaccelerator.Style.CustomToolTip;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author janni
 */
public class CustomToolTipTasks extends Observable {

    private Timer timer;
    private TimerTask timerTask;
    private int passedTime = 0;
    private CustomToolTip toolTip;
    private final BigDecimal percentPerTick = new BigDecimal(0.0083333333333333);

    public void startCountdownTimer(CustomToolTip toolTip) {
        this.toolTip = toolTip;
        this.addObserver(toolTip);
        timerTask = getCountdown();
        new Thread(timerTask).start();
    }

    public void cancelCountdown() {
        timerTask.cancel();
        this.deleteObservers();
    }

    private TimerTask getCountdown() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                passedTime += 1;
                toolTip.setProgressBarTo(percentPerTick.multiply(new BigDecimal(passedTime)).doubleValue());
                if (passedTime == 120) {
                    setChanged();
                    notifyObservers();
                    this.cancel();
                }
            }
        };
        timer = new Timer("Timer");
        long delay = 500;
        timer.schedule(task, 0, delay);
        return task;
    }

}
