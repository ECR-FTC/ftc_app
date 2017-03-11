package org.firstinspires.ftc.teamcode.steprunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 * Base class for all StepRunner Steps.
 */
public class Step {

    protected StepRobot robot;
    private Boolean running = false;

    // Stock result codes.
    public static final int RESULT_DONE = 0;
    public static final int RESULT_CONTINUE = 1;
    public static final int RESULT_FAIL = -1;

    private int result = RESULT_DONE;

    private static Boolean tellConsole = false;
    public static ArrayList<TelMessage> telMessages = new ArrayList<TelMessage>();
    private static HashMap<String, Integer> flags = new HashMap<String, Integer>();

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
        setResult(RESULT_DONE);     // assume step ends normally
        tell("starting");
    }

    /*
     * run: called repeatedly while Step is functioning.
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
     * cleanup, etc.
     */
    public void stop() {
        if (running) {
            running = false;
            tell("stopping with result %d", result);
        } else {
            tell("already stopped with result %d", result);
        }
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

    /*
     * Getter and setter for step result
     */
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    /*
     * Get, set, clear global flags
     */
    public void setFlag(String name, int value) {
        flags.put(name, value);
    }

    public Integer getFlag(String name) {
        return flags.get(name);
    }

    public void clearFlag(String name) {
        flags.remove(name);
    }

}
