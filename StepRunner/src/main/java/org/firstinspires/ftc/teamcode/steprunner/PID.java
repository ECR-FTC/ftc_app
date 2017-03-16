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

    protected double minVal;
    protected double maxVal;    // bounds on the control variable

    protected double errorSum;
    protected double lastError;

    public PID(double sp, double kP, double kI, double kD, double minVal, double maxVal) {
        this.sp = sp;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.minVal = minVal;
        this.maxVal = maxVal;
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
        cv = Math.min(Math.max(cv, minVal), maxVal);
        return cv;

    }


}