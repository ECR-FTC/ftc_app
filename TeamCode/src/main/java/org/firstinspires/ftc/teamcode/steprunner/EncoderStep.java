package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 */
public class EncoderStep extends  Step {

    protected double distance = 0;

    public EncoderStep(int theDistance)
    {
        distance = theDistance;
    }

    @Override
    public void start(Robot r) {
        super.start(r);

    }

    @Override
    public void run() {
        super.run();
        if (robot.encoderRight() >= distance) {
            stop();
        }
    }

    @Override
    public void stop(){
        super.stop();
        robot.allMotorsOff();
    }

}
