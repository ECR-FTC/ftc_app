package org.firstinspires.ftc.teamcode.steprunner;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.HardwareBuild2;
import org.firstinspires.ftc.teamcode.HardwareK9botECR;

/**
 * Created by ECR FTC on 1/14/2017.
 */
public class RamperStep extends Step {
    double ticks = robot.encoderRight();

    Ramper r = new Ramper(10, 20, 100, 0, 1);

    double motorSpeed = r.getRampValue(ticks);

    public void start(Robot r) {
        robot.allMotorsOn(motorSpeed);
    }




}
