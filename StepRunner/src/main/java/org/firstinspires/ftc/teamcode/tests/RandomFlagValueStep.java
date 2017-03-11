package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.steprunner.Step;

/**
 * Created by ECR FTC on 3/10/2017
 *
 * Step used for flag testing. Just assigns a random value (range specified) to a flag of
 * a given name.
 */

public class RandomFlagValueStep extends Step {

    protected String name;
    protected int minVal;
    protected int maxVal;

    public RandomFlagValueStep(String name, int minVal, int maxVal) {
        this.name = name;
        this.minVal = minVal;
        this.maxVal = maxVal;
    }

    @Override
    public void run() {
        int roll = (int)(Math.random() * (maxVal - minVal + 1) + minVal);
        setFlag(name, roll);
        tell("flag %s set to %d", name, roll);
    }

}
