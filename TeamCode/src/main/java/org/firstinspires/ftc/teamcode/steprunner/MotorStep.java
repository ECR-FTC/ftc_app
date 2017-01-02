package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 */
public class MotorStep extends  Step {

    @Override
    public void start(Robot r) {
        super.start(r);
        robot.motorLeftOn(.25);
    }

    @Override
    public void run() {
         super.run();

    }

    @Override
    public void stop(){
        super.stop();
        robot.motorLeftOff();
    };

}
