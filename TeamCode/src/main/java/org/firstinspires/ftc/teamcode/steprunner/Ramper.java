package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC on 1/12/2017.
 */
public class Ramper
{
    protected double tUp, tDown, tDuration, tMin, tMax;
    public Ramper(double tUp, double tDown, double tDuration, double tMin, double tMax)
    {
        this.tUp = tUp;
        this.tDown = tDown;
        this.tDuration = tDuration;
        this.tMax = tMax;
        this.tMin = tMin;
    }
    public double getRampValue (double t)
    {
        double rampUpSpeed, rampDownSpeed, value;

        // speed is proportional to number of encoder steps away from target
        rampDownSpeed = 1 / tDown * (tDuration - t);
        // don't go faster than 1
        rampDownSpeed = Math.signum(rampDownSpeed) * Math.min(Math.abs(rampDownSpeed), 1);
        // don't go slower than 0
        rampDownSpeed = Math.signum(rampDownSpeed) * Math.max(Math.abs(rampDownSpeed), 0);

        // speed is proportional to number of encoder steps away from 0
        rampUpSpeed = 1 / tUp * (t);
        // don't go faster than 1
        rampUpSpeed = Math.signum(rampUpSpeed) * Math.min(Math.abs(rampUpSpeed), 1);
        // don't go slower than 0
        rampUpSpeed = Math.signum(rampUpSpeed) * Math.max(Math.abs(rampUpSpeed), 0);

        // real speed
        value = rampDownSpeed * rampUpSpeed;


        return value;
    }

}
/*
            double rampUpSpeed, rampDownSpeed, speedMotor;

            // speed is proportional to number of encoder steps away from target
            rampDownSpeed = 0.5 / tDown * (tDuration - t);
            // don't go faster than 1
            rampDownSpeed = Math.signum(rampDownSpeed) * Math.min(Math.abs(rampDownSpeed), 1);
            // don't go slower than 0
            rampDownSpeed = Math.signum(rampDownSpeed) * Math.max(Math.abs(rampDownSpeed), 0);

            // speed is proportional to number of encoder steps away from 0
            rampUpSpeed = 0.5 / tUp * (t);
            // don't go faster than 1
            rampUpSpeed = Math.signum(rampUpSpeed) * Math.min(Math.abs(rampUpSpeed), 1);
            // don't go slower than 0
            rampUpSpeed = Math.signum(rampUpSpeed) * Math.max(Math.abs(rampUpSpeed), 0);

            // real speed
            speedMotor = rampDownSpeed * rampUpSpeed;


            // don't go faster than SPEED_NOMINAL
            speedMotor = Math.signum(speedMotor) * Math.min(Math.abs(speedMotor), 0.50);
            // don't go slower than SPEED_MIN
            speedMotor = Math.signum(speedMotor) * Math.max(Math.abs(speedMotor), 0.15);


 */