package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.autonomous.StepAutoCore;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.autonomous.steps.FindWhiteLineStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperDriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - White line test
 * Test white line detection steps.
 */

@Autonomous(name = "TestWhiteLine", group = "StepTests")
public class TestWhiteLine extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        Step toWhiteLineStep = new UntilOneDoneStep(
                new RamperDriveStep(6.0, 0.2),
                new FindWhiteLineStep()
        );

        Step backAndForthStep = new SequenceStep(
                toWhiteLineStep,
                new WaitStep(1000),
                new RamperDriveStep(1.5, -0.2),
                new WaitStep(1000)
        );
        Step mainStep = new CountLoopStep(backAndForthStep, 4);
        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("TestWhiteLine", robot, mainStep);
    }

}
