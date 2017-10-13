package org.firstinspires.ftc.teamcode;

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
 * ECR FTC 11096 - 2016-2017 Velocity Vortex robot "Morgana"
 * <p>
 * This class defines how our robot, Morgana, does the things that
 * our autonomous Steps need, like checking sensors, activating
 * motors, etc.
 */

public class MorganaBot extends StepRobot {

    // Hardware map provided by opmode
    public HardwareMap hwMap;

    // Interfaces to the devices on the robot

    // Drive motors
    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackRight;
    public DcMotor motorBackLeft;

    public List<DcMotor> driveMotors;

    // Shooter controls
    public DcMotor motorShoot;
    public Servo leftServo;
    public Servo rightServo;
    public Servo loadServo;
    public Servo fireServo;

    // Sensors
    public ColorSensor colorRight;
    public ModernRoboticsI2cGyro gyro;
    public OpticalDistanceSensor odSensor;
    public AnalogInput rightRedSensor;
    public AnalogInput rightBlueSensor;
    public AnalogInput leftRedSensor;
    public AnalogInput leftBlueSensor;

    // List of Servos and their Id's.
    public List<Servo> servoList;
    public final static int LEFT_SERVO = 0;
    public final static int RIGHT_SERVO = 1;
    public final static int LOAD_SERVO = 2;
    public final static int FIRE_SERVO = 3;

    // Constants
    public static final double LEFT_PRESS = 0.9;  // left button pusher "on" value
    public static final double RIGHT_PRESS = 0.1;  // right button pusher "on" value
    public static final double LEFT_STORE = 0.3;  // left button pusher "off" value
    public static final double RIGHT_STORE = 0.65; // right button pusher "off" value
    public static final double LEFT_SCAN = 0.6;     // left pusher scanning position
    public static final double RIGHT_SCAN = 0.38;     // right pusher scanning position

    public static final double FIRE_GO = 0.5;  // setting to fire a ball into the launcher
    public static final double FIRE_STAY = 0.0;  // down setting for the fire servo
    public static final double LOAD_CLOSED = 0.1;  // "up" setting for the ball loader
    public static final double LOAD_OPEN = 0.8;  // "down" setting for the ball loader
    public static final double FIRE_SERVO_TIME = 0.5;  // delay time for the firing servo
    public static final double WHITE_LINE_THRESHOLD = 0.2;      // above this means we see white line
    public static final double RED_THRESHOLD = 0.2;     // above this means we see red
    public static final double BLUE_THRESHOLD = 0.2;     // above this means we see blue
    public static final double TICKS_PER_TILE = 1350;   // encoder ticks for one game tile
    public static final int SHOOTER_MAX_TPS = 1000;  // maximum TPS for shooter

    /*
     *   Initialize the robot by getting access to all of its devices through the
     *   hardware map and then setting them to starting values.
     */
    @Override
    public void init(Object map) throws InterruptedException {

        hwMap = (HardwareMap) map;

        // Get access to the devices through the hardware map.
        motorFrontRight = hwMap.dcMotor.get("frontRight");
        motorFrontLeft = hwMap.dcMotor.get("frontLeft");
        motorBackRight = hwMap.dcMotor.get("backRight");
        motorBackLeft = hwMap.dcMotor.get("backLeft");

        // Make a list of the drive motors so we can do things to all of them easily
        driveMotors = asList(motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft);

        motorShoot = hwMap.dcMotor.get("shootMotor");
        leftServo = hwMap.servo.get("servoLeft");
        rightServo = hwMap.servo.get("servoRight");
        loadServo = hwMap.servo.get("servoLoad");
        fireServo = hwMap.servo.get("servoFire");

        gyro = (ModernRoboticsI2cGyro) hwMap.gyroSensor.get("sensorGyro");
        colorRight = hwMap.colorSensor.get("sensorColorRight");
        odSensor = hwMap.opticalDistanceSensor.get("sensorODS");
        rightRedSensor = hwMap.analogInput.get("rightRed");
        rightBlueSensor = hwMap.analogInput.get("rightBlue");
        leftRedSensor = hwMap.analogInput.get("leftRed");
        leftBlueSensor = hwMap.analogInput.get("leftBlue");

        // Make a list of the Servos, so we can refer to them by number.
        // Make sure they are in the right order.
        servoList = asList(leftServo, rightServo, loadServo, fireServo);

        // Reset all drive motors
        resetDriveMotors();

        // Turn off the LED on the right color sensor.
        colorRight.enableLed(false);

        // Calibrate the gyro.
        gyro.calibrate();
        while (gyro.isCalibrating()) {
            Thread.sleep(50);
        }

        // Set the button pushing servos to the store positions.
        rightServo.setPosition(RIGHT_STORE);
        leftServo.setPosition(LEFT_STORE);
        fireServo.setPosition(FIRE_STAY);
        loadServo.setPosition(LOAD_CLOSED);

        // By default, use internal encoder control on shooter motor.
        useInternalShooterPID(true);

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
 *  Set motors to drive (direction = -1) or right (direction = 1)
 *    at a specified power
 */
    @Override
    public void driveSideways(double power, int direction) {

        double p = power * direction;
        motorFrontRight.setPower(p);
        motorBackRight.setPower(-p);
        motorFrontLeft.setPower(-p);
        motorBackLeft.setPower(p);
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
        gyro.resetZAxisIntegrator();
    }

    /*
     * Get gyro heading.
     */
    @Override
    public double getGyroHeading() {
        return gyro.getIntegratedZValue();
    }

    /*
     * Set servo position
     */
    @Override
    public void setServo(int servoId, double position) {
        servoList.get(servoId).setPosition(position);
    }

    @Override
    public boolean checkWhiteLine() {
        return odSensor.getRawLightDetected() > WHITE_LINE_THRESHOLD;
    }

    @Override
    public void useInternalShooterPID(boolean useInternal) {

        if (useInternal) {
            // setMaxSpeed method no longer compiling - deprecated?
            // motorShoot.setMaxSpeed(SHOOTER_MAX_TPS);
            motorShoot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            motorShoot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }
    @Override
    public double getShooterEncoderValue() {
        return motorShoot.getCurrentPosition();
    }

    @Override
    public void setShootPower(double power) {
        motorShoot.setPower(power);
    }

    @Override
    public int getColorSeen() {
        // TODO: set which sensors (left or right) to check, for now, just right

        if (rightRedSensor.getVoltage() > RED_THRESHOLD) {
            return RED_SEEN;
        }
        if (rightBlueSensor.getVoltage() > BLUE_THRESHOLD) {
            return BLUE_SEEN;
        }
        return NONE_SEEN;          // we don't know yet

    }

    @Override
    public int getColorSeenLeft() {
        // TODO: set which sensors (left or right) to check, for now, just right
        // TODO: as a last seccond fix, we swapped right_seen and blue_seen to make the code work right
        // TODO: Yewell V. March 21, last seccond SUPER REGIONALS fix. I apologise for any mayhem I may have caused
        if (leftRedSensor.getVoltage() > RED_THRESHOLD) {
            return BLUE_SEEN;
        }
        if (leftBlueSensor.getVoltage() > BLUE_THRESHOLD) {
            return RED_SEEN;
        }
        return NONE_SEEN;          // we don't know yet

    }

    @Override
    public double getTicksPerTile() {
        return TICKS_PER_TILE;
    }
}