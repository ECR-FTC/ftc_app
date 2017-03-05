package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.steprunner.StepRobot;

import java.util.Locale;

/**
 * Created by ECR FTC on 2/28/17.
 */

public class MockRobot extends StepRobot {

    protected double leftMotorPower = 0.0;
    protected double rightMotorPower = 0.0;

    // Modeling the encoders. We say the robot goes 4000 ticks per second
    // at full power.
    protected double leftEncoder = 0.0;
    protected double rightEncoder = 0.0;
    protected double lastModelCheckTime;
    protected static final double TICKS_PER_SECOND = 4000;

    // Modeling the gyro. We say the robot turns 90 degrees per second
    // at full power
    protected double gyroHeading = 0.0;
    protected static final double DEGREES_PER_SECOND = 90;


    @Override
    public void init(Object map) throws InterruptedException {
        say("init");
    }

    @Override
    public void resetDriveMotors() {
        say("resetDriveMotors");
        updateModel();
        leftMotorPower = 0.0;
        rightMotorPower = 0.0;
    }

    @Override
    public void driveStraight(double power) {
        say(String.format(Locale.US, "driveStraight power=%.2f", power));
        updateModel();
        leftMotorPower = power;
        rightMotorPower = power;

    }

    @Override
    public void driveStop() {
        say("driveStop");
        updateModel();
        leftMotorPower = 0.0;
        rightMotorPower = 0.0;
    }

    @Override
    public void driveTurn(double power, int direction) {
        say(String.format(Locale.US, "driveTurn power=%.2f, direction=%s",
                power,
                (direction < 0 ? "left" : direction > 0 ? "right" : "?")
        ));
        updateModel();
        leftMotorPower = power * Math.signum(direction);
        rightMotorPower = power * -Math.signum(direction);

    }

    @Override
    public void resetDriveEncoders() {
        say("resetDriveEncoders");
        updateModel();
        leftEncoder = 0.0;
        rightEncoder = 0.0;
        lastModelCheckTime = System.currentTimeMillis();
    }

    @Override
    public double getDriveEncoderValue() {
        updateModel();
        double val = rightEncoder;
        say(String.format(Locale.US, "getDriveEncoderValue=%.2f", val));
        return val;
    }

    @Override
    public void resetGyro() {
        updateModel();
        gyroHeading = 0;
        say("resetGyro");

    }

    @Override
    public double getGyroHeading() {
        updateModel();
        say(String.format(Locale.US, "getGyroHeading=%.2f", gyroHeading));
        return gyroHeading;
    }

    @Override
    public void setServo(int servoId, double position) {
        updateModel();
        say(String.format("setServo: servoId=%d, position=%.2f", servoId, position));

    }

    @Override
    public boolean checkWhiteLine() {
        return false;
    }

    @Override
    public void setShootPower(double power) {
        updateModel();
        say(String.format("setShootPower: power=%.2f", power));
    }

    /*
             * Update the model.
             */
    protected void updateModel() {
        double now = System.currentTimeMillis();
        double elapsed = now - lastModelCheckTime;
        lastModelCheckTime = now;

        // How far have we rolled?
        double ticks = TICKS_PER_SECOND * elapsed / 1000.0;
        rightEncoder += ticks * rightMotorPower;
        leftEncoder += ticks * leftMotorPower;

        // How far have we turned? Dumb model for now.
        double leftDir = Math.signum(leftMotorPower);
        double rightDir = Math.signum(rightMotorPower);
        if (leftDir != rightDir) {
            double degrees = DEGREES_PER_SECOND * elapsed / 1000.0;
            gyroHeading += (degrees * leftMotorPower) % 360.0;
        }

    }

    protected void say(String msg) {
        System.out.println("MockRobot: " + msg);
    }
}
