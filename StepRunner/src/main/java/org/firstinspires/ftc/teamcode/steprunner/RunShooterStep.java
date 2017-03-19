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
    protected static final double SHOOTER_KP = 0.0002;
    protected static final double SHOOTER_KI = 0;       // KI NOT WORKING YET, LEAVE ZERO
    protected static final double SHOOTER_KD = 0;

    protected static final double MIN_POWER = 0.25;
    protected static final double MAX_POWER = 1.0;

    protected static final double TPS_TOLERANCE = 40;

    protected static long MIN_SAMPLE_INTERVAL = 250;        // sample at 4Hz
    protected static int READY_COUNT_NEEDED = 1;            // wnat to see 3 readings in range

    // Pass in how fast in ticks per second we want the shooter to run.
    public RunShooterStep(double tpsWanted) {
        this.tpsWanted = tpsWanted;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        lastTime = 0;
        lastEncoder = 0;
        currentPower = 0;
        pid = new PID(tpsWanted, SHOOTER_KP, SHOOTER_KI, SHOOTER_KD);
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
        double change = pid.getCV(dt, tps);             // PID computes change

        // Change the power
        currentPower += change;
        currentPower = Math.min(Math.max(currentPower, MIN_POWER), MAX_POWER);
        robot.setShootPower(currentPower);
        tell("tps=%.2f, power=%.2f, change=%.2f", tps, currentPower, change);

        // Remember time and encoder to compute delta next time
        lastTime = now;
        lastEncoder = encoderValue;

        // See if we're within tolerance to show shooter ready
        double diff = Math.abs(tps - tpsWanted);
        if (diff < TPS_TOLERANCE) {
            readyCount++;
            if (readyCount >= READY_COUNT_NEEDED) {
                setFlag("shooterReady", 1);
                tell("shooter ready");
            }
        } else {
            clearFlag("shooterReady");
            tell("shooter not ready");
            readyCount = 0;
        }
    }

    @Override
    public void stop() {
        super.stop();

    }

}
