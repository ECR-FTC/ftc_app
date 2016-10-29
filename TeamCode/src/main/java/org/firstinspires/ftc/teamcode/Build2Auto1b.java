/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static java.lang.Math.abs;
import static java.lang.Math.max;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Build2Auto1b", group="Linear Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class Build2Auto1b extends LinearOpMode {

        HardwareBuild2 robot;

        /* Declare OpMode members. */
        private ElapsedTime runtime = new ElapsedTime();
        // DcMotor leftMotor = null;
        // DcMotor rightMotor = null;

        @Override
        public void runOpMode() throws InterruptedException {
            telemetry.addData("Status", "Initialized");
            telemetry.update();

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */
            // leftMotor  = hardwareMap.dcMotor.get("left motor");
            // rightMotor = hardwareMap.dcMotor.get("right motor");

            // eg: Set the drive motor directions:
            // "Reverse" the motor that runs backwards when connected directly to the battery
            // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
            // rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

            // Wait for the game to start (driver presses PLAY)
            waitForStart();
            runtime.reset();

            // run until the end of the match (driver presses STOP)
            while (opModeIsActive()) {
                telemetry.addData("Status", "Run Time: " + runtime.toString());
                telemetry.update();

                autoDrive(1,0,0, 1000);

                robot.motorShoot.setPower(.35);

                // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
                // leftMotor.setPower(-gamepad1.left_stick_y);
                // rightMotor.setPower(-gamepad1.right_stick_y);

                idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
            }
        }
    public void autoDrive(float foward, float left, float turn, long waitFor){
        telemetry.addData("Say", "teledrive! 0");
        telemetry.update();

        float a, b, c;
        long  d;
//		float norm;
        // holonomic tank drive

        a = foward;
        b = left;
        c = turn;
        d = waitFor;

        MotorPower returnMotorPower;
        returnMotorPower = motorScale(a - b - c, a + b - c, a + b + c, a - b+ c, (float) 1.0);

        // clip the right/left values so that the values never exceed +/- 1
        // write the values to the motors
        robot.motorBackRight.setPower(returnMotorPower.getMotorBackLeft());// me have muches goods grammers yes
        robot.motorBackLeft.setPower(returnMotorPower.getMotorBackRight());//srrey mssi clarke
        robot.motorFrontLeft.setPower(returnMotorPower.getMotorFrontLeft());
        robot.motorFrontRight.setPower(returnMotorPower.getMotorFrontRight()); // note: reversal of BL and BR is purpassful
        runtime.reset();
        while ((runtime.seconds() < waitFor/1000.0)) {
            telemetry.update();
        }

    }

    public MotorPower motorScale(float motorFL, float motorBL, float motorFR, float motorBR, float maxSpeed) {
        float norm;
        float motorFLadj = 0, motorBLadj = 0, motorFRadj = 0, motorBRadj = 0;
        // Normalise by the largest motor power.
        norm = max(max(abs(motorFL), abs(motorBL)), max(abs(motorFR), abs(motorBR)));
        norm = max(norm, (float) 1.0);

        motorFLadj = (Range.clip((motorFL) / norm, -maxSpeed, maxSpeed));
        motorBLadj = (Range.clip((motorBL) / norm, -maxSpeed, maxSpeed));
        motorFRadj = (Range.clip((motorFR) / norm, -maxSpeed, maxSpeed));
        motorBRadj = (Range.clip((motorBR) / norm, -maxSpeed, maxSpeed));
        MotorPower returnMotorPower = new MotorPower(motorBLadj, motorBRadj, motorFLadj, motorFRadj);
        return returnMotorPower;
    }

}

