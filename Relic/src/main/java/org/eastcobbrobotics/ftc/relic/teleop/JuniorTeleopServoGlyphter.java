package org.eastcobbrobotics.ftc.relic.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by ECR FTC on 12/11/17.
 */

@TeleOp(name = "Junior: Servo Glyphter", group = "Junior")

public class JuniorTeleopServoGlyphter extends JuniorTeleop {

    double leftGlyphterArmPos;
    double rightGlyphterArmPos;

    public void runOpMode() {
        leftGlyphterArmPos = robot.leftRelease;
        rightGlyphterArmPos = robot.rightRelease;
        super.runOpMode();
    }

    @Override
    protected void handleGlyphter() {

        //lift/retract glyphter
        robot.motorGlyphter.setPower(gamepad2.right_stick_y * robot.glyphterSpeed);

        //version 2 of grab controls, it is operated like the k-9 controls
        //change the position of the glyphter servo by glyphterChangeSpeed
        if (gamepad2.y) // open
        {
            leftGlyphterArmPos = leftGlyphterArmPos - robot.glyphterChangeSpeed;
            rightGlyphterArmPos = rightGlyphterArmPos + robot.glyphterChangeSpeed;
        }
        if (gamepad2.b) // close
        {
            leftGlyphterArmPos = leftGlyphterArmPos + robot.glyphterChangeSpeed;
            rightGlyphterArmPos = rightGlyphterArmPos - robot.glyphterChangeSpeed;
        }
        //make sure the servos don't go too far
        leftGlyphterArmPos = Range.clip(leftGlyphterArmPos, robot.leftGrab, robot.leftRelease);
        rightGlyphterArmPos = Range.clip(rightGlyphterArmPos, robot.rightRelease, robot.rightGrab);

        //set the position
        robot.servoLeftGrab.setPosition(leftGlyphterArmPos);
        robot.servoRightGrab.setPosition(rightGlyphterArmPos);

        // Send telemetry message to signify robot running;
        telemetry.addData("left glyphter servo", "%.2f", robot.servoLeftGrab.getPosition());
        telemetry.addData("right glyphter servo", "%.2f", robot.servoRightGrab.getPosition());

    }
}
