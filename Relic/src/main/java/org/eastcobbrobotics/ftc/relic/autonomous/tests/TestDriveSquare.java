package org.eastcobbrobotics.ftc.relic.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.eastcobbrobotics.ftc.relic.autonomous.StepAutoCore;
import org.eastcobbrobotics.ftc.relic.RelicBot;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.CountLoopStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperDriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.TurnStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - basic StepRunner autonomous test
 * <p>
 * This one just drives in a square, waiting a second after each side.
 */

@Autonomous(name = "TestDriveSquare", group = "StepTests")
public class TestDriveSquare extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        // One-second pause to use between steps; use this twice
        // to test WaitStep reuse
        Step pauseStep = new WaitStep(1000.0);

        // This step does one side, pauses, turns, pauses...
        Step oneSideStep = new SequenceStep(
                new RamperDriveStep(1.0, 1.0),
                pauseStep,
                new TurnStep(90.0, 1.0),
                pauseStep
        );

        // ... so our main step repeats that four times.
        Step mainStep = new CountLoopStep(oneSideStep, 4);

        // Create the robot and run our routine
        RelicBot robot = new RelicBot();
        runStepAutonomous("TestDriveSquare", robot, mainStep);
    }

}
