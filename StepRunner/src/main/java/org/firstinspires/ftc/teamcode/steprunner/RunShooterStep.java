package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 03/05/2017.
 */
public class RunShooterStep extends Step {

    protected double tpsWanted = 0.0;
    protected double forcePower;
    protected PID pid;
    protected long lastTime;
    protected double lastEncoder;

    // MOVE THESE ELSEWHERE
    protected static final double KP = 0.2;
    protected static final double KI = 0;
    protected static final double KD = 0;

    protected static final double MIN_POWER = 0.25;
    protected static final double MAX_POWER = 1.0;

    protected static long MIN_SAMPLE_INTERVAL = 250;        // sample at 4Hz

    // Pass in how fast in ticks per second we want the shooter to run.
    public RunShooterStep(double tpsWanted, double forcePower) {
        this.tpsWanted = tpsWanted;
        this.forcePower = forcePower;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        lastTime = 0;
        lastEncoder = 0;
        pid = new PID(tpsWanted, KP, KI, KD, MIN_POWER, MAX_POWER);
    }

    @Override
    public void run() {
        super.run();

        long now = System.currentTimeMillis();
        double encoderValue = robot.getShooterEncoderValue();

        // If we haven't read anything before don't do anything now; otherwise it
        // might be time to get a new cv.

        if (lastTime == 0) {
            lastTime = now;
            lastEncoder = encoderValue;
        } else {
            double dt = now - lastTime;
            if (dt >= MIN_SAMPLE_INTERVAL) {
                double ticks = encoderValue - lastEncoder;
                double tps = ticks / dt * 1000;                 // dt is in milliseconds
                double power = pid.getCV(dt, tps);

                // FOR NOW FORCE POWER TO 0.5 to see what TPS is...
                // robot.setShootPower(forcePower);
                tell("tps=%.2f, force power=%.2f, pid power=%.2f", tps, forcePower, power);
                lastTime = now;
                lastEncoder = encoderValue;

                // TODO: set/clear shooterReady flag if tps within tolerance
            }
        }
    }

    @Override
    public void stop() {
        super.stop();

    }

    ;

}
