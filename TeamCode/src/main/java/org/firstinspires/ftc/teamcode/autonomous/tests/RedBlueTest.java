package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.autonomous.StepAutoCore;
import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.DriveStep;
import org.firstinspires.ftc.teamcode.steprunner.FindRedBlueStep;
import org.firstinspires.ftc.teamcode.steprunner.SayStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.ServoStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.SwitchStep;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - basic StepRunner autonomous test
 * Checks red/blue
 */

@Autonomous(name = "RedBlueTest", group = "StepTests")
public class RedBlueTest extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {


        // Our signal step - flip the loader closed and open
        Step signalStep = new SequenceStep(
                new ServoStep(MorganaBot.LOAD_SERVO, MorganaBot.LOAD_CLOSED),
                new WaitStep(500),
                new ServoStep(MorganaBot.LOAD_SERVO, MorganaBot.LOAD_OPEN),
                new WaitStep(500)
        );

        // What to do for red and blue. Red is one flip; blue is two.

        Step redPlan = new CountLoopStep(signalStep, 1);
        Step bluePlan = new CountLoopStep(signalStep, 2);
        Step defaultPlan = new SayStep("no color seen");

        Step mainStep = new SequenceStep(
                driveToBeacon,
                new SwitchStep("colorFound", defaultPlan, redPlan, bluePlan)
        );

        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("RedBlueTest", robot, mainStep);
    }



}
