/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

// needs updating to the new ftc app
// CEV 8/21/2016

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.MotorPower;

import java.util.Locale;

import static java.lang.Math.abs;
import static java.lang.Math.max;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */

@TeleOp(name = "Build2bot: Telop Tank. settings: John", group = "Build1bot")
//@Disabled
public class Build2TankDrive extends OpMode {
    HardwareBuild2 robot;
    double maxSpeed = 0.5;
    /**
     * Constructor
     */
    public Build2TankDrive() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {

        robot = new HardwareBuild2();          // Use Build2's Hardware file
        try {
            robot.init(hardwareMap);
        } catch (InterruptedException e) {
        }

		/*
		 */

    }

    @Override
    public void loop() {
        // set the drive motor speeds
        teleDrive(gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y);

        // let the driver adjust the drive motor max speed
        if (gamepad1.left_bumper) // lower max speed
        {
            maxSpeed = maxSpeed - robot.drivePowerIncrement;
        }
        if (gamepad1.right_bumper) // raise max speed
        {
            maxSpeed = maxSpeed + robot.drivePowerIncrement;
        }
        maxSpeed = Math.max(maxSpeed, robot.driveMinPower); // apply a min speed
        maxSpeed = Math.min(maxSpeed, robot.driveMaxPower); // apply a max speed

        if (gamepad2.dpad_down) // turn off the launch motor
        {
            robot.motorShoot.setPower(0.0);
        }
        if (gamepad2.dpad_up) // turn on the launch motor
        {
            robot.motorShoot.setPower(robot.shootPower);
        }
		if (gamepad2.left_bumper) // trigger servo settings
		{
			robot.leftServo.setPosition(robot.leftPress);
		}
		else
		{
			robot.leftServo.setPosition(robot.leftStore);
		}
		if (gamepad2.right_bumper)
		{
			robot.rightServo.setPosition(robot.rightPress);
		}
		else
		{
			robot.rightServo.setPosition(robot.rightStore);
		}
        if (gamepad2.a)
        {
            robot.fireServo.setPosition(robot.fireGo);
        }
        else
        {
            robot.fireServo.setPosition(robot.fireStay);
        }
        if (gamepad2.y)
        {
            robot.loadServo.setPosition(robot.loadClosed);
        }
        if (gamepad2.b)
        {
            robot.loadServo.setPosition(robot.loadOpen);
        }
		/*
		 * Send telemetry data back to driver station.
		 */

        	telemetry.addData("Text", "*** Robot Data***");
//	//	telemetry.addData("slide power", "pwr: " + String.format(Locale.US,"%.2f", robot.motorSlide.getPower()));
        //	telemetry.addData("right front power", "pwr: " + String.format(Locale.US,"%.2f", robot.motorFrontRight.getPower()));
        //	telemetry.addData("left front power", "pwr: " + String.format(Locale.US,"%.2f", robot.motorFrontLeft.getPower()));
        //	telemetry.addData("right back power", "pwr: " + String.format(Locale.US,"%.2f", robot.motorBackRight.getPower()));
        //	telemetry.addData("left back power", "pwr: " + String.format(Locale.US,"%.2f", robot.motorBackLeft.getPower()));
        telemetry.update();

    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }

    // holonomic tank drive
    public void teleDrive(float left, float side, float right) {
        telemetry.addData("Say", "teledrive! 0");
        telemetry.update();

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
        left = (float) scaleInput(-left); // "up" on joystick is negative
        right = (float) scaleInput(-right); // "up" on joystick is negative
        side = (float) scaleInput(side);

        MotorPower returnMotorPower;
        returnMotorPower = motorScale(left + side, left - side, right - side, right + side, (float) maxSpeed);

        // write the values to the motors
        robot.motorBackRight.setPower(returnMotorPower.getMotorBackRight());
        robot.motorBackLeft.setPower(returnMotorPower.getMotorBackLeft());
        robot.motorFrontLeft.setPower(returnMotorPower.getMotorFrontLeft());
        robot.motorFrontRight.setPower(returnMotorPower.getMotorFrontRight());

        //telemetry.addData("Say", "Motor power set");
        //telemetry.update();

    }

    // normalize the motor powers so that direction is preserved
    public MotorPower motorScale(float motorFL, float motorBL, float motorFR, float motorBR, float maxSpeed) {
        float norm;
        float motorFLadj = 0, motorBLadj = 0, motorFRadj = 0, motorBRadj = 0;
        // Normalize by the largest motor power.
        norm = max(max(abs(motorFL), abs(motorBL)), max(abs(motorFR), abs(motorBR)));
        norm = max(norm, (float) 1.0);

        motorFLadj = (Range.clip((motorFL) / norm, -maxSpeed, maxSpeed));
        motorBLadj = (Range.clip((motorBL) / norm, -maxSpeed, maxSpeed));
        motorFRadj = (Range.clip((motorFR) / norm, -maxSpeed, maxSpeed));
        motorBRadj = (Range.clip((motorBR) / norm, -maxSpeed, maxSpeed));

        MotorPower returnMotorPower = new MotorPower(motorBLadj, motorBRadj, motorFLadj, motorFRadj);
        return returnMotorPower;
    }

    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};
        double dScale;

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.

        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}

