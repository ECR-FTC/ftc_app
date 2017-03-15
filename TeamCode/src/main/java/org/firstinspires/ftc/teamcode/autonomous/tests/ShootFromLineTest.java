package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.autonomous.StepAutoCore;
import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.FindWhiteLineStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.ServoStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - White line test
 * Test white line detection steps.
 */

@Autonomous(name = "ShootFromLineTest", group = "StepTests")
public class ShootFromLineTest extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        Step toWhiteLine = new UntilOneDoneStep(
                new RamperDriveStep(6.0, 0.2),      // look whole length of the field
                new FindWhiteLineStep()
        );


        Step mainStep = new SequenceStep(
                toWhiteLine,
                startShooter,
                shootParticle,
                waitFor(2000),
                shootParticle,
                waitFor(1000),
                stopShooter,
                new ServoStep(MorganaBot.FIRE_SERVO, MorganaBot.FIRE_GO)
                );

        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("ShootFromLineTest", robot, mainStep);
    }

}
