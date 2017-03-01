package org.firstinspires.ftc.teamcode.steprunner;

import java.util.Locale;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 *
 * Step that just does nothing for a given number of milliseconds.
 * This one uses the Java class rather than the robot core ElapsedTime class.
 */

public class JavaWaitStep extends Step {

    protected double waitTime = 0.0;
    protected double startTime;

    public JavaWaitStep(double theWaitTime)
    {
        waitTime = theWaitTime;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        startTime = System.currentTimeMillis();
        tell("Waiting for %.2f ms", waitTime);
    }

    @Override
    public void run() {
        super.run();
        double elapsed = System.currentTimeMillis() - startTime;

        tell("elapsed=%.2f", elapsed);
        if (elapsed >= waitTime) {
            stop();
        }
    }

}
