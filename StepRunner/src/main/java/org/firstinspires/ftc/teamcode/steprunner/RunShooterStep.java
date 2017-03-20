package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 03/05/2017.
 */
public class RunShooterStep extends Step {

    protected double tpsWanted = 0.0;
    protected double currentPower;
    protected PID pid;
    protected long lastTime;
    protected double lastEncoder;
    protected int readyCount;

    // TODO: MOVE TUNING PARAMS TO MORGANABOT?
    // protected static final double SHOOTER_KP = 0.0002;
    protected static final double SHOOTER_KP = 0;
    protected static final double SHOOTER_KI = 0;
    protected static final double SHOOTER_KD = 0;

    protected static final double MIN_POWER = 0.25;
    protected static final double MAX_POWER = 1.0;

    protected static final double TPS_TOLERANCE = 40;

    protected static long MIN_SAMPLE_INTERVAL = 250;        // sample at 4Hz
    protected static int READY_COUNT_NEEDED = 3;            // want to see 3 readings in range
    protected static int MAX_READY_COUNT = 6;               // but don't count more than this

    protected static boolean useInternalPID = true;

    // Pass in how fast in ticks per second we want the shooter to run.
    public RunShooterStep(double tpsWanted) {
        this.tpsWanted = tpsWanted;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        lastTime = 0;
        lastEncoder = 0;

        robot.useInternalShooterPID(useInternalPID);
        if (useInternalPID) {
            currentPower = tpsWanted / 1000;        // 1000 is max shooter power!!
            robot.setShootPower(currentPower);
            pid = null;
        } else {
            currentPower = 0;
            pid = new PID(tpsWanted, SHOOTER_KP, SHOOTER_KI, SHOOTER_KD);
        }

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
            readyCount = 0;
            return;
        }

        // Has it been long enough to check in with the PID?
        double dt = now - lastTime;
        if (dt < MIN_SAMPLE_INTERVAL) {
            return;                                     // nope
        }

        double ticks = encoderValue - lastEncoder;      // here's how far we've gone
        double tps = ticks / dt * 1000;                 // dt is in milliseconds
        double change = 0;

        // Remember time and encoder to compute delta next time
        lastTime = now;
        lastEncoder = encoderValue;

        // If we're using our own PID give it a chance to compute a change.
        if (pid != null) {
            change = pid.getCV(dt, tps);
            currentPower += change;
            currentPower = Math.min(Math.max(currentPower, MIN_POWER), MAX_POWER);
            robot.setShootPower(currentPower);
        }

        // See if we're within tolerance to show shooter ready.
        double diff = Math.abs(tps - tpsWanted);
        if (diff < TPS_TOLERANCE) {
            readyCount = Math.min(MAX_READY_COUNT, readyCount + 1);
        } else {
            readyCount = Math.max(0, readyCount - 1);
        }

        tell("tps=%.2f, pwr=%.2f, chg=%.2f, rc=%d", tps, currentPower, change, readyCount);

        if (readyCount >= READY_COUNT_NEEDED) {
            setFlag("shooterReady", 1);
            tell("shooter ready");
        } else {
            clearFlag("shooterReady");
            tell("shooter not ready");
        }
    }

    @Override
    public void stop() {
        super.stop();

    }

}
