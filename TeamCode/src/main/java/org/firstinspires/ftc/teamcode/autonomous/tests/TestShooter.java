package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.autonomous.StepAutoCore;
import org.firstinspires.ftc.teamcode.steprunner.RunShooterStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - basic StepRunner autonomous test
 * Starts and Stops particle shooter.
 */

@Autonomous(name = "TestShooter", group = "StepTests")
public class TestShooter extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        Step mainStep = new SequenceStep(
                new UntilOneDoneStep(
                        new RunShooterStep(750, 0.3),
                        waitFor(10000)
                ),
                new UntilOneDoneStep(
                        new RunShooterStep(750, 0.5),
                        waitFor(10000)
                ),
                new UntilOneDoneStep(
                        new RunShooterStep(750, 0.7),
                        waitFor(10000)
                ),

                stopShooter
        );

        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("TestShooter", robot, mainStep);
    }

}