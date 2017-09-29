package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//import com.qualcomm.robotcore.hardware.TouchSensor;

//import com.qualcomm.robotcore.hardware.GyroSensor;

//import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRGyro;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is the build2 robot.
 *
 */
public class HardwareJunior_V0
{
    /* Public OpMode members. */
    /*
        Forward   Left   Clockwise
   FR     1         -1      -1
   FL     1          1       1
   BR     1          1       1
   BL     1         -1      -1
     */
/*    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackRight;
    public DcMotor motorBackLeft;
    */

    public DcMotor motorFL;
    public DcMotor motorBL;
    public DcMotor motorFR;
    public DcMotor motorBR;
    public DcMotor motorGlyphter;

    public Servo servoLeftGrab;
    public Servo servoRightGrab;

    /* Local OpMode members. */
    public HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();


    public double leftGrab         =  0.9;  // leftgrab grab value
    public double rightGrab        =  0.1;  // rightgrab grab value
    public double leftRelease      =  0.5;  // leftgrab release value
    public double rightRelease     =  0.5;  // rightgrab release value

    public double topSpeed         =  0.7;  // top speed for drive
    public double glyphterSpeed    =  0.5;  // top speed for glyphter

    /* Constructor */
    public HardwareJunior_V0() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) throws InterruptedException {
        // save reference to HW Map
        hwMap = ahwMap;

        motorFR = hwMap.dcMotor.get("motorFR");
        motorFL = hwMap.dcMotor.get("motorFL");
        motorBR = hwMap.dcMotor.get("motorBR");
        motorBL = hwMap.dcMotor.get("motorBL");
        motorGlyphter = hwMap.dcMotor.get("motorGlyphter");

        motorFL.setDirection(DcMotor.Direction.REVERSE);
        motorBL.setDirection(DcMotor.Direction.REVERSE);

        servoLeftGrab = hwMap.servo.get("servoLeftGrab");
        servoRightGrab = hwMap.servo.get("servoRightGrab");


        // Set all motors to zero power
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBR.setPower(0);
        motorBL.setPower(0);
        motorGlyphter.setPower(0);
        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorGlyphter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


/*        // start calibrating the gyro.
        gyro.calibrate();

        // make sure the gyro is calibrated.
        while (gyro.isCalibrating()) {
            Thread.sleep(50);
        }
*/
        // set the button pushing servos to the store positions
        servoRightGrab.setPosition(rightRelease);
        servoLeftGrab.setPosition(leftRelease);

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
