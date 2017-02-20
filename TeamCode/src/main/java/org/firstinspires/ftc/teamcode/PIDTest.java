/*
        East Cobb Robotics PID Code
        P) Proportional
        I) Integral
        D) Derivative

        This PID code was written from scratch by our programing team. We
        use this code on our robot to make our robot's shoot motor speed
        constant. This makes our shooting more accurate. We wanted to be
        able to ramp up quickly and display a green LED when we were at the
        right speed, things that the built-in PID couldn't do.

        The speed is measured in encoder ticks over milliseconds. When the
        motor is going too fast (ticks per millis is greater than target)
        the PID slows the motor down, and when the opposite occurs, the
        PID speeds the motor up.
*/

//the imports necessary for the working of this PID
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

@Autonomous(name="PID Test", group="K9")
@Disabled
public class PIDTest extends LinearOpMode {

    HardwareK9botECR robot = new HardwareK9botECR(); //our hardware map

    private ElapsedTime runtime = new ElapsedTime(); //how we calculate speed


    public void runOpMode() throws InterruptedException
    {

        int ticks = 0, ticksTwo = 0; //ticks (new and old) recorded in loop.
        double time = 0, timeTwo = 0; //time (new and old) recorded in loop
        // ticks per time, ticks per milliseconds, avg calculates the average ticks per time for a given timeInt
        double ticksPerTime, ticksPerTimeAvg = 0;
        double target = 0.90; // target ticks per millisecond
        double speed = 0.4; // starting speed, pid changes this as it goes along
        double P = 0.0, I = 0.0; // 'p' and 'i' variables, we don't use 'd'
        // 'P' is inaccurate because the loop is so fast that ticksPerTime is usually zero.
        // Therefore, the code only uses 'I'.
        double pChangeFactor = 0, iChangeFactor = 0.01;
        // iChangeFactor = 0.1: too high: unstable
        // iChangeFactor = 0.001: too low: 1-2 second response time to change in motor load
        double maxSpeed = 1.0, minSpeed = 0.1; // max and min speed
        int timeInt = 50; // time between loops (in milliseconds
        // the closeness the motor speed has to be for it to be considered a good speed
        double targetTolerance = 0.10;

        try {
            robot.init(hardwareMap); // we used our practice bot's hardware, substituting a NeverRest
                                    // 3.7 motor for the right drive motor
        } catch (InterruptedException e) {
        }

        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // resets encoders
        waitForStart(); // waits for driver to hit play

        robot.rightMotor.setPower(speed); //initial power set
        runtime.reset(); //resets time
        sleep(250); //waits so that we have non-zero time for the first step.

        while(opModeIsActive()) { // when stop is hit, it stops
            //get new time and ticks
            ticks =  robot.rightMotor.getCurrentPosition(); //get encoder ticks
            time = runtime.milliseconds(); //get time

            //get updated ticks per time
            ticksPerTime = ((ticks - ticksTwo) / (time - timeTwo));

            //this was used in initial testing to adjust feedback settings. The increment (0.01) was
            // too big, so this did not work.
/*            if(gamepad1.dpad_up)
            {
                pChangeFactor = pChangeFactor + .01;
            }
            if(gamepad1.dpad_down)
            {
                pChangeFactor = pChangeFactor - .01;
            }
*/
            // find 'p'
            P = target - ticksPerTime; // target - ticks per time = speed error

            // find 'i'
            //average ticks/time of last few loops  and calculates that against the distance off
            I = (I * (timeInt - (time - timeTwo)) + P * (time - timeTwo)) / timeInt;
            // average speed over many loops: used to display useful telemetry
            ticksPerTimeAvg = (ticksPerTimeAvg*(timeInt - (time - timeTwo)) + ticksPerTime*(time - timeTwo)) / timeInt;
            // update speed
            speed += (P*pChangeFactor) + (I*iChangeFactor); //decides how much to change the speed by
            speed = max(speed, minSpeed); //keeps speed over min
            speed = min(speed, maxSpeed); //keeps speed under max

            robot.rightMotor.setPower(speed); // set power

/*
            // We don't have an LED on the K-9, but we do on the competition robot,
            // so this section is used on the competition robot.

            // show when the wheel is spinning fast enough
            if(Math.abs(ticksPerTimeAvg - target) < targetTolerance)
            {
                robot.LEDGreen.enable(true); // turn on LED when correct.
            }
            else
            {
                robot.LEDGreen.enable(false); // turn off LED when too fast or slow

            }
*/
            //logs our settings so we can see how it is working
            telemetry.addData("speed      ",  "%6.3f", speed);
            telemetry.addData("time  ",  "%6.4f ms", time);
            telemetry.addData("ticks ",  "%6d", ticks);
            telemetry.addData("time diff  ",  "%6.4f ms", (time - timeTwo));
            telemetry.addData("ticks diff ",  "%6d", (ticks - ticksTwo));
            telemetry.addData("ticks/time ",  "%6.3f", ticksPerTimeAvg);
            telemetry.addData("pChangeFactor",  "%6.3f", pChangeFactor);
            telemetry.addData("iChangeFactor",  "%6.3f", iChangeFactor);
            telemetry.addData("P",   "%.3f", P);
            telemetry.addData("I",   "%.3f", I);
            telemetry.update();

            //save old time & ticks for next loop
            timeTwo = time;
            ticksTwo = ticks;

            idle();
        }
    }
}