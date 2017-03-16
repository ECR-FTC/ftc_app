package org.firstinspires.ftc.teamcode.steprunner;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by ECR FTC on 3/1/2017.
 */
public class PID
{
    protected double sp;    // desired setpoint
    protected double kP;    // proportional coefficient
    protected double kI;    // integral coefficient
    protected double kD;    // differential coefficient

    protected double errorSum;
    protected double lastError;

    public PID(double sp, double kP, double kI, double kD) {
        this.sp = sp;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        reset();
    }

    public void reset() {
        errorSum = 0;
        lastError = 0;
    }

    // Get the new control value given a time delta (dt) and the current process variable (pv).
    public double getCV(double dt, double pv) {

        double error = sp - pv;         // current error
        errorSum += error * dt;
        double dError = (error - lastError) / dt;
        double cv = (kP * error) + (kI * errorSum) + (kD * dError);
        return cv;

    }


}