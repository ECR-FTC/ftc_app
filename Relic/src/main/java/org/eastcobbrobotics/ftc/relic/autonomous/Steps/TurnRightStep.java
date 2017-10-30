package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.eastcobbrobotics.ftc.relic.RelicBot;

/**
 * Created by ECR-FTC on 10/20/2017.
 */

public class TurnRightStep extends Step {
    @Override
    public void start(StepRobot r) {
        super.start(r);
        ((RelicBot) robot).timeTurnRight();
    }

    @Override
    public void stop() {
        super.stop();
        ((RelicBot) robot).motorFrontLeft.setPower(0);
    }
}