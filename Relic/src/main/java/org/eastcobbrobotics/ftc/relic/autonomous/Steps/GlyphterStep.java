package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.eastcobbrobotics.ftc.relic.RelicBot;

/**
 * Created by ECR-FTC on 10/20/2017.
 */

public class GlyphterStep extends Step {
    protected int direction = 0;

    public GlyphterStep(int theDirection)
    {
        direction = theDirection;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        ((RelicBot) robot).driveGlyphter(direction);
    }

    @Override
    public void stop() {
        super.stop();
        ((RelicBot) robot).driveGlyphter(0);
    }
}