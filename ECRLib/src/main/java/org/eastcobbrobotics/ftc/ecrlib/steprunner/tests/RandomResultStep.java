package org.eastcobbrobotics.ftc.ecrlib.steprunner.tests;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;

/**
 * Created by ECR FTC on 3/10/2017
 *
 * Step used for switch testing. Sets "roll" flag to a random value in range specified.
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
        setFlag("roll", roll);
        stop();
    }

}
