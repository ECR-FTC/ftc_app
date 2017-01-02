package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 */
public class TurnStep extends  Step {

    protected double driveSpeed = 0;
    protected int direction = 0;

    public TurnStep(double theSpeed, int theDirection)//1 is counter clockwise
    {
        driveSpeed = theSpeed;
        direction = theDirection;
    }

    @Override
    public void start(Robot r) {
        super.start(r);
        robot.motorRightOn(driveSpeed*direction);
        robot.motorLeftOn(driveSpeed*(-direction));
    }

    @Override
    public void run() {
         super.run();

    }

    @Override
    public void stop(){
        super.stop();
        robot.allMotorsOff();
    };

}
