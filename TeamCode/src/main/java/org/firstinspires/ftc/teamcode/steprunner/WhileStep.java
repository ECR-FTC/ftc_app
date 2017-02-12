package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 *
 * Runs a step as long as a condition is true
 */

public class WhileStep extends Step {
    protected Checker checker;
    protected Step repeatedStep;  // the step that we do repeatedly

    public WhileStep(Step repeatedStep) {
        this.checker = checker;
        this.repeatedStep = repeatedStep;
    }

    /* When WhileStep starts, check our condition, and start
     * our repeatedStep unless we're stopped already. */

    public void start(Robot r) {
        super.start(r);
        checker.start();
        if (checkContinue()) {
            repeatedStep.start(robot);
        } else {
            stop();
        }
    }

    /* When whileStep runs, it checks the condition to see if we should
        contine or possibly restart our repeatedStep.
    */
    public void run() {
        super.run();
        if (repeatedStep.isRunning()) {
            repeatedStep.run();
        } else {
            repeatedStep.stop();
            if (checkContinue()) {
                repeatedStep.start(robot);
            } else {
                stop();
            }
        }
    }

    /* When WhileStep is told to stop, stop our repeatedStep.
     */

    public void stop() {
        repeatedStep.stop();
        super.stop();
    }

    /* Ask the checker whether we should keep going.
     */

    boolean checkContinue() {
        switch(checker.check()) {
            case Checker.FALSE_CHOICE:
            case Checker.STOP_CHOICE:
                return false;
            case Checker.CONTINUE_CHOICE:
            case Checker.TRUE_CHOICE:
                return true;
            default:
                return false;
        }
    }
}
