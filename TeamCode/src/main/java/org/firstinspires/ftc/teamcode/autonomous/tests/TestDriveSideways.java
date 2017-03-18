package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.autonomous.StepAutoCore;
import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveSidewaysStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.TurnStep;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - basic StepRunner autonomous test
 * <p>
 * This one just drives in a square, waiting a second after each side.
 */

@Autonomous(name = "TestDriveSideways", group = "StepTests")
public class TestDriveSideways extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        // One-second pause to use between steps; use this twice
        // to test WaitStep reuse
        Step pauseStep = new WaitStep(1000.0);
        Step RamperDriveSidewaysStep = new RamperDriveSidewaysStep(.5, 1, 1);

        // This step does one side, pauses, turns, pauses...
/*        Step oneSideStep = new SequenceStep(
                new RamperDriveSidewaysStep(.5, 1, 1),
                pauseStep,
                new RamperDriveSidewaysStep(.5, 1, 1),
                pauseStep
        );
*/
        Step oneSideStep = new UntilOneDoneStep(
            new WaitStep(2000),
            new RamperDriveSidewaysStep(0.5, 1, 1)
        );
        // ... so our main step repeats that four times.
        Step mainStep = new CountLoopStep(oneSideStep, 1);

        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("TestDriveSideways", robot, mainStep);
    }

}
