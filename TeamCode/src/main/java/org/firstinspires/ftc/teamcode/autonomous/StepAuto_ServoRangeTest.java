package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.ServoStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.TurnStep;
import org.firstinspires.ftc.teamcode.steps.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - servo range test
 *
 * Move all servos through their full range 10 times.
 */

@Autonomous(name = "ServoRangeTest", group = "StepTests")
public class StepAuto_ServoRangeTest extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        Step pauseStep = new WaitStep(2000.0);

        Step servoFlipStep = new SequenceStep(
                new ServoStep(MorganaBot.LEFT_SERVO, MorganaBot.LEFT_PRESS),
                new ServoStep(MorganaBot.RIGHT_SERVO, MorganaBot.RIGHT_PRESS),
                new ServoStep(MorganaBot.LOAD_SERVO, MorganaBot.LOAD_CLOSED),
                new ServoStep(MorganaBot.FIRE_SERVO, MorganaBot.FIRE_STAY),
                pauseStep,
                new ServoStep(MorganaBot.LEFT_SERVO, MorganaBot.LEFT_STORE),
                new ServoStep(MorganaBot.RIGHT_SERVO, MorganaBot.RIGHT_STORE),
                new ServoStep(MorganaBot.LOAD_SERVO, MorganaBot.LOAD_OPEN),
                new ServoStep(MorganaBot.FIRE_SERVO, MorganaBot.FIRE_GO),
                pauseStep
        );

        Step mainStep = new CountLoopStep(servoFlipStep, 10);

        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("ServoRangeTest", robot, mainStep);
    }

}
