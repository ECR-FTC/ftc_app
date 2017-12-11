package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.ServoStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;

/**
 * Created by ECR_FTC on 3/10/17.
 *
 */

public class JewelFlipRightStep extends Step {

    @Override
    public void run() {
        super.run();
        int color = ((RelicBot) robot).readColorRight();
        tell("color detected: %d", color);

        switch (color) {
            case RelicBot.COLOR_BLUE:
                robot.setServo(RelicBot.RIGHT_ARM_WRIST_SERVO,RelicBot.RIGHT_WRIST_RIGHT);
                break;

            case RelicBot.COLOR_RED:
                robot.setServo(RelicBot.RIGHT_ARM_WRIST_SERVO,RelicBot.RIGHT_WRIST_LEFT);
                break;

            case RelicBot.COLOR_UNKNOWN:
                robot.setServo(RelicBot.RIGHT_ARM_WRIST_SERVO,RelicBot.RIGHT_WRIST_CENTER);
                break;

        }

        stop();
    }
}