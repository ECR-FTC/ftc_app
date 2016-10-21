package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Created by ECR FTC on 10/15/2016.
 */

@Autonomous(name="K9: MiniK9AutoTri", group="K9")
//@Disabled

public class MiniK9AutoTri extends LinearOpMode {
    HardwareK9botECR robot = new HardwareK9botECR();

    int straightTime = 1200;
    int turnTime = 450;
    int pauseTime = 400;



    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        telemetry.addData("Say", "Hello TriWorld!");
        telemetry.update();

        for(int s = 0; s < 3; s++){
            goTurn();
            goPause();
            goStraight();
            goPause();

        }

    }
    public void goStraight() throws InterruptedException{


        robot.leftMotor.setPower(.5);
        robot.rightMotor.setPower(.5);
        Thread.sleep(straightTime);

    }
    public void goPause() throws InterruptedException{
        robot.leftMotor.setPower(0);
        robot.rightMotor.setPower(0);
        Thread.sleep(pauseTime);
    }
    public void goTurn() throws InterruptedException{
        robot.leftMotor.setPower(.5);
        robot.rightMotor.setPower(-.5);
        Thread.sleep(turnTime);
    }

}

