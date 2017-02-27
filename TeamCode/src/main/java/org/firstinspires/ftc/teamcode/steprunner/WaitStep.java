package org.firstinspires.ftc.teamcode.steprunner;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 *
 * Step that just does nothing for a given number of milliseconds.
 */

public class WaitStep extends Step {

    protected double waitTime = 0.0;
    protected ElapsedTime timer;

    public WaitStep(double theWaitTime)
    {
        waitTime = theWaitTime;
        timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        timer.reset();
        tell("Waiting for %.2f ms", waitTime);
    }

    @Override
    public void run() {
        super.run();
        if (timer.time() >= waitTime) {
            stop();
        }
    }

}
