package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 *
 * Runs a step a fixed number of times; simple extension of WhileStep
 */

public class CountLoopStep extends WhileStep {

    public CountLoopStep(Step repeatedStep, int count) {
        super(repeatedStep, new CountChecker(count));
    }

}
