package org.firstinspires.ftc.teamcode.steprunner;

import static org.firstinspires.ftc.teamcode.steprunner.StepRobot.NONE_SEEN;

/**
 * Created by ECR_FTC on 3/10/17.
 * <p>
 * Control step to find a red or blue light and set the flag "colorFound" to 1 (red) or 2 (blue).
 * <p>
 * TODO: specify name of flag to set
 */

public class CheckColorStep extends Step {

    @Override
    public void run() {
        super.run();
        int color = robot.getColorSeen();
        tell("color detected: %d", color);
        setFlag("colorFound", color);
    }
}
