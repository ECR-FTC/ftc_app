package org.firstinspires.ftc.teamcode.steprunner;


/**
 * Created by ECR FTC on 1/15/2017.
 *
 * This step tries to maintain a target speed on the shooter using a PID controller.
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


    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.setShootPower(0);
        pid = new PID(DEFAULT_TMAX, DEFAULT_TMIN, DEFAULT_P_CHANGE, DEFAULT_I_CHANGE, DEFAULT_D_CHANGE);
    }

    @Override
    public void run() {
        // This defines the ticks and time for our getNewSpeed method in our PID class to allow PID to run
        super.run();
        ticks = robot.getDriveEncoderValue();
        time = System.currentTimeMillis();
        double power = pid.getNewSpeed(ticks, ticks2, time, time2, DEFAULT_TARGET);
        robot.setShootPower(power);

    }
    @Override
    public void stop(){
        super.stop();
    }

}

