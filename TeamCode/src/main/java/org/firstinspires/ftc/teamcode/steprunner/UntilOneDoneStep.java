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
    protected boolean untilAllDone() {
        return false;

    }
}
