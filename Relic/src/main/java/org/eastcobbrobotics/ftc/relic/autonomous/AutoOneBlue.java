package org.eastcobbrobotics.ftc.relic.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.DriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperDriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.ServoStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SwitchStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilAllDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.CheckImageStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.ReadColorSensorStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnLeftStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.TurnRightStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.JewelFlipLeftStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.JewelFlipRightStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperTurnStep;

import static org.eastcobbrobotics.ftc.relic.RelicBot.LEFT_ARM_ELBOW_SERVO;
import static org.eastcobbrobotics.ftc.relic.RelicBot.LEFT_ARM_WRIST_SERVO;

/**
 * ECR FTC 11096 - 2017 - 2018 Relic Recovery
 * <p>
 * AutoOneBlue is a minimal autonomous routine, which uses
 * StepRunner to perform several competition processes.
 */

@Autonomous(name = "AutoOneBlue", group = "Competition")
//@Disabled

public class AutoOneBlue extends StepAutoCore {

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
                grabGlyph,
                new SequenceStep(
                        new UntilOneDoneStep(
                                new CheckImageStep(),
                                new WaitStep(1000)
                        ),
                        new UntilOneDoneStep(
                                new RamperDriveStep(1.2, 1),
                                new WaitStep(3000)
                        ),
                        // Code for left column
                       /* new SwitchStep("imageFound",
                                new UntilOneDoneStep(new UntilOneDoneStep(
                                        new WaitStep(1750),
                                        new RamperTurnStep(30, 0.5)
                                ),
                                        new UntilOneDoneStep(
                                                new RamperDriveStep(1, 1),
                                                new WaitStep(1000)
                                        )),
                                new UntilOneDoneStep(new UntilOneDoneStep(
                                        new WaitStep(1750),
                                        new RamperTurnStep(44, 0.5)
                                ),
                                        new UntilOneDoneStep(
                                                new RamperDriveStep(1.2, 1),
                                                new WaitStep(1000)
                                        )
                                ),
                                new UntilOneDoneStep(new UntilOneDoneStep(
                                        new WaitStep(1750),
                                        new RamperTurnStep(55, 0.5)
                                ),
                                        new UntilOneDoneStep(
                                                new RamperDriveStep(1.4, 1),
                                                new WaitStep(1000)
                                        )
                                )
                        ),
                        */
                        new UntilOneDoneStep(
                                new WaitStep(1750),
                                new RamperTurnStep(44, 0.5)
                        ),
                        new UntilOneDoneStep(
                                new RamperDriveStep(1.5, 1),
                                new WaitStep(3000)
                        ),
                        releaseGlyph,
                        new UntilOneDoneStep(
                                new DriveStep(-0.50),
                                new WaitStep(200)
                        )
                )

        );
        runStepAutonomous("AutoOneBlue", robot, mainStep);
    }
}