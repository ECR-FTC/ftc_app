package org.firstinspires.ftc.teamcode.autonomous.steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.firstinspires.ftc.teamcode.MorganaBot;

/**
 * Created by ECR FTC 11096 on 03/05/2017.
 */
public class StopShooterStep extends Step {


    public StopShooterStep() {

    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        ((MorganaBot) robot).setShootPower(0);
    }

    @Override
    public void run() {
        super.run();
        stop();

    }

    @Override
    public void stop() {
        super.stop();

    }

    ;

}
