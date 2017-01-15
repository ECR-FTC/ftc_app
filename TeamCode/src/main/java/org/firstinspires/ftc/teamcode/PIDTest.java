package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
/*
Created by ECR FTC on 10/15/2016.
*/

@Autonomous(name="PID Test", group="K9")
//@Disabled
public class PIDTest extends LinearOpMode {

    HardwareK9botECR robot = new HardwareK9botECR();

    private ElapsedTime runtime = new ElapsedTime();


    public void runOpMode() throws InterruptedException
    {
        try {
            robot.init(hardwareMap);
        } catch (InterruptedException e) {
        }

        waitForStart();

        robot.rightMotor.setPower(0.5);
        runtime.reset();

        int ticks = 0, ticksTwo = 0;
        double time = 0, timeTwo = 0;
        double ticksPerTime;
        double target = 1.22, difference = 0;
        double speed = 0.5;
        double P = 0.5, I = 0;
        double pChangeFactor = 4, iChangeFactor = 1;
        double maxSpeed = 1.0, minSpeed = 0.2;
        int timeInt = 1000;


        while(opModeIsActive()) {
            //get new time and ticks

//            robot.rightMotor.setPower(speed);
            ticks =  robot.rightMotor.getCurrentPosition();
            time = runtime.milliseconds();

            //get ticks per time
            ticksPerTime = ((ticks - ticksTwo) / (time - timeTwo));
/*
            if(gamepad1.dpad_up)
            {
                pChangeFactor = pChangeFactor + .01;
            }
            if(gamepad1.dpad_down)
            {
                pChangeFactor = pChangeFactor - .01;
            }
*/
            // find 'p'
            difference = target - ticksPerTime;

            // find 'i'
            I = (I * (timeInt - (time - timeTwo)) + difference * (time - timeTwo)) / timeInt;

            // find speed
            speed += (difference*pChangeFactor) + (I*iChangeFactor);
            speed = max(speed, minSpeed);
            speed = min(speed, maxSpeed);


            robot.rightMotor.setPower(speed);
            //save old time & ticks
            timeTwo = time;
            ticksTwo = ticks;
            sleep(250);


            telemetry.addData("speed", speed);
            telemetry.addData("ticks over time", ticksPerTime);
            telemetry.addData("pChangeFactor", pChangeFactor);
            telemetry.addData("iChangeFactor", iChangeFactor);
            telemetry.addData("P", difference);
            telemetry.addData("I", I);
            telemetry.update();

            idle();
        }
    }
}