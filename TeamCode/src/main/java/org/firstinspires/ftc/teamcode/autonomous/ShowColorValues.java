package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - Color test
 * Check values returned by color sensors.
 */

@Autonomous(name = "ShowColorValues", group = "StepTests")
public class ShowColorValues extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        MorganaBot robot = new MorganaBot();

        // Initialize the robot.
        try {
            robot.init(hardwareMap);
        } catch (InterruptedException e) {
            return;         // just exit if interrupted
        }

        // Show telemetry and wait for Start button to be pressed
        showMessage("Waiting for start");
        waitForStart();

        while (opModeIsActive()) {
            double red = robot.rightRedSensor.getVoltage();
            double blue = robot.rightBlueSensor.getVoltage();
            telemetry.addData("Colors", "Red=%.2f", red);
            telemetry.addData("Colors", "Blue=%.2f", blue);
            telemetry.update();
        }

    }

}
