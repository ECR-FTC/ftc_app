package org.firstinspires.ftc.teamcode.steprunner;

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
     * Do you see a white line?
     */
    abstract public boolean checkWhiteLine();

    /*
     * Set shooter power
     */
    abstract public void setShootPower(double power);

    /*
     * Get color
     */
    abstract public int getColorSeen();

}



