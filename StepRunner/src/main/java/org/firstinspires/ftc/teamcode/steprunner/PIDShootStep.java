package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC on 1/15/2017.
 *
 * This step drives a specified distance (in encoder ticks) using a Ramper to
 * gradually increase power to start and decrease at end.
 */

public class PIDShootStep extends Step {
    static final double DEFAULT_TUP    = 2000;
    static final double DEFAULT_TDOWN  = 2000;
    static final double DEFAULT_MIN    = 0.25;

    protected double maxPower;
    protected double distance;

    protected Ramper ramper;

    public PIDShootStep(double distance, double maxPower)
    {
        this.distance = distance;
        this.maxPower = maxPower;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.resetDriveMotors();
        robot.resetDriveEncoders();
        ramper = new Ramper(DEFAULT_TUP, DEFAULT_TDOWN, distance, DEFAULT_MIN, maxPower);
    }

    @Override
    public void run() {
        super.run();
        double driveEncoderValue = robot.getDriveEncoderValue();
        if (driveEncoderValue >= distance) {
            stop();
        }
        else {
            double power = ramper.getRampValue(driveEncoderValue);
            robot.driveStraight(power);
            tell("Encoder=%.2f, Power=%.2f", driveEncoderValue, power);
        }
    }
    @Override
    public void stop(){
        super.stop();
        robot.driveStop();
    }

}

