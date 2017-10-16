package org.eastcobbrobotics.ftc.relic.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.ServoStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilAllDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.ReadColorSensorStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex
 *
 * HelloAuto is a minimal autonomous routine, just to make sure we
 * can drive forward under StepRunner control.
 */

@Autonomous(name = "HelloAuto", group = "Testing")
//@Disabled

public class AutoOneRed extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        RelicBot robot = new RelicBot();
        Step mainStep = new SequenceStep(
                new UntilAllDoneStep(
                        new WaitStep(2000),
                        new ServoStep(4, RelicBot.LEFT_WRIST_CENTER),
                        new ServoStep(2, RelicBot.LEFT_JEWEL_DOWN)
                ),
                new ReadColorSensorStep()//,
                //new
        );
        runStepAutonomous("HelloAuto", robot, mainStep);

    }

}
