package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 2/22/2017
 *
 * Runs a step a fixed number of times
 */

public class CountLoopStep extends Step {

    protected int target;
    protected int count;
    protected Step repeatedStep;

    public CountLoopStep(Step repeatedStep, int count) {
        this.repeatedStep = repeatedStep;
        this.target = count;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        count = 1;
        checkNext();
    }

    @Override
    public void run() {
        super.run();
        repeatedStep.run();
        if (repeatedStep.isRunning()) {
            return;                             // it's still going
        }
        count++;
        checkNext();
    }

    // See if we should keep going, and if so, start the repeatedStep.
    protected void checkNext() {
        if (count > target) {
            stop();
        } else {
            tell("starting loop %d", count);
            repeatedStep.start(robot);
        }

    }
}
