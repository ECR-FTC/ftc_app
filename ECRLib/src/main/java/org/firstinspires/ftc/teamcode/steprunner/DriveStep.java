package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 */
public class DriveStep extends Step {

    protected double driveSpeed = 0.0;

    public DriveStep(double theSpeed)
    {
        driveSpeed = theSpeed;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.driveStraight(driveSpeed);
    }

    @Override
    public void run() {
         super.run();

    }

    @Override
    public void stop(){
        super.stop();
        robot.driveStop();
    };

}
