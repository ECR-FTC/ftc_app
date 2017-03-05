package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.FindWhiteLineStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.ServoStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;
import org.firstinspires.ftc.teamcode.steps.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - White line test
 * Test white line detection steps.
 */

@Autonomous(name = "WhiteLineTest", group = "StepTests")
public class StepAuto_WhiteLineTest extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        Step toWhiteLineStep = new UntilOneDoneStep(
                new RamperDriveStep(20000, 1.0),
                new FindWhiteLineStep()
        );

        Step backAndForthStep = new SequenceStep(
                toWhiteLineStep,
                new WaitStep(1000),
                new RamperDriveStep(4000, -1.0),
                new WaitStep(1000)
        );
        Step mainStep = new CountLoopStep(backAndForthStep, 4);
        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("WhiteLineTest", robot, mainStep);
    }

}
