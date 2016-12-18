package org.firstinspires.ftc.teamcode.steprunner;

import org.firstinspires.ftc.teamcode.HardwareK9botECR;

/**
 * Created by ECR FTC 11096 on 10/29/2016.
 *
 * This class abstracts the interface to the robot. Each step is given an
 * instance of this class, and uses methods on the class to do things on
 * the robot like turning motors on and off, checking sensors, etc.
 */


public class Robot {

    protected HardwareK9botECR bot;
    public Robot(HardwareK9botECR bot) {
        this.bot = bot;
    }
    public void motorOn()
    {
        bot.leftMotor.setPower(0.25);
    }
    public void motorOff()
    {
        bot.leftMotor.setPower(0.00);
    }
    public void allMotorsOn(double speed)
    {
        bot.leftMotor.setPower(speed);
        bot.rightMotor.setPower(speed);
    }
    public void allMotorsOff()
    {
        bot.leftMotor.setPower(0.00);
        bot.rightMotor.setPower(0.00);
    }    /*
        Return current time in milliseconds.
     */
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
