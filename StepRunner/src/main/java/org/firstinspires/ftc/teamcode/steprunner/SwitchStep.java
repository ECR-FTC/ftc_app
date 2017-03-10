package org.firstinspires.ftc.teamcode.steprunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ECR FTC on 3/10/17.
 *
 * Runs a control step that returns a result that determines which other step
 * in a list should run.
 */

public class SwitchStep extends Step {

    protected Step controlStep;
    protected List<Step> switchSteps;
    protected Step selectedStep;

    /*
     * First argument specifies the "control step" that runs until it provides
     * a result. Other arguments are the switch steps, one of which is selected
     * by the result.
     */

    public SwitchStep(Step controlStep, Step... steps) {
        this.controlStep = controlStep;
        switchSteps = new ArrayList<Step>();
        for (Step step: steps) {
            switchSteps.add(step);
        }
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        this.selectedStep = null;
        controlStep.start(robot);
    }

    @Override
    public void run() {
        super.run();
        if (selectedStep != null) {         // if we've chosen one,
            selectedStep.run();             // let it run
            if (!selectedStep.isRunning()) {
                stop();                     // it's done
            }
        } else {
            controlStep.run();
            if (!controlStep.isRunning()) {             // if control step is now done,
                int choice = controlStep.getResult();   // get its result
                try {
                    selectedStep = switchSteps.get(choice);
                    if (selectedStep == null) {         // missing?
                        tell("switch step for result %d empty", choice);
                        stop();
                    } else {                            // good choice, start it up
                        tell("switch step for result %d selected", choice);
                        selectedStep.start(robot);
                    }
                } catch (IndexOutOfBoundsException e) {
                    tell("result %d out of bounds", choice);
                    stop();
                }
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        if (selectedStep != null) {
            selectedStep.stop();
        } else {
            controlStep.stop();
        }
    }
}
