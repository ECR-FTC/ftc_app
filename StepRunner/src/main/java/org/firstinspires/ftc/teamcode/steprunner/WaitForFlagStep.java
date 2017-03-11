package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC on 3/10/17.
 *
 * Waits for a flag to be raised or set to a particular value
 */

public class WaitForFlagStep extends Step {

    protected String name;
    protected Integer value;

    public WaitForFlagStep(String name) {
        this.name = name;
        this.value = null;
    }

    public WaitForFlagStep(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void run() {
        Integer flagValue = getFlag(name);
        if ((flagValue != null) && (value == null || value.equals(flagValue))) {
            tell("wait for flag %s ended with value %d", name, flagValue);
            stop();
        } else {
            if (value == null) {
                tell("waiting for any flag %s, now %d", name, flagValue);
            } else {
                tell("waiting for flag %s to be %d, now %d", name, value, flagValue);
            }
        }
    }
}
