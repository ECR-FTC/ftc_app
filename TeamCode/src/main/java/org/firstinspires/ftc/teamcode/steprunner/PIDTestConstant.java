package org.firstinspires.ftc.teamcode.steprunner;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.HardwareK9botECR;


/*
Created by ECR FTC on 10/15/2016.
*/

@Autonomous(name="PID Test constant", group="K9")
//@Disabled
public class PIDTestConstant extends LinearOpMode {

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
        double target = 1.16, diffrence;
        double speed = 0.5, change = 1;
        double a = 0;

        while(opModeIsActive()) {
            //get new time and ticks

            robot.rightMotor.setPower(speed);
            ticks =  robot.rightMotor.getCurrentPosition();
            time = runtime.milliseconds();

            //get ticks per time
            ticksPerTime = ((ticks - ticksTwo) / (time - timeTwo));

            if(gamepad1.dpad_up)
            {
                a = a + .01;
            }
            if(gamepad1.dpad_down)
            {
                a = a - .01;
            }

            telemetry.addData("speed", speed);
            telemetry.addData("ticks over time", ticksPerTime);
            telemetry.addData("a", a);
            telemetry.update();

            //save old time & ticks
            timeTwo = time;
            ticksTwo = ticks;
            sleep(250);
            idle();
/*
            diffrence = target - ticksPerTime;
            speed += diffrence*a;
*/
            robot.rightMotor.setPower(speed);

        }
    }
}

