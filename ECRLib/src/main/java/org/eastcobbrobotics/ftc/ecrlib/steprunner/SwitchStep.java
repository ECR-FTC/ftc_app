package org.eastcobbrobotics.ftc.ecrlib.steprunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ECR FTC on 3/10/17.
 *
 * Chooses one of a set of steps based on a flag value.
 */

public class SwitchStep extends Step {

    protected String flagName;
    protected List<Step> switchSteps;
    protected Step selectedStep;

    /*
     * First argument specifies the name of the flag to use for branching.
     * Other arguments are the switch steps, one of which is selected
     * by the flag.
     */

    public SwitchStep(String flagName, Step... steps) {
        this.flagName = flagName;
        switchSteps = new ArrayList<Step>();
        for (Step step: steps) {
            switchSteps.add(step);
        }
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);

        // Which one do we use?
        Integer choice = getFlag(flagName);
        if (choice == null) {           // default for switching is zero
            choice = 0;
        }

        try {
            selectedStep = switchSteps.get(choice);
            if (selectedStep == null) {         // missing?
                tell("switch step for choice %d empty", choice);
            } else {                            // good choice, start it up
                tell("switch step for choice %d selected", choice);
                selectedStep.start(robot);
            }
        } catch (IndexOutOfBoundsException e) {
            tell("choice %d out of bounds", choice);
        }
    }


    @Override
    public void run() {
        super.run();
        if (selectedStep == null) {
            stop();                         // nothing to do; stop
        } else {
            selectedStep.run();             // run it
            if (!selectedStep.isRunning()) {
                stop();                     // it's done
            }
        }
    }

    @Override
    public void stop() {
        super.stop();
        if (selectedStep != null) {
            selectedStep.stop();
        }
    }
}
