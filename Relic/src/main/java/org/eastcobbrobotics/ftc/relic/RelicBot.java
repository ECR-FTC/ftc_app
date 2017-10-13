package org.eastcobbrobotics.ftc.relic;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * ECR FTC 11096 - 2017-2018 Relic Recovery robot "Junior"
 * <p>
 * This class defines how our robot, Junior, does the things that
 * our autonomous Steps need, like checking sensors, activating
 * motors, etc.
 */

public class RelicBot extends StepRobot {

    // Hardware map provided by opmode
    public HardwareMap hwMap;

    // Interfaces to the devices on the robot

    // Drive motors
    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackRight;
    public DcMotor motorBackLeft;

    public List<DcMotor> driveMotors;

    // Glyphter control
    public DcMotor motorGlyphter;
    public Servo leftGrab;
    public Servo rightGrab;

    // Jewel arm servos
    public Servo leftArmElbow;
    public Servo leftArmWrist;
    public Servo rightArmElbow;
    public Servo rightArmWrist;

    // Sensors
    // public ColorSensor colorRight;

    // List of Servos and their Id's. NOTE THAT THE IDs ARE IN THE
    // ORDER added to servoList below!
    public List<Servo> servoList;
    public final static int LEFT_GRAB_SERVO = 0;
    public final static int RIGHT_GRAB_SERVO = 1;
    public final static int LEFT_ARM_ELBOW_SERVO = 2;
    public final static int RIGHT_ARM_ELBOW_SERVO = 3;
    public final static int LEFT_ARM_WRIST_SERVO = 4;
    public final static int RIGHT_ARM_WRIST_SERVO = 5;

    // Constants
    public static final double TICKS_PER_TILE = 1350;   // encoder ticks for one game tile

    /*
     *   Initialize the robot by getting access to all of its devices through the
     *   hardware map and then setting them to starting values.
     */
    @Override
    public void init(Object map) throws InterruptedException {

        hwMap = (HardwareMap) map;

        // Get access to the devices through the hardware map.
        motorFrontRight = hwMap.dcMotor.get("motorFR");
        motorFrontLeft = hwMap.dcMotor.get("motorFL");
        motorBackRight = hwMap.dcMotor.get("motorBR");
        motorBackLeft = hwMap.dcMotor.get("motorBL");

        // Make a list of the drive motors so we can do things to all of them easily
        driveMotors = asList(motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft);

        // Glyphter components.
        motorGlyphter = hwMap.dcMotor.get("motorGlyphter");
        leftGrab = hwMap.servo.get("servoLeftGrab");
        rightGrab = hwMap.servo.get("servoRightGrab");

        // Jewel arms.
        leftArmElbow = hwMap.servo.get("servoLeftJewel");
        rightArmElbow = hwMap.servo.get("servoRightJewel");

        // TODO: add jewel wrist servos
        leftArmWrist = null;    // hwMap.servo.get("???")
        rightArmWrist = null;   // hwMap.servo.get("???")

        // Make a list of the Servos, so we can refer to them by number.
        // Make sure they are in the right order! SEE COMMENT ABOVE!
        // TODO: add wrist servos by uncommenting line below
        servoList = asList(leftGrab, rightGrab, leftArmElbow, rightArmElbow
                // , leftArmWrist, rightArmWrist
        );

        // Reset all drive motors
        resetDriveMotors();

        // Reset jewel arms
        reset_jewel_arms();

        // Calibrate the gyro.
        // TODO: do this for the new sensor
//        gyro.calibrate();
//        while (gyro.isCalibrating()) {
//            Thread.sleep(50);
//        }
    }

    /*
     *   Configure all drive motors and set them to zero power.
     */
    @Override
    public void resetDriveMotors() {
        for (DcMotor motor : driveMotors) {

            // Docs say this mode does not attempt to use encoders to regulate
            // motor speed.
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // Set the motor to brake on zero power.
            // motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // And set the power to zero.
            motor.setPower(0.0);
        }

        // Left-side motors run in reverse mode
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    /*
     *   Stop driving.
     */
    @Override
    public void driveStop() {
        driveStraight(0.0);
    }

    /*
     *  Set motors to turn left (direction = -1) or right (direction = 1)
     *    at a specified power
     */
    @Override
    public void driveTurn(double power, int direction) {
        double p = power * direction;
        motorFrontRight.setPower(p);
        motorBackRight.setPower(p);
        motorFrontLeft.setPower(-p);
        motorBackLeft.setPower(-p);
    }

    /*
     *   Drive straight at a set power
     */
    @Override
    public void driveStraight(double power) {
        for (DcMotor motor : driveMotors) {
            motor.setPower(power);
        }
    }

    /*
     * Junior doesn't drive sideways. Just stop.
     */
    @Override
    public void driveSideways(double power, int direction) {
        driveStop();
    }

    /*
     * Reset drive motor encoders.
     * TODO: do we have to wait until the encoder values are reset?
     */
    @Override
    public void resetDriveEncoders() {
        for (DcMotor motor : driveMotors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    /*
     * Get drive encoder value. Currently we just read one
     * of the encoders, and we return absolute value.
     *
     * TODO: return average of all four?
     */
    @Override
    public double getDriveEncoderValue() {
        return Math.abs((float) motorFrontRight.getCurrentPosition());

    }

    /*
     * Reset gyro.
     */
    @Override
    public void resetGyro() {
        // TODO: use new sensor
        // gyro.resetZAxisIntegrator();
    }

    /*
     * Get gyro heading.
     */
    @Override
    public double getGyroHeading() {
        // TODO: use new sensor for heading
        // return gyro.getIntegratedZValue();
        return 0.0;
    }

    /*
     * Set servo position
     */
    @Override
    public void setServo(int servoId, double position) {
        servoList.get(servoId).setPosition(position);
    }

    @Override
    public double getTicksPerTile() {
        return TICKS_PER_TILE;
    }

    // Relic-specific methods

    /*
     * Reset jewel arms to stowed position
     * TODO: uncomment and provide appropriate position values
     */
    public void reset_jewel_arms() {
//        leftArmElbow.setPosition(?);
//        rightArmElbow.setPosition(?);
//        leftArmWrist.setPosition(?);
//        rightArmWrist.setPosition(?);
    }
}
