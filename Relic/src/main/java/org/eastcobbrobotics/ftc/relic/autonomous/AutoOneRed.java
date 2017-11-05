package org.eastcobbrobotics.ftc.relic.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.DriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperDriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.TurnStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnLeftStep;
//import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnRightStep;

/**
 * ECR FTC 11096 - 2017 - 2018 Relic Recovery
 * <p>
 * AutoOneRed is a minimal autonomous routine, which uses
 * StepRunner to perform several competition processes.
 */

@Autonomous(name = "AutoOneRed", group = "Competition")
//@Disabled

public class AutoOneRed extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        RelicBot robot = new RelicBot();
        Step mainStep = new SequenceStep(
                deployRightArmStep,
                flickRightBallStep,
                retractRightArmStep,
                grabGlyph,
                new SequenceStep(
                        new UntilOneDoneStep(
                                new RamperDriveStep(1,1),
                                new WaitStep(1000)
                        ),
                        new UntilOneDoneStep(
                                new WaitStep(1750),
                                new TurnLeftStep()
                        ),
                        new UntilOneDoneStep(
                                new RamperDriveStep(1.2,1),
                                new WaitStep(1000)
                        ),
                        releaseGlyph,
                        new UntilOneDoneStep(
                                new DriveStep(-0.50),
                                new WaitStep(800)
                        )

                )
        );
        runStepAutonomous("AutoOneBlue", robot, mainStep);
    }
}