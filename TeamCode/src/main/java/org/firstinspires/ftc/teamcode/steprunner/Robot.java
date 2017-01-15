package org.firstinspires.ftc.teamcode.steprunner;

import com.qualcomm.robotcore.hardware.DcMotor;

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
    public void motorLeftOn(double speed)
    {
        bot.leftMotor.setPower(speed);
    }
    public void motorLeftOff()
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
    }

    public void motorRightOn(double speed)
    {
        bot.leftMotor.setPower(speed);
    }

    public void motorRightOff()
    {
        bot.leftMotor.setPower(0.00);
    }

    public int encoderRight()
    {
        //need to reset encoder
        int rightTicks = bot.rightMotor.getCurrentPosition();
        return rightTicks;
    }

    public int encoderLeft()
    {
        //need to reset encoder
        int leftTicks = bot.leftMotor.getCurrentPosition();
        return leftTicks;
    }
    /*
        Return current time in milliseconds.
     */
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
