package org.eastcobbrobotics.ftc.relic;

import android.util.Log;

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
import org.eastcobbrobotics.ftc.relic.utils.Tracecaster;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

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

    public ColorSensor colorSensorRight;
    public ColorSensor colorSensorLeft;
    public BNO055IMU imu;


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
    public static final double TICKS_PER_TILE = 1525;   // encoder ticks for one game tile

    public static final double GLYPHTER_POWER = 0.50;  // glyphter power

    public static final double LEFT_JEWEL_STORE = 0.25;  // leftjewel store value
    public static final double LEFT_JEWEL_DOWN = 1.00;  // leftjewel deployed value
    public static final double RIGHT_JEWEL_STORE = 0.84;  // rightjewel store value
    public static final double RIGHT_JEWEL_DOWN = 0.11;  // rightjewel deployed value

    public static final double RIGHT_WRIST_LEFT = 0.56;
    public static final double RIGHT_WRIST_CENTER = 0.28;
    public static final double RIGHT_WRIST_RIGHT = 0.02;
    public static final double RIGHT_WRIST_STORE = 1.00;

    public static final double LEFT_WRIST_LEFT = 0.77;
    public static final double LEFT_WRIST_CENTER = 0.54;
    public static final double LEFT_WRIST_RIGHT = 0.28;
    public static final double LEFT_WRIST_STORE = 0.00;

    // The values for the colors we detect with the color sensor.
    public static final int COLOR_BLUE = 0;
    public static final int COLOR_UNKNOWN = 1;
    public static final int COLOR_RED = 2;

    public double colorDifference = 10;

    // Tracing.
    public Telemetry telemetry;
    public int tcPort = 11096;
    public Tracecaster tc;
    public boolean tracecastEnabled;

    public VuforiaLocalizer vuforia;
    public VuforiaTrackables relicTrackables;
    public VuforiaTrackable relicTemplate;

    /*
     *   Initialize the robot by getting access to all of its devices through the
     *   hardware map and then setting them to starting values.
     */
    @Override
    public void init(Object map) throws InterruptedException {

        // Tracecaster must be DISABLED (false) for competition
        tracecastEnabled = false;

        tell("RelicBot: Initializing");

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

        leftArmWrist = hwMap.servo.get("servoLeftWrist");
        rightArmWrist = hwMap.servo.get("servoRightWrist");

        colorSensorRight = hwMap.colorSensor.get("sensorColorDistanceRight");
        colorSensorLeft = hwMap.colorSensor.get("sensorColorDistanceLeft");

        // Make a list of the Servos, so we can refer to them by number.
        // Make sure they are in the right order! SEE COMMENT ABOVE!
        servoList = asList(leftGrab, rightGrab, leftArmElbow, rightArmElbow, leftArmWrist, rightArmWrist);

        // Reset all drive motors
        resetDriveMotors();

        // Reset jewel arms
        reset_jewel_arms();

        // Enable color sensor LEDs.
        colorSensorRight.enableLed(true);
        colorSensorLeft.enableLed(true);

        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Set up our Vuforia camera.
        VuforiaLocalizer.Parameters vuforiaParameters = new VuforiaLocalizer.Parameters();
        vuforiaParameters.vuforiaLicenseKey = "ATzOAID/////AAAAGWB+pRDolE/Mlr+59IMYtjx6LhM5Ct9clbf5okK+ie5MhZ7gTp7z0hdxcRP/DAzErKsfTg3Cz3JNZMUVM2LL5Aj5Nx3r0awwiSDS5/FRxdDurfddsF4wVzgzDyyIk3jIW3LQu96DVlcsGS2NzCcnclfft/kwfcQt6J5lGBbbWOp65h/cSopGehPckyTjrOUuIDQGQnrmqM+QjdL2eardbNfvQQ3/DGLHHsO4f/ZYXXHxahD4r6vCNBCW282upQVl8dflrEVcGaQ9G39MbBOJSsxpFsece0P+MsHoF6Y58GQDxBXQzRrNbP2OBU14lhSTb0mZBl52MLEhCZGgzWXgMkKronzDwp2g4QwVAngF8XzU";
        vuforiaParameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        vuforia = ClassFactory.createVuforiaLocalizer(vuforiaParameters);

        // Load images
        relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        relicTrackables.activate();

    }

    @Override
    public void setTelemetry(Object telemetry) {
        this.telemetry = (Telemetry) telemetry;
    }

    /*
     * Send a telemetry / status message. Current implementation uses Tracecaster.
     */
    @Override
    public void tell(String msg) {

        if (telemetry != null) {
            telemetry.addData("TEL", msg);      // FOR NOW also send message to telemetry
        }

        if (tracecastEnabled) {
            if (tc == null) {
                tc = new Tracecaster(tcPort);
                tc.post("Tracecast: Ready ---------------------");
            }
            tc.post(msg);
        }
    }


    /*
     * Shut down at the end of a run.
     */
    @Override
    public void shutDown() {
        if (tc != null) {
            tc.close();
            tc = null;
        }
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

    // Read the value of a color sensor.
    public int readColor(ColorSensor sensor) {
        float blue = sensor.blue();
        float red = sensor.red();

        // Assume unknown until we get a blue reading significantly above the
        // red reading, or vice versa.
        int color = COLOR_UNKNOWN;
        if (blue - red > colorDifference) {
            color = COLOR_BLUE;
        } else if (red - blue > colorDifference) {
            color = COLOR_RED;
        }
        return color;

    }

    //@Override
    public int readColorRight() {
        return readColor(colorSensorRight);
    }

    public int readColorLeft() {
        return readColor(colorSensorLeft);
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
        motorFrontRight.setPower(-p);
        motorBackRight.setPower(-p);
        motorFrontLeft.setPower(p);
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

    public void driveGlyphter(int direction) {
        motorGlyphter.setPower(direction * GLYPHTER_POWER);
    }

    public void timeTurnLeft() {
        motorFrontRight.setPower(0.50);
    }

    public void timeTurnRight() {
        motorFrontLeft.setPower(0.50);
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
        // TODO: don't know if we really need thie yet; maybe just re-initialize?
    }

    /*
     * Get gyro heading.
     */
    @Override
    public double getGyroHeading() {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
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
