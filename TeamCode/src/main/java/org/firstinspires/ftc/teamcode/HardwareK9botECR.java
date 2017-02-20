package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRGyro;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left motor"
 * Motor channel:  Right drive motor:        "right motor"
 * Servo channel:  Servo to raise/lower arm: "arm"
 * Servo channel:  Servo to open/close claw: "claw"
 *
 * Note: the configuration of the servos is such that:
 *   As the arm servo approaches 0, the arm position moves up (away from the floor).
 *   As the claw servo approaches 0, the claw opens up (drops the game element).
 */
public class HardwareK9botECR
{
    /* Public OpMode members. */
    public DcMotor  leftMotor   = null;
    public DcMotor  rightMotor  = null;
    public Servo    arm         = null;
    public Servo    claw        = null;
    public ModernRoboticsI2cGyro gyro = null;
    public TouchSensor touchR = null;

    public final static double ARM_HOME = 0.36;
    public final static double CLAW_HOME = 0.2;
    public final static double ARM_MIN_RANGE  = 0.36;
    public final static double ARM_MAX_RANGE  = 1.00;
    public final static double CLAW_MIN_RANGE  = 0.00;
    public final static double CLAW_MAX_RANGE  = 0.7;

    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareK9botECR() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) throws InterruptedException {
        // save reference to HW Map
        hwMap = ahwMap;

        gyro = (ModernRoboticsI2cGyro)hwMap.gyroSensor.get("gyro");

        touchR = hwMap.touchSensor.get("touchFR");

        // Define and Initialize Motors
        leftMotor   = hwMap.dcMotor.get("motor_1");
        rightMotor  = hwMap.dcMotor.get("motor_2");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to run without encoders.
        //DcMotor.RunMode.RUN_WITHOUT_ENCODER
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Define and initialize ALL installed servos.
        arm = hwMap.servo.get("servo_1");
        claw = hwMap.servo.get("servo_6");
        arm.setPosition(ARM_HOME);
        claw.setPosition(CLAW_HOME);

        // start calibrating the gyro.
        gyro.calibrate();

        // make sure the gyro is calibrated.
        while (gyro.isCalibrating()) {
            Thread.sleep(50);
        }

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
