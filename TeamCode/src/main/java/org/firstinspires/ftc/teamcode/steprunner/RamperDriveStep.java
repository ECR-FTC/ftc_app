package org.firstinspires.ftc.teamcode.steprunner;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.Locale;



import org.firstinspires.ftc.teamcode.steprunner.Robot;

/**
 * Created by ECR FTC on 1/15/2017.
 */
public class RamperDriveStep extends Step{
    static final double DEFAULT_TUP    = 500;
    static final double DEFAULT_TDOWN  = 500;
    static final double DEFAULT_MIN    =   0.15;

    protected double maxPower;
    protected double distance;

    protected Ramper ramper;

    public RamperDriveStep(double maxPower, double distance)
    {
        this.maxPower = maxPower;
        this.distance = distance;

    }

    @Override
    public void start(Robot r) {
        super.start(r);
        this.robot.bot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ramper = new Ramper(DEFAULT_TUP, DEFAULT_TDOWN, distance, DEFAULT_MIN, maxPower);
    }

    @Override
    public void run() {
        super.run();
        if (robot.encoderRight() >= distance) {
            stop();
        }
        else {
            double pos = robot.encoderRight();
            double power = ramper.getRampValue(pos);
            telemetry.addData("Power", String.format(Locale.US,"%d %.2f",i, v ));
            telemetry.update();
            robot.allMotorsOn(power);
        }
    }
    @Override
    public void stop(){
        super.stop();
        robot.allMotorsOff();
    }
}

