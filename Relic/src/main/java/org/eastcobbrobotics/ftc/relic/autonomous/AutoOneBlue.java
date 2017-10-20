package org.eastcobbrobotics.ftc.relic.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.ServoStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SwitchStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilAllDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.ReadColorSensorStep;

import static org.eastcobbrobotics.ftc.relic.RelicBot.LEFT_ARM_ELBOW_SERVO;
import static org.eastcobbrobotics.ftc.relic.RelicBot.LEFT_ARM_WRIST_SERVO;

/**
 * ECR FTC 11096 - 2017 - 2018 Relic Recovery
 *
 * HelloAuto is a minimal autonomous routine, just to make sure we
 * can drive forward under StepRunner control.
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
                        new ReadColorSensorStep(),
                        new WaitStep(1000)
                        ),
                new UntilAllDoneStep(
                        new WaitStep(1000),
                        new SwitchStep("colorFound",
                                new ServoStep(LEFT_ARM_WRIST_SERVO, RelicBot.LEFT_WRIST_LEFT),
                                null,
                                new ServoStep(LEFT_ARM_WRIST_SERVO, RelicBot.LEFT_WRIST_RIGHT)
                        )
                ),

                retractLeftArmStep

        );
        runStepAutonomous("HelloAuto", robot, mainStep);

    }

}
