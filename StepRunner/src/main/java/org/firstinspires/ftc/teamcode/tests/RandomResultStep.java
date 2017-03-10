package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.steprunner.Step;

/**
 * Created by ECR FTC on 3/10/2017
 *
 * Step used for switch testing. Returns a random value in range specified.
 */

public class RandomResultStep extends Step {

    protected int minVal;
    protected int maxVal;

    public RandomResultStep(int minVal, int maxVal) {
        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    @Override
    public void run() {
        int roll = (int)(Math.random() * (maxVal - minVal + 1) + minVal);
        setResult(roll);
        stop();
    }

}
