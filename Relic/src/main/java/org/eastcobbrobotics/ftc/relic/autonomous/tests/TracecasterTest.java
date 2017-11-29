package org.eastcobbrobotics.ftc.relic.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.Tracecaster;
import org.eastcobbrobotics.ftc.relic.autonomous.StepAutoCore;

/**
 * ECR FTC 11096 - 2017 - 2018 Relic Recovery
 *
 * This tiny autonomous tests Tracecaster sending.
 */

@Autonomous(name = "Tracecaster", group = "Competition")

public class TracecasterTest extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        RelicBot robot = new RelicBot();
        Tracecaster tc = new Tracecaster(hardwareMap.appContext, 11096);

        tc.post("Waiting for start");
        waitForStart();
        tc.post("Starting up");

        for(int i = 0 ; i < 20 ; i++) {
            tc.post(String.format("Hello #%d", i));
            sleep(100);
        }
        tc.post("That is all!");
        tc.close();

    }
}