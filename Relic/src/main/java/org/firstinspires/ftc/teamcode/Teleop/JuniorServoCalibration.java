/* Copyright (c) 2017 FIRST. All rights reserved.
 */

package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.HardwareJunior_V0;

/*
 */

@TeleOp(name = "Junior: Servo Calibration", group = "Junior")
//@Disabled

/**
 * Created by ECR FTC on 9/17/2017.
 **/

public class JuniorServoCalibration extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareJunior_V0 robot = new HardwareJunior_V0();
    float offsetServo[] = new float[6];

    boolean doneCalibrating = false;

    @Override
    public void runOpMode() {
        double changeSpeed = 0.01;
        double leftGrabPos = 0.5;
        double rightGrabPos = 0.5;
        double leftJewelPos = 0.5;
        double rightJewelPos = 0.5;

        double leftGlyphterArmPos = robot.leftRelease;
        double rightGlyphterArmPos = robot.rightRelease;

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
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                if (gamepad1.a) {
                    leftGrabPos = leftGrabPos + changeSpeed;
                }
                if (gamepad1.b) {
                    rightGrabPos = rightGrabPos + changeSpeed;
                }
                if (gamepad1.x) {
                    leftJewelPos = leftJewelPos + changeSpeed;
                }
                if (gamepad1.y) {
                    rightJewelPos = rightJewelPos + changeSpeed;
                }
            }

            if (gamepad1.dpad_down) {
                if (gamepad1.a) {
                    leftGrabPos = leftGrabPos - changeSpeed;
                }
                if (gamepad1.b) {
                    rightGrabPos = rightGrabPos - changeSpeed;
                }
                if (gamepad1.x) {
                    leftJewelPos = leftJewelPos - changeSpeed;
                }
                if (gamepad1.y) {
                    rightJewelPos = rightJewelPos - changeSpeed;
                }

            }

            leftGrabPos = Range.clip(leftGrabPos, 0, 1);
            rightGrabPos = Range.clip(rightGrabPos, 0, 1);
            leftJewelPos = Range.clip(leftJewelPos, 0, 1);
            rightJewelPos = Range.clip(rightJewelPos, 0, 1);

            robot.servoLeftGrab.setPosition(leftGrabPos);
            robot.servoRightGrab.setPosition(rightGrabPos);
            robot.servoLeftJewel.setPosition(leftJewelPos);
            robot.servoRightJewel.setPosition(rightJewelPos);

            telemetry.addData("servoLeftGrab", ": %f", leftGrabPos);
            telemetry.addData("servoRightGrab", ": %f", rightGrabPos);
            telemetry.addData("servoLeftJewel", ": %f", leftJewelPos);
            telemetry.addData("servoRightJewel", ": %f", rightJewelPos);

            telemetry.update();
            sleep(40);
        }
    }
}

