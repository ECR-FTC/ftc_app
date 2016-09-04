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

package org.firstinspires.ftc.team11096code;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.Locale;
import static java.lang.Math.*;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class Build1TankDrive extends OpMode {
	HardwareBuild1   robot        = new HardwareBuild1();          // Use Bild1's Hardware file

	/**
	 * Constructor
	 */
	public Build1TankDrive() {

	}

	/*
	 * Code to run when the op mode is first enabled goes here
	 *
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
	 */
	@Override
	public void init() {
		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot.
		 *
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */

	}

	/*
	 * This method will be called repeatedly in a loop
	 *
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
	 */
	@Override
	public void loop() {
		float a,b,c;
		float norm;
		// holonomic tank drive

		a = gamepad1.left_stick_y;
		b = gamepad1.right_stick_y;
		c = gamepad1.right_stick_x;

		// scale the joystick value to make it easier to control
		// the robot more precisely at slower speeds.
		a = (float)scaleInput(a);
		b = (float)scaleInput(b);
		c = (float)scaleInput(c);

		// Normalise by the largest motor power.
		norm = max(max(abs(b+c),abs(a-c)),max(abs(b-c),abs(a+c)));
		norm = max(norm,(float)1.0);

		// clip the right/left values so that the values never exceed +/- 1
		// write the values to the motors
		robot.motorFrontRight.setPower(Range.clip((b+c)/norm, -1, 1));
		robot.motorFrontLeft.setPower(Range.clip((a-c)/norm, -1, 1));
		robot.motorBackRight.setPower(Range.clip((b-c)/norm, -1, 1));
		robot.motorBackLeft.setPower(Range.clip((a+c)/norm, -1, 1));


		if (gamepad1.a) // motor for slide settings
		{
			robot.motorSlide.setPower(.5);
		}
		if (gamepad1.x)
		{
			robot.motorSlide.setPower(-.5);
		}

		if (gamepad1.left_bumper) // trigger servo settings
		{
			robot.leftServo.setPosition(0.0);
		}
		else
		{
			robot.leftServo.setPosition(0.5);
		}
		if (gamepad1.right_bumper)
		{
			robot.rightServo.setPosition(0.9);
		}
		else
		{
			robot.rightServo.setPosition(0.3);
		}

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */

		telemetry.addData("Text", "*** Robot Data***");
		telemetry.addData("slide power", "pwr: " + String.format(Locale.US,"%.2f", robot.motorSlide.getPower()));
		telemetry.addData("right front power", "pwr: " + String.format(Locale.US,"%.2f", robot.motorFrontRight.getPower()));
		telemetry.addData("left front power", "pwr: " + String.format(Locale.US,"%.2f", robot.motorFrontLeft.getPower()));
		telemetry.addData("right back power", "pwr: " + String.format(Locale.US,"%.2f", robot.motorBackRight.getPower()));
		telemetry.addData("left back power", "pwr: " + String.format(Locale.US,"%.2f", robot.motorBackLeft.getPower()));
	}

	/*
	 * Code to run when the op mode is first disabled goes here
	 *
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
	 */
	@Override
	public void stop() {

	}

	/*
	 * This method scales the joystick input so for low joystick values, the
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
	double scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };
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

