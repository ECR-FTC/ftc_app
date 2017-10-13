package org.eastcobbrobotics.ftc.ecrlib.steprunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 *
 * Runs a set of steps all at once. By default, the
 *  class stops when all steps are done.
 */

abstract public class ParallelStep extends Step {
    protected List<Step> stepList;

    public ParallelStep() {
        stepList = new ArrayList<Step>();
    }

    public ParallelStep(Step... steps) {
        stepList = new ArrayList<Step>();
        for (Step step: steps) {
            stepList.add(step);
        }
    }

    public ParallelStep(List<Step> theStepList) {
        stepList = theStepList;
    }

    public void add(Step step) {
        stepList.add(step);
    }
    /* When ParallelStep starts, it starts all steps in its list. */

    public void start(StepRobot r) {
        super.start(r);
        for(Step step : stepList) {
           step.start(robot);
        }
    }

    /* Each ParallelStep subclass handles its own run logic. */

    public void run() {
        super.run();
    }

    /* When a ParallelStop is told to stop, we stop all of our steps
        that are running, and then stop ourselves.
     */

    public void stop() {
        for (Step step: stepList) {
            if (step.isRunning()) {
                step.stop();
            }
        }
        super.stop();
    }

}
