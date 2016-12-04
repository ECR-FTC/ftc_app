package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
//import com.qualcomm.robotcore.hardware.TouchSensor;
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

    public DcMotor motorShoot;
    public Servo leftServo;
    public Servo rightServo;
    public Servo loadServo;
    public Servo fireServo;
    public ColorSensor colorR;
    public ModernRoboticsI2cGyro gyro;   // Hardware Device Object
    public OpticalDistanceSensor ODS;

//    private double ENCODER_PORT_RESET;

    /* Local OpMode members. */
    public HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    public double deadZone = 0.05; // Min and Max Range to not do anything
    public double leftPress = 0.9; // left button pusher "on" value
    public double rightPress = 0.1; // right button pusher "on" value
    public double leftStore = 0.3; // left button pusher "off" value
    public double rightStore = 0.65;  // right button pusher "off" value
    public double fireGo = 0.5; // setting to fire a ball into the launcher
    public double fireStay = 0.0; // down setting for the fire servo
    public double loadClosed = 0.7; // "up" setting for the ball loader
    public double loadOpen = 0.0;  // "down" setting for the ball loader
    public double shootPower = -0.15; // steady-state launcher motor power
    public double shootRampPower = -1.0; // ramp-up launcher motor power
    public double shootRampTime = 2;  // ramp-up time for launcher motor
    public double fireServoTime = 0.5;  // delay time for the firing servo

    public int GO_ONE_TILE_PORT = 3150;

    public double driveMinPower = 0.4;
    public double driveMaxPower = 1.0;
    public double drivePowerIncrement = 0.01;
    /* Constructor */
    public HardwareBuild2() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) throws InterruptedException {
        // save reference to HW Map
        hwMap = ahwMap;

  /*      motorFrontRight = hwMap.dcMotor.get("frontRight");
        motorFrontLeft = hwMap.dcMotor.get("frontLeft");
        motorBackRight = hwMap.dcMotor.get("backRight");
        motorBackLeft = hwMap.dcMotor.get("backLeft");
        */
        motorShoot = hwMap.dcMotor.get("shootMotor");

//        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
//        motorBackRight.setDirection(DcMotor.Direction.REVERSE);

        leftServo = hwMap.servo.get("servoLeft");
        rightServo = hwMap.servo.get("servoRight");
        fireServo = hwMap.servo.get("servoFire");
        loadServo = hwMap.servo.get("servoLoad");

        gyro = (ModernRoboticsI2cGyro)hwMap.gyroSensor.get("sensorGyro");
        colorR = hwMap.colorSensor.get("sensorColorRight");
//        colorL = hwMap.colorSensor.get("sensorColorLeft");
        ODS = hwMap.opticalDistanceSensor.get("sensorODS");
//        touch = hwMap.touchSensor.get("sensorTouch");

/*        // Set all motors to zero power
        motorFrontLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
*/
  /*      // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
*/

        colorR.enableLed(false);

        // start calibrating the gyro.
        gyro.calibrate();

        // make sure the gyro is calibrated.
        while (gyro.isCalibrating()) {
            Thread.sleep(50);
        }

        // set the button pushing servos to the store positions
        rightServo.setPosition(rightStore);
        leftServo.setPosition(leftStore);

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
