package org.eastcobbrobotics.ftc.relic.autonomous.tests;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.SayStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.autonomous.StepAutoCore;

/**
 * ECR FTC 11096 - 2017 - 2018 Relic Recovery
 * <p>
 * AutoOneRed is a minimal autonomous routine, which uses
 * StepRunner to perform several competition processes.
 */

@Autonomous(name = "TellTest", group = "Competition")

public class TellTest extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        RelicBot robot = new RelicBot();
        Step mainStep = new SequenceStep(
                new SayStep("HELLO from SayStep"),
                new WaitStep(1000),
                new SayStep("GOODBYE from SayStep")
        );

        runStepAutonomous("TellTest", robot, mainStep);
        robot.shutDown();
    }
}