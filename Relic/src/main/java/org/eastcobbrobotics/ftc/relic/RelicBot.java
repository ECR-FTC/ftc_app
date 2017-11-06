package org.eastcobbrobotics.ftc.relic;

import android.media.MediaPlayer;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.R;

import java.util.List;
import java.util.Map;

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
    public ColorSensor colorSensor;
    public BNO055IMU imu;

    // State used for updating telemetry
    public Orientation angles;

    // The media player we use for "spoken" output
    private MediaPlayer mp;

    // List of Servos and their Id's. NOTE THAT THE IDs ARE IN THE
    // ORDER added to servoList below!
    public List<Servo> servoList;
    public final static int LEFT_GRAB_SERVO           = 0;
    public final static int RIGHT_GRAB_SERVO          = 1;
    public final static int LEFT_ARM_ELBOW_SERVO      = 2;
    public final static int RIGHT_ARM_ELBOW_SERVO     = 3;
    public final static int LEFT_ARM_WRIST_SERVO      = 4;
    public final static int RIGHT_ARM_WRIST_SERVO     = 5;

    // Constants
    public static final double TICKS_PER_TILE = 1525;   // encoder ticks for one game tile

    public static final double GLYPHTER_POWER       =  0.50;  // glyphter power

    public static final double LEFT_JEWEL_STORE     =  0.25;  // leftjewel store value
    public static final double LEFT_JEWEL_DOWN      =  0.91;  // leftjewel deployed value
    public static final double RIGHT_JEWEL_STORE    =  0.84;  // rightjewel store value
    public static final double RIGHT_JEWEL_DOWN     =  0.11;  // rightjewel deployed value

    public static final double RIGHT_WRIST_LEFT     =  0.75;
    public static final double RIGHT_WRIST_CENTER   =  0.52;
    public static final double RIGHT_WRIST_RIGHT    =  0.26;
    public static final double RIGHT_WRIST_STORE    =  1.00;

    public static final double LEFT_WRIST_LEFT      =  0.65;
    public static final double LEFT_WRIST_CENTER    =  0.44;
    public static final double LEFT_WRIST_RIGHT     =  0.20;
    public static final double LEFT_WRIST_STORE     =  0.00;

    public int colorDifference = 20;

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


        leftArmWrist = hwMap.servo.get("servoLeftWrist");    // hwMap.servo.get("???")
        rightArmWrist = hwMap.servo.get("servoRightWrist");   // hwMap.servo.get("???")

        colorSensor = hwMap.colorSensor.get("sensorColorDistance");

        // Make a list of the Servos, so we can refer to them by number.
        // Make sure they are in the right order! SEE COMMENT ABOVE!
        servoList = asList(leftGrab, rightGrab, leftArmElbow, rightArmElbow, leftArmWrist, rightArmWrist);

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
        colorSensor.enableLed(true);

        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        //todo angles   = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        // return formatAngle(angles.angleUnit, angles.firstAngle);
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
    //@Override
    public int readColor()
    {
        int color = 1; // 0 is blue, 1 is neither(or both), 2 is red
        float blue = colorSensor.blue();
        float red = colorSensor.red();

        if(blue - red > colorDifference)
        {
            color = color - 1;
        }

        if(red - blue > colorDifference)
        {
            color = color + 1;
        }
        return color;
    }


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
    public void driveGlyphter(int direction){
        motorGlyphter.setPower(direction*GLYPHTER_POWER);
    }
    public void timeTurnLeft() {motorFrontRight.setPower(0.50);}
    public void timeTurnRight() {motorFrontLeft.setPower(0.50);}
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

    /* Play a sound specified by a resource ID. */
    @Override
    public void play(int resId) {
        mp = MediaPlayer.create(hwMap.appContext, resId);
        mp.start();
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
