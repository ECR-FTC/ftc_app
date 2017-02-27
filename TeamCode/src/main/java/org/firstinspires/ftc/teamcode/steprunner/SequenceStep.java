package org.firstinspires.ftc.teamcode.steprunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 *
 * Runs a set of steps one at a time.
 */

public class SequenceStep extends Step {
    protected List<Step> stepList;
    protected Integer currentStep = 0;

    public SequenceStep() {
        stepList = new ArrayList<Step>();
    }

    public SequenceStep(Step... steps) {
        stepList = new ArrayList<Step>();
        for (Step step: steps) {
            stepList.add(step);
        }
    }

    public SequenceStep(List<Step> theStepList) {
        stepList = theStepList;
    }


    public void add(Step step) {
        stepList.add(step);
    }

    /* When SequenceStep starts, it starts the first step in its list. */

    public void start(StepRobot r) {
        super.start(r);

        // If we don't have an empty list, start the first step.
        if (stepsLeft()) {
            stepList.get(currentStep).start(robot);
        }

    }

    /* When SequenceStep runs, it runs the current step in its list. If that
        step is done, it starts the next step -- if there is one. */

    public void run() {
        super.run();

        Step step = stepList.get(currentStep);
        if (step.isRunning()) {
            step.run();
        } else {
            currentStep++;
            if (stepsLeft()) {
                stepList.get(currentStep).start(robot);
            } else {
                stop();
            }
        }
    }

    /* When SequenceStep is told to stop, stop our current Step
        and ourselves.
     */

    public void stop() {
        if (stepsLeft()) {
            stepList.get(currentStep).stop();
        }
        super.stop();
    }


    /*
        Check to see if we're out of steps to run.
    */
    protected Boolean stepsLeft() {
        return (currentStep < stepList.size());
    }

}
