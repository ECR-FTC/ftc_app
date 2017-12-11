package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.relic.RelicBot;

import static org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot.NONE_SEEN;

/**
 * Created by ECR_FTC on 3/10/17.
 *
 * Step reads the specified color sensor and sets the colorFound flag to
 * COLOR_RED, COLOR_BLUE, or COLOR_UNKNOWN.  The step stops when it sees
 * red or blue.
 *
 */

public class ReadColorSensorStep extends Step {

    protected ColorSensor sensor;

    public ReadColorSensorStep(ColorSensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public void run() {
        super.run();
        int color = ((RelicBot) robot).readColor(sensor);
        setFlag("colorFound", color);

        // If we got something other than unknown, report that and
        // stop the step.
        if (color != RelicBot.COLOR_UNKNOWN) {
            tell("color detected: %d", color);
            stop();
        }
    }
}

