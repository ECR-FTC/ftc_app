package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.steprunner.StepRobot;

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
    public static final double FIRE_GO = 0.5;  // setting to fire a ball into the launcher
    public static final double FIRE_STAY = 0.0;  // down setting for the fire servo
    public static final double LOAD_CLOSED = 0.8;  // "up" setting for the ball loader
    public static final double LOAD_HALF = 0.45; // load 1/2 way up
    public static final double LOAD_OPEN = 0.1;  // "down" setting for the ball loader
    public static final double SHOOT_POWER = -0.15; // steady-state launcher motor power
    public static final double SHOOT_RAMP_POWER = -1.0;  // ramp-up launcher motor power
    public static final double SHOOT_RAMP_TIME = 2.1;  // ramp-up time for launcher motor
    public static final double FIRE_SERVO_TIME = 0.5;  // delay time for the firing servo
    public static final double DEAD_ZONE = 0.25; // for lift motor
    public static final double COLOR_THRESHOLD = 0.45; // value that the sensor has to be to return positive.


    public static final int GO_ONE_TILE_PORT = 3150;

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
        loadServo.setPosition(LOAD_OPEN);

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
        // Servo s = servoList.get(servoId);
        // s.setPosition(position);
        servoList.get(servoId).setPosition(position);
    }


}
