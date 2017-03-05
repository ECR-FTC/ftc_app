package org.firstinspires.ftc.teamcode.steprunner;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by ECR FTC on 1/12/2017.
 */
public class PID
{
    protected double minSpeed, maxSpeed, pChangeFactor, iChangeFactor, dChangeFactor;

    int ticks = 0, ticksTwo = 0;
    double time = 0, timeTwo = -50;
    double ticksPerTime;
    double speed = 1.0;
    double P = 0.5, I = 0;
    int timeInt = 50;
    double ticksPerTimeAvg;
    double targetTolerance = 0.05;

    public PID(double tMax, double tMin, double pChange, double iChange, double dChange)
    {
        this.minSpeed = tMin;
        this.maxSpeed = tMax;
    }
    public double getNewSpeed (int currentTicks, int currentTicks2, double currentTime, double currentTime2, double target)
    { //user needs: private ElapsedTime runtime = new ElapsedTime();

        // PID: see PIDTest.java for full details
        //get new time and ticks
        ticks    = currentTicks;
        ticksTwo = currentTicks2;
        time     = currentTime;
        timeTwo  = currentTime2;

        //get updated ticks per time
        // encoder ticks / milliseconds
        ticksPerTime = ((ticks - ticksTwo) / (time - timeTwo));
        // find 'p' value (used in 'I')
        P = target - ticksPerTime;

        // find 'i' value
        I = (I * (timeInt - (time - timeTwo)) + P * (time - timeTwo)) / timeInt;
        ticksPerTimeAvg = (ticksPerTimeAvg*(timeInt - (time - timeTwo)) + ticksPerTime*(time - timeTwo)) / timeInt;

        // update speed
        speed += (P*pChangeFactor) + (I*iChangeFactor);
        speed = max(speed, minSpeed);
        speed = min(speed, maxSpeed);

        return speed;
    }
}