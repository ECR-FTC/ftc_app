/* Copyright (c) 2017 FIRST. All rights reserved.
 */

package org.eastcobbrobotics.ftc.relic.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.eastcobbrobotics.ftc.relic.HardwareJunior_V0;

/*
 */

@TeleOp(name = "Junior: TTLV0", group = "Junior")
//@Disabled

/**
 * Created by ECR FTC on 9/17/2017.
 **/

public class JuniorTeleop extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareJunior_V0 robot = new HardwareJunior_V0();

    @Override
    public void runOpMode() {
        //variables that set positions/speeds of motors and servos
        double left;
        double right;
        double leftGlyphterArmPos = robot.leftRelease;
        double rightGlyphterArmPos = robot.rightRelease;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         * Init also inits servos to home value
         */
        try {
            robot.init(hardwareMap);
        } catch (InterruptedException e) {
            //todo handle this falure
        }


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            //change top speed
            if (gamepad1.dpad_up) {
                robot.topSpeed = robot.topSpeed + 0.01;
            }
            if (gamepad1.dpad_down) {
                robot.topSpeed = robot.topSpeed - 0.01;
            }
            robot.topSpeed = Range.clip(robot.topSpeed, 0, 1);

            // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
            left = -gamepad1.left_stick_y * robot.topSpeed;
            right = -gamepad1.right_stick_y * robot.topSpeed;

            robot.motorFL.setPower(left);
            robot.motorBL.setPower(left);
            robot.motorFR.setPower(right);
            robot.motorBR.setPower(right);

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

            //store jewel servos
            if (gamepad2.left_bumper || gamepad2.right_bumper)
            {
                // set the button pushing servos to the store positions
                robot.servoRightGrab.setPosition(robot.rightRelease);
                robot.servoLeftGrab.setPosition(robot.leftRelease);
                robot.servoRightJewel.setPosition(robot.rightJewelStore);
                robot.servoLeftJewel.setPosition(robot.leftJewelStore);
            }

            // Send telemetry message to signify robot running;
            telemetry.addData("left", "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.addData("left glyphter servo", "%.2f", robot.servoLeftGrab.getPosition());
            telemetry.addData("right glyphter servo", "%.2f", robot.servoRightGrab.getPosition());
            telemetry.addData("top speed", "%.2f", robot.topSpeed);
            telemetry.update();


            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
