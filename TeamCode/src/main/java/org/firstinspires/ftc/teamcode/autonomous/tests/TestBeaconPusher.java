package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.autonomous.StepAutoCore;
import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.ServoStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex
 * Move Beacon Pushers out and back 3 times.
 */

@Autonomous(name = "TestBeaconPusher", group = "StepTests")
public class TestBeaconPusher extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        Step pauseStep = new WaitStep(750.0);

        Step pusherTestStep = new SequenceStep(
                pushBeaconButtonRight,
                pauseStep,
                pushBeaconButtonLeft,
                pauseStep
        );

        Step mainStep = new CountLoopStep(pusherTestStep, 3);

        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("TestBeaconPusher", robot, mainStep);
    }

}
