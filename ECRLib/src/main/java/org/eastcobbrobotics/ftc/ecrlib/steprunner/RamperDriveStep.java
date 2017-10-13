package org.eastcobbrobotics.ftc.ecrlib.steprunner;

import org.eastcobbrobotics.ftc.ecrlib.Ramper;

/**
 * Created by ECR FTC on 1/15/2017.
 * This step drives a specified distance (in game tiles) using a Ramper to
 * gradually increase power to start and decrease at end.
 */

public class RamperDriveStep extends Step {
    static final double DEFAULT_TUP = 2000;
    static final double DEFAULT_TDOWN = 2000;
    static final double DEFAULT_MIN = 0.25;

    protected double maxPower;
    protected double distance;
    protected double ticks;

    protected Ramper ramper;

    public RamperDriveStep(double distance, double maxPower) {
        this.distance = distance;
        this.maxPower = maxPower;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.resetDriveMotors();
        robot.resetDriveEncoders();
        ticks = distance * robot.getTicksPerTile();
        ramper = new Ramper(DEFAULT_TUP, DEFAULT_TDOWN, ticks, DEFAULT_MIN, maxPower);
    }

    @Override
    public void run() {
        super.run();
        double driveEncoderValue = robot.getDriveEncoderValue();
        double tiles = driveEncoderValue / robot.getTicksPerTile();
        if (driveEncoderValue >= ticks) {
            stop();
        } else {
            double power = ramper.getRampValue(driveEncoderValue);
            robot.driveStraight(power);
            tell("Tiles=%.2f, Power=%.2f", tiles, power);
        }
    }

    @Override
    public void stop() {
        super.stop();
        robot.driveStop();
    }

}
