package org.firstinspires.ftc.teamcode.autonomous.steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.firstinspires.ftc.teamcode.MorganaBot;

import static org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot.NONE_SEEN;

/**
 * Created by ECR_FTC on 3/10/17.
 *
 * Control step to find a red or blue light and set the flag "colorFound" to 1 (red) or 2 (blue).
 *
 * TODO: specify name of flag to set
 */

public class FindRedBlueOnBlueStep extends Step {

    @Override
    public void run() {
        super.run();
        int color = ((MorganaBot) robot).getColorSeenLeft();
        if (color != NONE_SEEN) {
            tell("color detected: %d", color);
            setFlag("colorFound", color);
            stop();
        }
    }
}
