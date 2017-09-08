package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC on 3/10/17.
 *
 * Simple step just tells the message it's created with when it's run, and then stops.
 */

public class SayStep extends Step {

    protected String message;

    public SayStep(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        super.run();
        tell(message);
        stop();
    }
}
