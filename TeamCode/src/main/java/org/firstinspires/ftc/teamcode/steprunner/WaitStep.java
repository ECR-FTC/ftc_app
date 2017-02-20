package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 *
 * Step that just does nothing for a given number of milliseconds.
 */

public class WaitStep extends Step {

    protected long waitTime = 0;

    public WaitStep(long theWaitTime)
    {
        waitTime = theWaitTime;
    }

    @Override
    public void start(Robot r) {
        super.start(r);
        log(String.format("waiting %d ms", waitTime));
    }

    @Override
    public void run() {
        super.run();
        if (elapsedTimeMillis() >= waitTime) {
            stop();
        }
    }

}
