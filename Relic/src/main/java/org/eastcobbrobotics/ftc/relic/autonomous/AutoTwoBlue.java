package org.eastcobbrobotics.ftc.relic.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.DriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperDriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.TurnStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilAllDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnLeftStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnRightStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.JewelFlipLeftStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.JewelFlipRightStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperTurnStep;

/**
 * ECR FTC 11096 - 2017 - 2018 Relic Recovery
 * <p>
 * AutoOneBlue is a minimal autonomous routine, which uses
 * StepRunner to perform several competition processes.
 */

@Autonomous(name = "AutoTwoBlue", group = "Competition")
//@Disabled

public class AutoTwoBlue extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        RelicBot robot = new RelicBot();
        Step mainStep = new SequenceStep(
                deployLeftArmStep,
                new UntilAllDoneStep(
                        new JewelFlipLeftStep(),
                        new WaitStep(2000)
                ),
                retractLeftArmStep,
                closeMotorGlyphterStep,
                new UntilOneDoneStep(
                        new RamperDriveStep(1.2, 1),
                        new WaitStep(2000)
                ),
                new UntilOneDoneStep(
                        new WaitStep(2800),
                        new RamperTurnStep(-90, 0.5)
                ),
                new UntilOneDoneStep(
                        new RamperDriveStep(1.2, 1),
                        new WaitStep(2000)
                ),
                openMotorGlyphterStep,
                new UntilOneDoneStep(
                        new DriveStep(-0.50),
                        new WaitStep(200)
                )
        );
        runStepAutonomous("AutoTwoBlue", robot, mainStep);
    }
}