package org.firstinspires.ftc.teamcode.steprunner;

import static org.firstinspires.ftc.teamcode.steprunner.StepRobot.NONE_SEEN;

/**
 * Created by ECR_FTC on 3/10/17.
 *
 * Control step to find a red or blue light and set the result to RED_SEEN (1) or BLUE_SEEN (2).
 */

public class FindRedBlueStep extends Step {

    @Override
    public void run() {
        super.run();
        int color = robot.getColorSeen();
        if (color != NONE_SEEN) {
            tell("color detected: %d", color);
            setResult(color);
            stop();
        }
    }
}
