package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.relic.RelicBot;

import static org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot.NONE_SEEN;

/**
 * Created by ECR_FTC on 3/10/17.
 *
 * Control step to find a red or blue light and set the flag "colorFound" to 1 (red) or 2 (blue).
 *
 * TODO: specify name of flag to set
 */

public class ReadColorSensorStep extends Step {

    @Override
    public void run() {
        super.run();
        int color = ((RelicBot) robot).readColor();
        if (color != 1) {
            tell("color detected: %d", color);
            setFlag("colorFound", color);
            stop();
        }
    }
}

