/* Copyright (c) 2017 FIRST. All rights reserved.
 */

package org.eastcobbrobotics.ftc.relic.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.eastcobbrobotics.ftc.relic.HardwareJunior_V0;

/*
 */

@TeleOp(name = "MotorTest", group = "Junior")
//@Disabled

/**
 * Created by ECR FTC on 9/17/2017.
 **/

public class MotorTest extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareJunior_V0 robot = new HardwareJunior_V0();

    @Override
    public void runOpMode() {

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

            // Run wheels at 0.8 (note: The joystick goes negative when pushed forwards, so negate it)
            robot.motorFL.setPower(0.8);
            robot.motorBL.setPower(0.8);
            robot.motorFR.setPower(0.8);
            robot.motorBR.setPower(0.8);

            // Send telemetry message to signify robot running;
            telemetry.addData("Fl", "%.2f", robot.motorFL.getCurrentPosition());
            telemetry.addData("BL", "%.2f", robot.motorBL.getCurrentPosition());
            telemetry.addData("FR", "%.2f", robot.motorFR.getCurrentPosition());
            telemetry.addData("BR", "%.2f", robot.motorBR.getCurrentPosition());
            telemetry.update();


            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
