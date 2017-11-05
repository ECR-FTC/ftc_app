package org.eastcobbrobotics.ftc.ecrlib.steprunner;

/**
 * Created by ECR FTC on 1/15/2017.
 */
public class TurnStep extends Step {

    protected double power;
    protected double heading;
    protected int direction;


    public TurnStep(double heading, double power) {
        this.heading = heading;                         // negative degrees for left, pos for right
        this.direction = (int) Math.signum(heading);    // -1 for left, 1 for right
        this.power = power;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.resetGyro();
        robot.driveTurn(power, direction);
    }

    @Override
    public void run() {
        super.run();
        double currentHeading = Math.abs(robot.getGyroHeading());
        if (currentHeading >= Math.abs(heading)) {
            stop();
        } else {
            tell("Heading=%.2f", currentHeading);
        }
    }

    @Override
    public void stop() {
        super.stop();
        robot.driveStop();
    }

}
