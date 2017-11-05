package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.eastcobbrobotics.ftc.relic.RelicBot;

/**
 * Created by ECR-FTC on 10/20/2017.
 */

public class TurnLeftStep extends Step {
    @Override
    public void start(StepRobot r) {
        super.start(r);
        ((RelicBot) robot).timeTurnLeft();
    }

    @Override
    public void stop() {
        super.stop();
        ((RelicBot) robot).motorFrontRight.setPower(0);
    }
}