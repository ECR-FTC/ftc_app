package org.firstinspires.ftc.teamcode.steprunner;
import java.util.List;

/**
 * Created by by ECR FTC 11096  on 12/2/16.
 */

public class UntilAllDoneStep extends ParallelStep {

    public UntilAllDoneStep() {
        super();
    }

    public UntilAllDoneStep(List<Step> theStepList) {
        super(theStepList);
    }

    public UntilAllDoneStep(Step... steps) {
        super(steps);
    }

    public void run() {
        super.run();

        // Go through each step and run any that are still running. If there aren't any,
        // stop.
        Boolean stopNow = true;
        for (Step step: stepList) {
            if (step.isRunning()) {
                step.run();
                stopNow = false;                // and we will keep going
            }
        }

        if (stopNow) {
            stop();
        }

    }
}
