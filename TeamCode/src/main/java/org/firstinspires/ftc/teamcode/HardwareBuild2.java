package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRGyro;

//import com.qualcomm.robotcore.hardware.GyroSensor;

//import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRGyro;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is the build2 robot.
 *
 */
public class HardwareBuild2
{
    /* Public OpMode members. */

    DcMotor motorFrontRight;
    DcMotor motorFrontLeft;
    DcMotor motorBackRight;
    DcMotor motorBackLeft;
    DcMotor motorShoot;
    Servo leftServo;
    Servo rightServo;
    Servo loadServo;
    Servo fireServo;
    ColorSensor colorR, colorL;
    ModernRoboticsI2cGyro gyro;   // Hardware Device Object
    OpticalDistanceSensor ODS;
    TouchSensor touch;
    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    double leftPress = .9;
    double rightPress = .1;
    double leftStore = .3;
    double rightStore = .7;
    double fireGo = .5;
    double fireStay = .0;
    double loadClosed = .7;
    double loadOpen = .0;

    /* Constructor */
    public HardwareBuild2() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) throws InterruptedException {
        // save reference to HW Map
        hwMap = ahwMap;

        motorFrontRight = hwMap.dcMotor.get("frontRight");
        motorFrontLeft = hwMap.dcMotor.get("frontLeft");
        motorBackRight = hwMap.dcMotor.get("backRight");
        motorBackLeft = hwMap.dcMotor.get("backLeft");
        motorShoot = hwMap.dcMotor.get("shootMotor");


        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        leftServo = hwMap.servo.get("servoLeft");
        rightServo = hwMap.servo.get("servoRight");
        fireServo = hwMap.servo.get("servoFire");
        loadServo = hwMap.servo.get("servoLoad");

        gyro = (ModernRoboticsI2cGyro)hwMap.gyroSensor.get("sensorGyro");
        colorR = hwMap.colorSensor.get("sensorColorRight");
        colorL = hwMap.colorSensor.get("sensorColorLeft");
        ODS = hwMap.opticalDistanceSensor.get("sensorODS");
        touch = hwMap.touchSensor.get("sensorTouch");

        // Set all motors to zero power
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        /* // Define and initial
        // start calibrating the gyro.
        gyro.calibrate();

        // make sure the gyro is calibrated.
        while (gyro.isCalibrating()) {
            Thread.sleep(50);
        }

        */
        colorL.enableLed(false);
        colorR.enableLed(false);
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs)  throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}
