package org.firstinspires.ftc.teamcode.steprunner;

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
        robot.setShootPower(shooterSpeed);
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
