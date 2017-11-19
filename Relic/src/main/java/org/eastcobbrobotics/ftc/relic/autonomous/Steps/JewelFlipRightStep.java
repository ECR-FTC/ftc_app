package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.ServoStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;

/**
 * Created by ECR_FTC on 3/10/17.
 *
 * Control step to find a red or blue light and set the flag "colorFound" to 1 (red) or 2 (blue).
 *
 * TODO: specify name of flag to set
 */

public class JewelFlipRightStep extends Step {

    @Override
    public void run() {
        super.run();
        int color = ((RelicBot) robot).readColorRight();
        tell("color detected: %d", color);

        if(color == 0)
        {
            robot.setServo(RelicBot.RIGHT_ARM_WRIST_SERVO,RelicBot.RIGHT_WRIST_LEFT);
        }
        else if(color == 1)
        {
            robot.setServo(RelicBot.RIGHT_ARM_WRIST_SERVO,RelicBot.RIGHT_WRIST_CENTER);
        }
        else if(color == 2)
        {
            robot.setServo(RelicBot.RIGHT_ARM_WRIST_SERVO,RelicBot.RIGHT_WRIST_RIGHT);
        }
        stop();
    }
}