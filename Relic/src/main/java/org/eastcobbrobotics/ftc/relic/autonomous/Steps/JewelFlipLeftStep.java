package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.relic.RelicBot;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by ECR_FTC on 3/10/17.
 */

public class JewelFlipLeftStep extends Step {

    @Override
    public void run() {
        super.run();
        int color = ((RelicBot) robot).readColorLeft();
        tell("color detected: %d", color);

        switch (color) {
            case RelicBot.COLOR_BLUE:
                robot.setServo(RelicBot.LEFT_ARM_WRIST_SERVO, RelicBot.LEFT_WRIST_LEFT);
                break;

            case RelicBot.COLOR_RED:
                robot.setServo(RelicBot.LEFT_ARM_WRIST_SERVO, RelicBot.LEFT_WRIST_RIGHT);
                break;

            case RelicBot.COLOR_UNKNOWN:
                robot.setServo(RelicBot.LEFT_ARM_WRIST_SERVO, RelicBot.LEFT_WRIST_CENTER);
                break;
        }
        stop();
    }
}