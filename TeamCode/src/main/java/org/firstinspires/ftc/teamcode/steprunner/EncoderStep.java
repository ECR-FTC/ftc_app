package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 *
 * This step stops when the drive encoder exceeds the specified value. Use in a
 * ParallelStep along some driving step.
 */
public class EncoderStep extends Step {

    protected double distance = 0;

    public EncoderStep(double distance)
    {
        this.distance = distance;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.resetDriveEncoders();
    }

    @Override
    public void run() {
        super.run();
        double driveEncoderValue = robot.getDriveEncoderValue();

        if (driveEncoderValue >= distance) {
            stop();
        } else {
            tell("Encoder=%.2f, Target=%.2f", driveEncoderValue, distance);
        }
    }

}
