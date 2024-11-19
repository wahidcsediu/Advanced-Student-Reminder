package advancedstudytimer;

import java.util.Timer;
import java.util.TimerTask;

public class PomodoroTimer {
    private Timer timer;
    private int secondsRemaining;
    private Runnable onTimerComplete;
    private Runnable onUpdate;

    public PomodoroTimer() {
        timer = new Timer();
    }

    public void startTimer(int seconds, Runnable onTimerComplete, Runnable onUpdate) {
        this.secondsRemaining = seconds;
        this.onTimerComplete = onTimerComplete;
        this.onUpdate = onUpdate;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                    onUpdate.run();
                } else {
                    stopTimer();
                    if (onTimerComplete != null) {
                        onTimerComplete.run();
                    }
                }
            }
        }, 0, 1000);
    }

    public void stopTimer() {
        timer.cancel();
        timer = new Timer();
    }

    public int getSecondsRemaining() {
        return secondsRemaining;
    }
}
