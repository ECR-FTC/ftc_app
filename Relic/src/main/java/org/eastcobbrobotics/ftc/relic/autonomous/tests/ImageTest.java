package org.eastcobbrobotics.ftc.relic.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperDriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.autonomous.StepAutoCore;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.CheckImageStep;

/**
 * Created by ECR-FTC on 10/30/2017.
 */

@Autonomous(name = "ImageTest", group = "Tests")
public class ImageTest extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        RelicBot robot = new RelicBot();
        Step mainStep = new SequenceStep(
                new CheckImageStep(),
                new WaitStep(10000)
        );
        runStepAutonomous("ImageTest", robot, mainStep);
    }
}
