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

@Autonomous(name="StepRunnerAutoHello", group="K9bot")
//@Disabled
public class StepRunnerAuto extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareK9botECR bot = new HardwareK9botECR();

    @Override
    public void runOpMode() {

        /* Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
        */

/*
        Ramper r = new Ramper(10, 20, 100, 0, 1);
        for(int i = 0; i <= 100; i++)
        {
            double v = r.getRampValue((double) i);
            telemetry.addData("Power", String.format(Locale.US,"%d %.2f",i, v ));
            telemetry.update();
            sleep(200);
        }
*/

        try {
            bot.init(hardwareMap);
        } catch (InterruptedException e) {
        // TODO: what to do if this fails?
        }

        Robot robot = new Robot(bot);

        telemetry.addData("hi", "hi");
        telemetry.update();
        // we call the hardware map here

        //go straight until 2000 encoder ticks
        /*
        UntilOneDoneStep step1 = new UntilOneDoneStep();
        step1.add(new DriveStep(0.5));
        step1.add(new EncoderStep(2000));

        //turn for 2 seconds
        UntilOneDoneStep step2 = new UntilOneDoneStep();
        step2.add(new TurnStep(0.5, 1));
        step2.add(new WaitStep(2000));

        //sequence step 1 and 2
        SequenceStep step = new SequenceStep();
        step.add(step1);
        step.add(step2);
*/
        RamperDriveStep step = new RamperDriveStep( 0.60, 3000);
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
            telemetry.addData("Status", msg);    //
            telemetry.update();
            step.run();
        }

        // Stop the step
        telemetry.addData("Status", "StepRunnerAuto Stopping");    //
        telemetry.update();
        step.stop();

    }
}
