package org.firstinspires.ftc.teamcode.steprunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 * Base class for all StepRunner Steps.
 */
public class Step {

    protected StepRobot robot;
    private Boolean running = false;
    private static Boolean tellConsole = false;

    public static ArrayList<TelMessage> telMessages = new ArrayList<TelMessage>();

    /*
     * Use console, rather than deferred telemetry output, for status messages.
     */
    public static void useConsole(Boolean useConsole) {
        tellConsole = useConsole;
    }

    /*
     * start: method called when Step begins
     */
    public void start(StepRobot r) {
        robot = r;
        running = true;
        tell("start()");
    }

    /*
     * run: called repeatedly while Step is functioning.
     * Subclasses should check for completion and call setDone() if they're done.
     */
    public void run() {
    }

    /*
     * isRunning: Returns true if the step wants to continue to run.
     */
    public Boolean isRunning() {
        return running;
    }


    /*
     * stop: called after Step's job is done OR to cancel the step. Allows for
     * cleanup, etc. Subclasses call super.stop()
     */
    public void stop() {
        running = false;
        tell("stop()");
    }

    /*
     * tell: add a telemetry message
     */
    public void tell(String msg) {
        if (tellConsole) {
            System.out.println(
                    String.format(">> %s: %s",
                            this.getClass().getSimpleName(),
                            msg));
        } else {
            telMessages.add(new TelMessage(
                    this.getClass().getSimpleName(),
                    msg
            ));
        }
    }

    public void tell(String fmt, Object... arguments) {
        tell(String.format(Locale.US, fmt, arguments));
    }

    /*
     * Return all posted telemetry messages.
     */
    public List<TelMessage> getMessages() {
        return telMessages;
    }

}
