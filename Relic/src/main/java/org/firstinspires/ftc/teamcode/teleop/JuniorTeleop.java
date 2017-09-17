/* Copyright (c) 2017 FIRST. All rights reserved.
 */

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.HardwareJunior_V0;

/*
 */

@TeleOp(name = "Junior: TTLV0", group = "Junior")
//@Disabled

/**
 * Created by ECR FTC on 9/17/2017.
 */

public class JuniorTeleop extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareJunior_V0 robot = new HardwareJunior_V0();              // Use a K9'shardware

    @Override
    public void runOpMode() {
        double left;
        double right;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
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

            //grab/release glyphs
            if (gamepad2.y)
            {
                robot.servoRightGrab.setPosition(robot.rightGrab);
                robot.servoLeftGrab.setPosition(robot.leftGrab);
            }
            else if(gamepad2.b)
            {
                robot.servoRightGrab.setPosition(robot.rightRelease);
                robot.servoLeftGrab.setPosition(robot.leftRelease);
            }


            // Send telemetry message to signify robot running;
            telemetry.addData("left", "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.addData("top speed", "%.2f", robot.topSpeed);
            telemetry.update();


            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
