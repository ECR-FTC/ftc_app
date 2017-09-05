package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC on 3/12/17.
 *
 * Step to set or clear a flag.
 */

public class SetFlagStep extends Step {

    protected String name;
    protected Integer value;

    public SetFlagStep(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void run() {
        super.run();
        if (value == null) {
            clearFlag(name);
        } else {
            setFlag(name, value);
        }
        stop();

    }
}
