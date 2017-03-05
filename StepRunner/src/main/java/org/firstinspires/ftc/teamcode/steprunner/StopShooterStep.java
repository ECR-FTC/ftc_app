package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 03/05/2017.
 */
public class StopShooterStep extends Step {


    public StopShooterStep()
    {

    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.setShootPower(0);
    }

    @Override
    public void run() {
         super.run();

    }

    @Override
    public void stop(){
        super.stop();

    };

}
