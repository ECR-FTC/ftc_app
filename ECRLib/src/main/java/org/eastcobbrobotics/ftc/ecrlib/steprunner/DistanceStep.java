package org.eastcobbrobotics.ftc.ecrlib.steprunner;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 *
 * This step stops when the robot has gone the specified distance in game tiles.
 */
public class DistanceStep extends Step {

    protected double distance = 0;
    protected double ticks = 0;

    public DistanceStep(double distance)
    {
        this.distance = distance;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.resetDriveEncoders();
        ticks = distance * robot.getTicksPerTile();
    }

    @Override
    public void run() {
        super.run();
        double driveEncoderValue = robot.getDriveEncoderValue();
        double tiles = driveEncoderValue / robot.getTicksPerTile();

        if (driveEncoderValue >= ticks) {
            stop();
        } else {
            tell("Tiles=%.2f, Target=%.2f", tiles, distance);
        }
    }

}
