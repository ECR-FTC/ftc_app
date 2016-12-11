package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 *
 * This class abstracts the interface to the robot. Each step is given an
 * instance of this class, and uses methods on the class to do things on
 * the robot like turning motors on and off, checking sensors, etc.
 */


public class Robot {

    /*
        Return current time in milliseconds.
     */
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
