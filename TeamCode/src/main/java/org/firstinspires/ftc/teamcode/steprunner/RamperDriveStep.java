package org.firstinspires.ftc.teamcode.steprunner;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.Locale;



import org.firstinspires.ftc.teamcode.steprunner.Robot;

/**
 * Created by ECR FTC on 1/15/2017.
 */
public class RamperDriveStep extends Step{
    static final double DEFAULT_TUP    = 4000;
    static final double DEFAULT_TDOWN  = 4000;
    static final double DEFAULT_MIN    =   0.15;

    protected double maxPower;
    protected double distance;
    protected double currentPos;
    protected double currentPower;

    protected Ramper ramper;

    public RamperDriveStep(double maxPower, double distance)
    {
        this.maxPower = maxPower;
        this.distance = distance;
        this.currentPos = 0.00;
        this.currentPower = 0.00;
    }

    @Override
    public void start(Robot r) {
        super.start(r);
        this.robot.bot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.robot.bot.rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.robot.bot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.robot.bot.leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        ramper = new Ramper(DEFAULT_TUP, DEFAULT_TDOWN, distance, DEFAULT_MIN, maxPower);
    }

    @Override
    public void run() {
        super.run();
        if (Math.abs(robot.encoderRight()) >= distance) {
            stop();
        }
        else {
            currentPos = Math.abs(robot.encoderRight());
            currentPower = ramper.getRampValue(currentPos);
            robot.allMotorsOn(currentPower);
        }
    }
    @Override
    public void stop(){
        super.stop();
        robot.allMotorsOff();
    }
    public String getTelemetry(){

        return String.format(Locale.US,"%.2f %.2f",(currentPos), currentPower );
    }
}

