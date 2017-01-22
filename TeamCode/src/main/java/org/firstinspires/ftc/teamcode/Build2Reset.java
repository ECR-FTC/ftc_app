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

@TeleOp(name = "Build2bot: Reset", group = "Build1bot")
//@Disabled
public class Build2Reset extends OpMode {
    HardwareBuild2_tele robot;

    /**
     * Constructor
     */
    public Build2Reset() {

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

    }

    @Override
    public void loop() {
        // retract lift
        if (gamepad2.right_stick_y > robot.deadZone)
        {
            robot.motorLift.setPower(-robot.liftMotorPower);
        }
        if (gamepad2.right_stick_y < robot.deadZone)
        {
            robot.motorLift.setPower(0.0);
        }

        //reset servos to closed
        //close flip servo
        robot.flipServo.setPosition(robot.flipStore);
        //clamp release servo
        robot.releaseServo.setPosition(robot.releaseClosed);
        //close beacon pushers
        robot.leftServo.setPosition(robot.leftStore);
        robot.rightServo.setPosition(robot.rightStore);
        //retract fire servo
        robot.fireServo.setPosition(robot.fireStay);
        //close scoop
        robot.loadServo.setPosition(robot.loadClosed);
    }

    @Override
    public void stop() {

    }

}

