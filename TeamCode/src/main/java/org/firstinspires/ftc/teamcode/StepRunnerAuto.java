/*
    ECR FTC 11096 experimentation with basic autonomous

*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareK9botECR;
import org.firstinspires.ftc.teamcode.steprunner.EncoderStep;
import org.firstinspires.ftc.teamcode.steprunner.MotorStep;
import org.firstinspires.ftc.teamcode.steprunner.Ramper;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.Robot;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.TurnStep;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;
import org.firstinspires.ftc.teamcode.steprunner.DriveStep;

import java.util.Locale;

/**
 We're trying to get something that does nothing working.
 */

@Autonomous(name="StepRunnerAuto", group="K9bot")
@Disabled
public class StepRunnerAuto extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareK9botECR bot = new HardwareK9botECR();

    @Override
    public void runOpMode() {

        /* Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
        */


        try {
            bot.init(hardwareMap);
        } catch (InterruptedException e) {
        // TODO: what to do if this fails?
        }

        Robot robot = new Robot(bot);
        bot.gyro.calibrate();

        // make sure the gyro is calibrated before continuing
        while (!isStopRequested() && bot.gyro.isCalibrating())  {
            sleep(50);
            idle();
        }

        telemetry.addData("t hi", "hi");
        telemetry.update();
        // we call the hardware map here

        //go straight until 2000 encoder ticks


        RamperDriveStep step1 = new RamperDriveStep(.4, 4000);
        TurnStep step2 = new TurnStep(0.40, -90);

        //sequence step 1 and 2
        SequenceStep step = new SequenceStep();
        step.add(step1);
        step.add(step2);
        step.add(step1);
        step.add(step2);
        step.add(step1);
        step.add(step2);
        step.add(step1);
        step.add(step2);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "StepRunnerAuto Ready");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        // Abort this loop is started or stopped.
        while (!(isStarted() || isStopRequested())) {
            idle();
        }

        // Start our step(s)
        telemetry.addData("Status", "StepRunnerAuto Starting");    //
        telemetry.update();
        step.start(robot);

        // run
        while (opModeIsActive() && step.isRunning()) {

            String msg = step.getTelemetry();
            if(msg != null && !msg.isEmpty()) {
                telemetry.addData("Status", msg);    //
                telemetry.update();
            }
            step.run();
        }

        // Stop the step
        telemetry.addData("Status", "StepRunnerAuto Stopping");    //
        telemetry.update();
        step.stop();

    }
}
