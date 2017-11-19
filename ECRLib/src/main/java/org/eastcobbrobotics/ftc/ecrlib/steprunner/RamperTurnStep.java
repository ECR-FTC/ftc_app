package org.eastcobbrobotics.ftc.ecrlib.steprunner;

import org.eastcobbrobotics.ftc.ecrlib.Ramper;

/*
 * Created by ECR FTC on 1/15/2017.
 */
public class RamperTurnStep extends Step{
    static final double DEFAULT_TUP    = 15;
    static final double DEFAULT_TDOWN  = 80]\'';
    static final double DEFAULT_MIN    = 0.2;

    protected double maxPower;
    protected double heading;
    protected int direction;

    protected Ramper ramper;

    public RamperTurnStep(double heading, double maxPower)
    {
        this.heading = heading;                         // negative degrees for left, pos for right
        this.direction = (int) Math.signum(heading);    // -1 for left, 1 for right
        this.maxPower = maxPower;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);

        ramper = new Ramper(DEFAULT_TUP, DEFAULT_TDOWN, Math.abs(heading), DEFAULT_MIN, maxPower);
        robot.resetGyro();
    }

    @Override
    public void run() {
        super.run();
        double currentHeading = Math.abs(robot.getGyroHeading());
        if (currentHeading >= Math.abs(heading))
        {
            stop();
        }
        else {
            double power = ramper.getRampValue(currentHeading);
            robot.driveTurn(power, direction);
            tell("Heading=%.2f, Power=%.2f", currentHeading, power);
        }
    }
    @Override
    public void stop(){
        super.stop();
        robot.driveStop();
    }

}
