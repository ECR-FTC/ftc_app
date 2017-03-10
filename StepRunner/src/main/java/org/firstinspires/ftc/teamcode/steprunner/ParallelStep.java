package org.firstinspires.ftc.teamcode.steprunner;

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

    /* When ParallelStep runs, it first checks to see if it should continue,
     * and if so it runs all of the steps that aren't stopped yet.
     */

    public void run() {
        super.run();

        Boolean stopNow = untilAllDone();

        for (Step step: stepList) {
            if (step.isRunning()) {
                if (stopNow) {
                    stopNow = false;
                    break;
                }
            } else {
                if (!stopNow) {
                    stopNow = true;
                    break;
                }
            }
        }

        // Either stop or run all of the running steps, and inherit the
        // result from whoever runs. So last step running sets the result.

        if (stopNow) {
            stop();
        } else {
            for (Step step: stepList) {
                if (step.isRunning()) {
                    step.run();
                    setResult(step.getResult());
                }
            }
        }

    }

    /* When ParallelStop is told to stop, we stop all of our steps
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

    // Whether we run until all done or just one. Must be
    // implemented by a subclass.
    abstract protected boolean untilAllDone();

}
