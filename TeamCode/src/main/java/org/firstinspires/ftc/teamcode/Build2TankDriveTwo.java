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
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.util.Locale;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;


//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */

@TeleOp(name = "Build2bot: Telop Tank", group = "Build1bot")
//@Disabled
public class Build2TankDriveTwo extends OpMode {
    HardwareBuild2_tele robot;
    double maxDSpeed = 1.0;
    boolean motorOn = false;
    private ElapsedTime runtime = new ElapsedTime();
    int ticks = 0, ticksTwo = 0;
    double time = 0, timeTwo = 0;
    double ticksPerTime;
    double target = 1.10, difference = 0;
    double speed = 1.0;
    double P = 0.5, I = 0;
    double pChangeFactor = 4, iChangeFactor = 1;
    double maxSpeed = 1.0, minSpeed = 0.2;
    int timeInt = 1000;

    /**
     * Constructor
     */
    public Build2TankDriveTwo() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {

        robot = new HardwareBuild2_tele();          // Use Build2's Hardware file
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
            maxDSpeed = maxDSpeed - robot.drivePowerIncrement;
        }
        if (gamepad1.right_bumper) // raise max speed
        {
            maxDSpeed = maxDSpeed + robot.drivePowerIncrement;
        }
        maxDSpeed = Math.max(maxDSpeed, robot.driveMinPower); // apply a min speed
        maxDSpeed = Math.min(maxDSpeed, robot.driveMaxPower); // apply a max speed

        //set motor speed for shoot
        if (gamepad2.dpad_up) // forward
        {
            motorOn = true;
            runtime.reset();
        }
        if (gamepad2.dpad_down && gamepad1.dpad_down) // backward
        {
            motorOn = false;
            robot.motorShoot.setPower(-0.5);
        }
        if (gamepad2.dpad_left || gamepad2.dpad_right) // off
        {
            robot.motorShoot.setPower(0.0);
            motorOn = false;
        }
        if(motorOn == true) {
            //get new time and ticks
         //   robot.motorShoot.setPower(speed);
            ticks = robot.motorShoot.getCurrentPosition();
            time = runtime.milliseconds();

            //get ticks per time
            ticksPerTime = ((ticks - ticksTwo) / (time - timeTwo));

            // find 'p'
            difference = target - ticksPerTime;

            // find 'i'
            I = (I * (timeInt - (time - timeTwo)) + difference * (time - timeTwo)) / timeInt;

            // find speed
            speed += (difference * pChangeFactor) + (I * iChangeFactor);
            speed = max(speed, minSpeed);
            speed = min(speed, maxSpeed);

            robot.motorShoot.setPower(speed);

            //save old time & ticks
            timeTwo = time;
            ticksTwo = ticks;
        }


        // extend & retract lift
        if (gamepad2.right_stick_y > robot.deadZone)
        {
            robot.motorLift.setPower(robot.liftMotorPower);
        }

        if (gamepad2.right_stick_y < robot.deadZone)
        {
            robot.motorLift.setPower(0.0);
        }

        // flip 'forklift'
        if(gamepad2.x)
        {
            robot.flipServo.setPosition(robot.flipGo);
        }
        else
        {
            robot.flipServo.setPosition(robot.flipStore);
        }


        // beacon pushers
        if (gamepad2.left_bumper || gamepad2.right_bumper) // trigger servo settings
		{
            robot.leftServo.setPosition(robot.leftPress);
            robot.rightServo.setPosition(robot.rightPress);
		}
		else
		{
            robot.leftServo.setPosition(robot.leftStore);
            robot.rightServo.setPosition(robot.rightStore);
		}

        // fire particles
        if (gamepad2.a)
        {
            robot.fireServo.setPosition(robot.fireGo);
        }
        else
        {
            robot.fireServo.setPosition(robot.fireStay);
        }

        // load particles
        if (gamepad2.y)
        {
            robot.loadServo.setPosition(robot.loadClosed);
        }
        if (gamepad2.b)
        {
            robot.loadServo.setPosition(robot.loadOpen);
        }


		// Send telemetry data back to driver station.
        telemetry.addData("Drive Power", "Drive: " + String.format(Locale.US,"%.2f", maxSpeed));
        telemetry.addData("Encoder Value:", robot.returnEncoderValue());
        telemetry.addData("Heading:", robot.gyro.getIntegratedZValue());
        telemetry.addData("speed", speed);
        telemetry.addData("ticks over time", ticksPerTime);
        telemetry.update();

    }

    @Override
    public void stop() {

    }

    // holonomic tank drive
    public void teleDrive(float left, float side, float right) {

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
        left = (float) scaleInput(-left); // "up" on joystick is negative
        right = (float) scaleInput(-right); // "up" on joystick is negative
        side = (float) -scaleInput(side);

        MotorPower returnMotorPower;
        returnMotorPower = motorScale(left + side, left - side, right - side, right + side, (float) maxSpeed);

        // write the values to the motors
        robot.motorBackRight.setPower(returnMotorPower.getMotorBackRight());
        robot.motorBackLeft.setPower(returnMotorPower.getMotorBackLeft());
        robot.motorFrontLeft.setPower(returnMotorPower.getMotorFrontLeft());
        robot.motorFrontRight.setPower(returnMotorPower.getMotorFrontRight());

    }

    // normalize the motor powers so that direction is preserved
    public MotorPower motorScale(float motorFL, float motorBL, float motorFR, float motorBR, float maxSpeed) {
        float norm;
        float motorFLadj = 0, motorBLadj = 0, motorFRadj = 0, motorBRadj = 0;

        // Normalize by the largest motor power.
        norm = max(max(abs(motorFL), abs(motorBL)), max(abs(motorFR), abs(motorBR)));
        norm = max(norm, (float) 1.0);

        motorFLadj = (Range.clip((motorFL) / norm, -(float)maxDSpeed, (float)maxDSpeed));
        motorBLadj = (Range.clip((motorBL) / norm, -(float)maxDSpeed, (float)maxDSpeed));
        motorFRadj = (Range.clip((motorFR) / norm, -(float)maxDSpeed, (float)maxDSpeed));
        motorBRadj = (Range.clip((motorBR) / norm, -(float)maxDSpeed, (float)maxDSpeed));

        MotorPower returnMotorPower = new MotorPower(motorBLadj, motorBRadj, motorFLadj, motorFRadj);
        return returnMotorPower;
    }

    double scaleInput(double dVal) {
        double[] scaleArray = {0.00, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
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

