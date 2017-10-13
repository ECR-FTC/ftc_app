package org.firstinspires.ftc.teamcode.autonomous.steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.firstinspires.ftc.teamcode.MorganaBot;

/**
 * Created by ECR FTC 11096 on 03/05/2017.
 * Runs the shooter without PID control. You'll need to delay to give it time to spin up.
 */
public class SimpleRunShooterStep extends Step {

    protected double shooterSpeed = 0.0;

    public SimpleRunShooterStep(double theSpeed) {
        shooterSpeed = theSpeed;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        ((MorganaBot) robot).setShootPower(shooterSpeed);
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
