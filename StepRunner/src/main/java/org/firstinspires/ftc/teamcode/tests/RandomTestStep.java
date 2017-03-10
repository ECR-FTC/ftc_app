package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.StepRobot;

/**
 * Created by ECR FTC on 3/10/2017
 *
 * Step used for loop testing. This one "rolls" a 6-sided die each time it runs;
 * on a roll of 1, it stops with result 0 (done), on a roll of 2 or 3 it stops with result 1
 * (continue), and on any other roll it just keeps running.
 */

public class RandomTestStep extends Step {

    @Override
    public void run() {
        int roll = (int)(Math.random() * 6 + 1);
        switch (roll) {
            case 1:
                tell("Rolled %d, now done", roll);
                setResult(RESULT_DONE);
                stop();
                break;
            case 2:
            case 3:
                tell("Rolled %d, continue loop", roll);
                setResult(RESULT_CONTINUE);
                stop();
                break;
            default:
                tell("Rolled %d, continuing to check", roll);
                break;
        }
    }

}
