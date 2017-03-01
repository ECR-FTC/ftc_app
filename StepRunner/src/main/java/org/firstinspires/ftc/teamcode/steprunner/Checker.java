package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC on 2/12/2017.
 *
 * Base class for objects that check things (sensors, loop counts, others)
 * for use in control flow Steps.
 */
public class Checker {
    public static final int STOP_CHOICE = -2;
    public static final int CONTINUE_CHOICE = -1;
    public static final int TRUE_CHOICE = 0;
    public static final int FALSE_CHOICE = 1;

    // Initialize the checker
    public void start() {

    }

    public int check() {
        return STOP_CHOICE;
    }
}
