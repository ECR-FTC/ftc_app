package org.firstinspires.ftc.teamcode;

/**
 * Created by ECR FTC on 10/16/2016.
 * this object contains the motor power set points for a four-motor robot
 */
public class MotorPower {

    float motorFrontLeft;
    float motorFrontRight;
    float motorBackLeft;
    float motorBackRight;


    public void setMotorFrontLeft(float value)
    {
        this.motorFrontLeft = value;
    }
    public void setMotorFrontRight(float value)
    {
        this.motorFrontRight = value;
    }
    public void setMotorBackLeft(float value)
    {
        this.motorBackLeft = value;
    }
    public void setMotorBackRight(float value)
    {
        this.motorBackRight = value;
    }
    public float getMotorFrontLeft()
    {
        return motorFrontLeft;
    }
    public float getMotorFrontRight()
    {
        return motorFrontRight;
    }
    public float getMotorBackLeft()
    {
        return motorBackLeft;
    }
    public float getMotorBackRight()
    {
        return motorBackRight;
    }
    private void init ()
    {
        this.setMotorBackLeft(0);
        this.setMotorBackRight(0);
        this.setMotorFrontLeft(0);
        this.setMotorFrontRight(0);
    }
    public void setMotorValues (float motorBackLeft, float motorBackRight, float motorFrontLeft, float motorFrontRight)
    {
        this.setMotorBackLeft(motorBackLeft);
        this.setMotorBackRight(motorBackRight);
        this.setMotorFrontLeft(motorFrontLeft);
        this.setMotorFrontRight(motorFrontRight);
    }
    public MotorPower()
    {
        init();
    }
    public MotorPower(float motorBackLeft, float motorBackRight, float motorFrontLeft, float motorFrontRight)
    {
        this.setMotorValues(motorBackLeft, motorBackRight, motorFrontLeft, motorFrontRight);
    }
}

