package org.eastcobbrobotics.ftc.relic.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.DriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperDriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilAllDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnLeftStep;
//import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnRightStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.JewelFlipLeftStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.JewelFlipRightStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnRightStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperTurnStep;

/**
 * ECR FTC 11096 - 2017 - 2018 Relic Recovery
 * <p>
 * AutoOneRed is a minimal autonomous routine, which uses
 * StepRunner to perform several competition processes.
 */

@Autonomous(name = "AutoTwoRed", group = "Competition")
//@Disabled

public class AutoTwoRed extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        RelicBot robot = new RelicBot();
        Step mainStep = new SequenceStep(
                deployRightArmStep,
                new UntilAllDoneStep(
                        new JewelFlipRightStep(),
                        new WaitStep(2000)
                ),
                retractRightArmStep,
                grabGlyph,
                new UntilOneDoneStep(
                        new RamperDriveStep(0.9,1),
                        new WaitStep(1000)
                ),
                new UntilOneDoneStep(
                        new WaitStep(3000),
                        new RamperTurnStep(90, 0.5)
                ),
                new UntilOneDoneStep(
                        new RamperDriveStep(1.2,1),
                        new WaitStep(1000)
                ),
                releaseGlyph,
                new UntilOneDoneStep(
                        new DriveStep(-0.50),
                        new WaitStep(200)
                )
        );
        runStepAutonomous("AutoTwoRed", robot, mainStep);
    }
}