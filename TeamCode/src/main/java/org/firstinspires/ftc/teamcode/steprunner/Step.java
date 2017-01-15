package org.firstinspires.ftc.teamcode.steprunner;

import java.util.Locale;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 */
public class Step {

    protected Robot robot;
    private Boolean running = false;
    private long startTime = 0;


    /*
        start: method called when Step begins
     */
    public void start(Robot r) {
        robot = r;
        running = true;
        startTime = robot.currentTimeMillis();
        log("start");
    }

    /*
        run: called repeatedly while Step is functioning.
        Subclasses should check for completion and call setDone() if they're done.
     */
    public void run() {
        log("running");
    }

    /*
        isRunning: Returns true if the step wants to continue to run.
     */
    public Boolean isRunning() {
        return running;
    }


    /*
        stop: called after Step's job is done OR to cancel the step. Allows for
        cleanup, etc.
     */
    public void stop() {
        if (isRunning()) {
            log("stop");
            running = false;
        }
    }

    /*
        Returns elapsed time since step start in milliseconds.
        QUESTION: Is this a good name for this method? Should it be shorter,
        like just "elapsed"?
     */
    protected long elapsedTimeMillis() {
        return robot.currentTimeMillis() - startTime;
    }


    /*
        Log a debugging message. Method prefixes message with classname.
        QUESTION: Why is this declared as 'protected' and not 'private'?
     */
    protected void log(String message) {
        String prefix = this.getClass().getSimpleName();
        System.out.println(String.format(Locale.US,
                "%-15s (t=%5d): %s",
                prefix,
                elapsedTimeMillis(),
                message));
        // Log.info(prefix + ":" + message);
    }
    public String getTelemetry(){

        return "Hi";
    }
}
