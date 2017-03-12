package org.firstinspires.ftc.teamcode.steprunner;

import java.util.List;

/**
 * Created by ECR FTC 11096  on 12/2/16.
 */

public class UntilOneDoneStep extends ParallelStep {

    public UntilOneDoneStep() {
        super();
    }

    public UntilOneDoneStep(List<Step> theStepList) {
        super(theStepList);
    }

    public UntilOneDoneStep(Step... steps) {
        super(steps);
    }

    public void run() {
        super.run();

        Boolean stopNow = false;
        for (Step step : stepList) {
            if (step.isRunning()) {
                step.run();
            } else {
                stopNow = true;
            }
        }

        if (stopNow) {
            stop();
        }
    }
}
