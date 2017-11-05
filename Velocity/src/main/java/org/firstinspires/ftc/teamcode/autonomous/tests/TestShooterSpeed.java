package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.autonomous.StepAutoCore;
import org.firstinspires.ftc.teamcode.autonomous.steps.RunShooterStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.ServoStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex
 * Test shooter PID control
 */

@Autonomous(name = "TestShooterSpeed", group = "StepTests")
public class TestShooterSpeed extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        Step mainStep = new SequenceStep(
                new ServoStep(MorganaBot.LOAD_SERVO, MorganaBot.LOAD_OPEN),
                new UntilOneDoneStep(
                        new RunShooterStep(800),
                        waitFor(30000)
                ),
                stopShooter
        );

        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("TestShooterSpeed", robot, mainStep);
    }

}
