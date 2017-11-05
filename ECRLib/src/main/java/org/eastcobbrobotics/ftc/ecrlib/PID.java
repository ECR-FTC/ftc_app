package org.eastcobbrobotics.ftc.ecrlib;

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

    // We track error over only a certain window.
    protected static final int WINDOW_SIZE = 3;
    protected double[] errors;
    protected int errorIndex;

    public PID(double sp, double kP, double kI, double kD) {
        this.sp = sp;
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        errors = new double[WINDOW_SIZE];
        reset();
    }

    public void reset() {
        errorSum = 0;
        lastError = 0;
        for (int i = 0 ; i < errors.length ; i++) {
            errors[i] = 0;
        }
        errorIndex = 0;
    }

    // Get the new control value given a time delta (dt) and the current process variable (pv).
    public double getCV(double dt, double pv) {

        double error = sp - pv;         // current error

        // Save the current error term and add up all the ones we have in our window.
        errors[errorIndex++] = error * dt;
        if (errorIndex >= WINDOW_SIZE) {
            errorIndex = 0;
        }
        errorSum = 0;
        for (double err: errors) {
            errorSum += err;
        }

        double dError = (error - lastError) / dt;
        double cv = (kP * error) + (kI * errorSum) + (kD * dError);
        return cv;

    }

    public double getErrorSum() {
        return errorSum;
    }

}