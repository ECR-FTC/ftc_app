/*
    ECR FTC 11096 experimentation with basic autonomous

*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareK9bot;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;

/**
 We're trying to get something that does nothing working.
 */

@Autonomous(name="StepRunnerAuto", group="K9bot")
//@Disabled
public class StepRunnerAuto extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareK9bot robot = new HardwareK9bot();

    @Override
    public void runOpMode() {

        /* Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Can we make it?
        WaitStep waitStep = new WaitStep(30);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "StepRunnerAuto Ready");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        // Abort this loop is started or stopped.
        while (!(isStarted() || isStopRequested())) {
            idle();
        }

        // create steps and start them

        // run until the white line is seen OR the driver presses STOP;
        while (opModeIsActive() ) {

            // run the step
        }

        // Stop the step

    }
}
