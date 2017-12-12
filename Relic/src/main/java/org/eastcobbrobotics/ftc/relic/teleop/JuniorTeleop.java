/* Copyright (c) 2017 FIRST. All rights reserved.
 */

package org.eastcobbrobotics.ftc.relic.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.eastcobbrobotics.ftc.relic.HardwareJunior_V0;

/**
 * Created by ECR FTC on 9/17/2017.
 * <p>
 * This base class does NOT have working methods for the glyphter, so
 * it is an abstract base class. Extend this class and implement glyhpter
 * methods to get a working teleop.
 **/

public abstract class JuniorTeleop extends LinearOpMode {

    HardwareJunior_V0 robot = new HardwareJunior_V0();

    @Override
    public void runOpMode() {
        //variables that set positions/speeds of motors
        double left;
        double right;

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
        telemetry.addData("Say", "Hello Driver");
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

            telemetry.addData("left", "%.2f", left);
            telemetry.addData("right", "%.2f", right);
            telemetry.addData("top speed", "%.2f", robot.topSpeed);

            // Call child class overrides to handle the glyphter.
            handleGlyphter();

            //store jewel servos
            if (gamepad2.left_bumper || gamepad2.right_bumper) {
                // set the button pushing servos to the store positions
                robot.servoRightGrab.setPosition(robot.rightRelease);
                robot.servoLeftGrab.setPosition(robot.leftRelease);
                robot.servoRightJewel.setPosition(robot.rightJewelStore);
                robot.servoLeftJewel.setPosition(robot.leftJewelStore);
            }

            telemetry.update();

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }

    // Subclasses must override this method to implement glyphter control.

    abstract protected void handleGlyphter();


}
