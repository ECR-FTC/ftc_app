package org.firstinspires.ftc.teamcode.steprunner;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 * <p>
 * This is the abstract base class for StepRunner-compatible robots. Most of the methods
 * here should be overridden in subclasses that know how to do things specific
 * to a particular robot design.
 */


public abstract class StepRobot {


    public HardwareMap hwMap;   // Hardware map provided by opmode

    public StepRobot() {
    }

    /*
     *  Initialize the robot. Subclasses MUST call super.init first before
     *  doing their own thing.
     */
    public void init(HardwareMap hwMap) throws InterruptedException {
        this.hwMap = hwMap;
    }

    /*
     *   Abstract methods used by Steps -- these need to be overridden by subclasses
     */

    /*
     *  Reset and stop all drive motors.
     */
    abstract public void resetDriveMotors();

    /*
     *  Drive straight. Positive power value goes forward, negative backward.
     */
    abstract public void driveStraight(double power);

    /*
     *  Stop driving.
     */
    abstract public void driveStop();

    /*
     *  Set motors to turn left or right (left = direction < 0; right = direction > 0)
     *    at a specified power
     */
    abstract public void driveTurn(double power, int direction);

    /*
     * Reset drive motor encoders.
     */
    abstract public void resetDriveEncoders();

    /*
     * Get drive encoder value.
     */
    abstract public double getDriveEncoderValue();

    /*
     * Reset gyro.
     */
    abstract public void resetGyro();

    /*
     * Get gyro heading.
     */
    abstract public double getGyroHeading();


    // TODO: get/set servo
    // TODO: get sensor values
}