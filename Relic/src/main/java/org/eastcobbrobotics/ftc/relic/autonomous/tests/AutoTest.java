package org.eastcobbrobotics.ftc.relic.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.DriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperDriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.autonomous.StepAutoCore;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnLeftStep;
//import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnRightStep;

/**
 * ECR FTC 11096 - 2017 - 2018 Relic Recovery
 * <p>
 * AutoOneRed is a minimal autonomous routine, which uses
 * StepRunner to perform several competition processes.
 */

@Autonomous(name = "AutoTest", group = "Competition")
@Disabled

public class AutoTest extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        RelicBot robot = new RelicBot();
        Step mainStep = new SequenceStep(
                new RamperDriveStep( 1 , 1 )
        );
        runStepAutonomous("AutoTest", robot, mainStep);
    }
}