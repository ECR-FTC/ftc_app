package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.FindWhiteLineStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;
import org.firstinspires.ftc.teamcode.steps.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - Color test
 * Check values returned by color sensors.
 */

@Autonomous(name = "ColorTest", group = "StepTests")
public class StepAuto_ColorTest extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        MorganaBot robot = new MorganaBot();

        // Initialize the robot.
        try {
            robot.init(hardwareMap);
        } catch (InterruptedException e) {
            return;         // just exit if interrupted
        }

        // Show telemetry and wait for Start button to be pressed
        showMessage("Waiting for start");
        waitForStart();

        while (opModeIsActive()) {
            double red = robot.leftRedSensor.getVoltage();
            double blue = robot.leftBlueSensor.getVoltage();
            telemetry.addData("Colors", "Red=%.2f", red);
            telemetry.addData("Colors", "Blue=%.2f", blue);
            telemetry.update();
        }

    }

}
