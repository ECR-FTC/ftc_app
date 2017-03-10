package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 * <p>
 * Runs a step as long as a condition is true
 */

public class WhileStep extends Step {
    protected Step controlStep;     // the step that controls whether we continue
    protected Step repeatedStep;    // the step that we do repeatedly
    protected boolean checking;     // true if checking condition; false if running repeatedStep

    public WhileStep(Step controlStep, Step repeatedStep) {
        this.controlStep = controlStep;
        this.repeatedStep = repeatedStep;
    }

    /*
     * When WhileStep starts, start our control step.
     */

    public void start(StepRobot r) {
        super.start(r);
        checking = true;
        controlStep.start(robot);
    }

    /*
     * When WhileStep runs, if we're "checking", give our controlStep
     * a chance to run and see if we should continue -- which we do
     * if its result is positive. If we're not "checking", give our
     * repeatedStep a chance to run, and if it's done, we start
     * checking again.
     */
    public void run() {
        super.run();
        if (checking) {
            controlStep.run();
            if (!controlStep.isRunning()) {             // if control step is ready to check,
                if (controlStep.getResult() <= 0) {     // see if we should stop looping
                    stop();                             // yes, we're all done
                } else {                                // control says we should keep going
                    checking = false;
                    repeatedStep.start(robot);          // start up the repeated step
                }
            }
        } else {                                    // but if we're running the repeatedStep,
            repeatedStep.run();
            if (repeatedStep.isRunning()) {
                return;                             // still working on our repeated step
            }
            checking = true;                        // repeated step is done; check again
            controlStep.start(robot);               // TODO: use restart method?
        }
    }

    /*
     * When WhileStep is told to stop, stop both of our steps.
     */

    public void stop() {
        super.stop();
        if (!checking) {
            repeatedStep.stop();
        }
        controlStep.stop();
    }

}
