package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 *
 * Runs one of two Steps once a condition has been determined.
 */

public class IfElseStep extends Step {
    protected Checker checker;  // we ask this which branch to take
    protected Step ifStep;      // do this if condition ends true
    protected Step elseStep;    // do this if condition ends false
    protected Step chosenStep;  // set to one of those once we know what to do

    public IfElseStep(Checker checker, Step ifStep, Step elseStep) {
        this.checker = checker;
        this.ifStep = ifStep;
        this.elseStep = elseStep;
    }

    /* When IfElseStep starts, it clears out its chosenStep because
       we haven't chosen yet. */

    public void start(StepRobot r) {
        super.start(r);
        chosenStep = null;
    }

    /* When IfElseStep runs, it runs the chosenStep if there is one.
        Otherwise, it evaluates its condition to see if it's time to
        choose one or the other step.
    */
    public void run() {
        super.run();

        // If we've already chosen a step, run it if it
        // still wants to run... otherwise stop us, we're done.
        if (chosenStep != null) {
            if (chosenStep.isRunning()) {
                chosenStep.run();
            } else {
                stop();
            }
            return;
        }

        // Okay, we need to see if it's time to decide between
        // our options.
        switch(checker.check()) {

            case Checker.STOP_CHOICE:
                stop();
                return;                 // gave up

            case Checker.CONTINUE_CHOICE:
                return;                 // no choice yet; nothing to do

            case Checker.TRUE_CHOICE:
                chosenStep = ifStep;
                break;                  // use the 'if' choice

            case Checker.FALSE_CHOICE:
                chosenStep = elseStep;
                break;                   // use the 'else' choice

            default:
                stop();                 // THIS SHOULD NOT HAPPEN!
                return;
        }

        // If we're here, we've made a choice. If the chosen step is real, start it;
        // but if not, our choice was a null step -- so just stop.
        if (chosenStep != null) {
            chosenStep.start(robot);
        } else {
            stop();
        }

    }

    /* When IfElseStepis told to stop, stop our chosenStep if we have one.
     */

    public void stop() {
        if (chosenStep != null) {
            chosenStep.stop();
        }
        super.stop();
    }


}
