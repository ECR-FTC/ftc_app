package org.eastcobbrobotics.ftc.ecrlib.steprunner;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 * <p>
 * This is the abstract base class for StepRunner-compatible robots. Most of the methods
 * here should be overridden in subclasses that know how to do things specific
 * to a particular robot design.
 */


public abstract class StepRobot {


    public StepRobot() {
    }

    /* Constants for color detection */
    public static final int NONE_SEEN = 0;
    public static final int RED_SEEN = 1;
    public static final int BLUE_SEEN = 2;

    /*
     *  Initialize the robot, given some kind of map.
     */
    abstract public void init(Object map) throws InterruptedException;

    /*
     *  Reset and stop all drive motors.
     */
    abstract public void resetDriveMotors();

    /*
     *  Drive straight. Positive power value goes forward, negative backward.
     */
    abstract public void driveStraight(double power);

    /*
     *  Drive sideways. Positive power value goes right, negative left.
     */
    abstract public void driveSideways(double power, int direction);

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

    /*
     * Set servo position
     */
    abstract public void setServo(int servoId, double position);

    /*
     * Get number of encoder ticks per tile.
     */
    abstract public double getTicksPerTile();

}



