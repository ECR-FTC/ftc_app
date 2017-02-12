package org.firstinspires.ftc.teamcode.steprunner;

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Locale;

/**
 * Created by ECR FTC on 1/15/2017.
 */
public class TurnStep extends Step{
    static final double DEFAULT_TUP    = 45;
    static final double DEFAULT_TDOWN  = 45;
    static final double DEFAULT_MIN    =   0.15;

    protected double maxPower;
    protected double heading;
    protected double currentPos;
    protected double currentPower;

    protected Ramper ramper;

    public TurnStep(double maxPower, double heading)
    {
        this.maxPower = maxPower;
        this.heading = heading;
        this.currentPos = 0.00;
        this.currentPower = 0.00;
    }

    @Override
    public void start(Robot r) {
        super.start(r);

        ramper = new Ramper(DEFAULT_TUP, DEFAULT_TDOWN, Math.abs(heading), DEFAULT_MIN, maxPower);
        this.robot.bot.gyro.resetZAxisIntegrator();
    }

    @Override
    public void run() {
        super.run();
        currentPos = Math.abs(robot.bot.gyro.getIntegratedZValue());
        if (currentPos >= Math.abs(heading))
        {
            stop();
        }
        else {

            currentPower = ramper.getRampValue(currentPos);
            robot.motorLeftOn(currentPower*(Math.signum(heading)));
            robot.motorRightOn(currentPower*-(Math.signum(heading)));
        }
    }
    @Override
    public void stop(){
        super.stop();
        robot.allMotorsOff();
    }
    public String getTelemetry()
    {
        return String.format(Locale.US,"%.2f %.2f",(currentPos), currentPower );
    }
}
