/*
    ECR FTC 11096 experimentation with basic autonomous

*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.HardwareK9botECR;
import org.firstinspires.ftc.teamcode.steprunner.MotorStep;
import org.firstinspires.ftc.teamcode.steprunner.Robot;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;
import org.firstinspires.ftc.teamcode.steprunner.DriveStep;

/**
 We're trying to get something that does nothing working.
 */

@Autonomous(name="StepRunnerAuto", group="K9bot")
//@Disabled
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

        // we call the hardware map here

        // Can we make it?

        UntilOneDoneStep step1 = new UntilOneDoneStep();
        step1.add(new DriveStep(0.5));
        step1.add(new WaitStep(2000));
        UntilOneDoneStep step2 = new UntilOneDoneStep();
        step2.add(new DriveStep(0.5));
        step2.add(new WaitStep(2000));
        SequenceStep step = new SequenceStep();
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
            telemetry.addData("Status", "StepRunnerAuto Running");    //
            telemetry.update();
            step.run();
        }

        // Stop the step
        telemetry.addData("Status", "StepRunnerAuto Stopping");    //
        telemetry.update();
        step.stop();

    }
}
