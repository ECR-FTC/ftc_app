package org.eastcobbrobotics.ftc.relic.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by ECR FTC on 12/11/17.
 */

@TeleOp(name = "Junior: Motor Glyphter", group = "Junior")

public class JuniorTeleopMotorGlyphter extends JuniorTeleop {

    @Override
    protected void handleGlyphter() {

        //lift/retract glyphter
        robot.motorGlyphter.setPower(gamepad2.right_stick_y * robot.glyphterSpeed);

        double power;
        if (gamepad2.y) {
            power = robot.maxGrabSpeed;
        }
        else if (gamepad2.a) {
            power = -robot.maxGrabSpeed;
        }
        else if (gamepad2.x) {
            power = robot.holdSpeed;
        }
        else {
            power = 0;
        }

        robot.motorLeftGrab.setPower(power);
        robot.motorRightGrab.setPower(power);

        telemetry.addData("glyphter motor power", "%.2f", power);

    }
}
