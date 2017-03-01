package org.firstinspires.ftc.teamcode.steprunner;


/**
 * Created by ECR FTC on 2/26/17.
 *
 * Checker using sensors
 */

public class SensorChecker extends Checker {

    protected StepRobot robot;

    // TODO: how to specify which sensor(s) to check? Or different classes for each?

    SensorChecker(StepRobot robot) {
        this.robot = robot;
    }

    // Initialize the checker
    public void start() {

    }

    // TODO: read sensor value and return appropriate result
    public int check() {
        return STOP_CHOICE;
    }
}
