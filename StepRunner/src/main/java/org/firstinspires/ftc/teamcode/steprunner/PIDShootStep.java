package org.firstinspires.ftc.teamcode.steprunner;


/**
 * Created by ECR FTC on 1/15/2017.
 *
 * This step drives a specified distance (in encoder ticks) using a Ramper to
 * gradually increase power to start and decrease at end.
 */

public class PIDShootStep extends Step {
    static final double DEFAULT_TMIN   = 0.15;
    static final double DEFAULT_TMAX   = 1.00;
    static final double DEFAULT_P_CHANGE = 0.00;
    static final double DEFAULT_I_CHANGE = 0.02;
    static final double DEFAULT_D_CHANGE = 0.00;
    static final double DEFAULT_TARGET   = 0.75;
    double ticks = 0, ticks2 = 0;
    double time = 0, time2 = 0;


    public PID pid;

    public PIDShootStep()
    {
        //this. _.______ = _.______
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.resetDriveMotors();
        robot.resetDriveEncoders();
        pid = new PID(DEFAULT_TMAX, DEFAULT_TMIN, DEFAULT_P_CHANGE, DEFAULT_I_CHANGE, DEFAULT_D_CHANGE);
    }

    @Override
    public void run() {
        super.run();
        ticks = robot.getDriveEncoderValue();
        time = System.currentTimeMillis();
        pid.getNewSpeed((int)ticks, (int)ticks2, time, time2, DEFAULT_TARGET);
    }
    @Override
    public void stop(){
        super.stop();
        robot.driveStop();
    }

}

