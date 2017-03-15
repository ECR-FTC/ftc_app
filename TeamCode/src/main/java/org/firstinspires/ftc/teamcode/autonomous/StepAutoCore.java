/*
East Cobb Robotics 11096
10/29/2016

this code is used for developing autonomous functions for the competition robot

*/
package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.DriveStep;
import org.firstinspires.ftc.teamcode.steprunner.FindRedBlueStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.SayStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.ServoStep;
import org.firstinspires.ftc.teamcode.steprunner.SetFlagStep;
import org.firstinspires.ftc.teamcode.steprunner.StartShooterStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.StepRobot;
import org.firstinspires.ftc.teamcode.steprunner.StopShooterStep;
import org.firstinspires.ftc.teamcode.steprunner.TelMessage;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitForFlagStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;

import java.util.List;

/*
 *  This is the base class for all StepRunner-based autonomous classes. The actual logic
 *  for initializing the robot and running the main Step is here; child classes just create
 *  the robot and step in their runOpMode method and call runStepAutonomous with those:
 *
 *      SomeRobot robot = new SomeRobot();
 *      Step step = new SomeStep(...);
 *      runStepAutonomous(robot, mainStep);
 *
 *  The class also contains common steps that can be used by all autonomous variants so
 *  they don't have to create them separately. Remember not to use a particular step instance
 *  in parallel with itself.
 */


abstract public class StepAutoCore extends LinearOpMode {

    // Parameters to tweak
    protected static final double DISTANCE_TO_SHOOT_POSITION = 1.6;
    protected static final double DISTANCE_TO_PLATFORM = 2.0;
    protected static final double SHOOTER_SPINUP_TIME = 2000;
    protected static final double SHOOTER_POWER = 0.6;

    protected static final double BEACON_SCAN_SPEED = 0.15;
    protected static final int BEACON_PUSH_REPEAT = 2;          // push it this many times


    // Common steps
    protected Step driveToShootPosition;
    protected Step startShooter;
    protected Step shootParticle;
    protected Step stopShooter;
    protected Step driveToPlatform;
    protected Step driveToBeacon;
    protected Step pushBeaconButtonLeft;
    protected Step pushBeaconButtonRight;

    public StepAutoCore() {

        // Define all the common steps we use in our routines

        // Drive to the starting shoot position.
        driveToShootPosition = new RamperDriveStep(DISTANCE_TO_SHOOT_POSITION, 0.8);

        // Start shooter. This one just sets speed and waits a bit for it to get to speed.
        // Soon use the PID one instead.
        startShooter = new SequenceStep(
                new StartShooterStep(SHOOTER_POWER),
                new WaitStep(SHOOTER_SPINUP_TIME),
                new SetFlagStep("shooterReady", 1)
        );

        // Shoot a particle.
        shootParticle = new SequenceStep(
                new WaitForFlagStep("shooterReady"),
                new WaitStep(500),
                new ServoStep(MorganaBot.FIRE_SERVO, MorganaBot.FIRE_GO),
                new WaitStep(1000),
                new ServoStep(MorganaBot.FIRE_SERVO, MorganaBot.FIRE_STAY),
                new WaitStep(500)
        );

        // Stop shooter.
        stopShooter = new SequenceStep(
                new StopShooterStep(),
                new SetFlagStep("shooterReady", null)
        );

        // Drive to platform.
        driveToPlatform = new RamperDriveStep(DISTANCE_TO_PLATFORM, 1.0);

        // Drive until we see the red or blue beacon. Currently using non-ramping drive.
        driveToBeacon = new UntilOneDoneStep(
                new DriveStep(BEACON_SCAN_SPEED),
                new FindRedBlueStep()
        );

        // Repeatedly push the beacon button.
        pushBeaconButtonLeft = new CountLoopStep(new SequenceStep(
                new ServoStep(MorganaBot.LEFT_SERVO, MorganaBot.LEFT_PRESS),
                new WaitStep(500),
                new ServoStep(MorganaBot.LEFT_SERVO, MorganaBot.LEFT_STORE),
                new WaitStep(500)
        ), BEACON_PUSH_REPEAT);

        pushBeaconButtonRight = new CountLoopStep(new SequenceStep(
                new ServoStep(MorganaBot.RIGHT_SERVO, MorganaBot.RIGHT_PRESS),
                new WaitStep(500),
                new ServoStep(MorganaBot.RIGHT_SERVO, MorganaBot.RIGHT_STORE),
                new WaitStep(500)
        ), BEACON_PUSH_REPEAT);
    }


    public void runStepAutonomous(String autoName, StepRobot robot, Step mainStep) {

        // Initialize the robot.
        try {
            robot.init(hardwareMap);
        } catch (InterruptedException e) {
            return;         // just exit if interrupted
        }

        // Show telemetry and wait for Start button to be pressed
        showMessage("Waiting for start");
        waitForStart();

        // Start our main step
        showMessage("Starting main step");
        mainStep.start(robot);

        // Run until we're done
        while (opModeIsActive() && mainStep.isRunning()) {
            mainStep.run();

            // If there are telemetry messages, show them and flush them.
            List<TelMessage> messages = mainStep.getMessages();
            if (!messages.isEmpty()) {
                for (TelMessage tm: messages) {
                    telemetry.addData(tm.caption, tm.message);
                }
                telemetry.update();
                messages.clear();
            }

            // Let the rest of the robot do whatever it wants
            idle();
        }

        // Stop the main step
        showMessage("Stopping main step");
        mainStep.stop();
    }

    /*
     * Create a wait step; looks nicer
     */
    protected WaitStep waitFor(double duration) {
        return new WaitStep(duration);
    }


    /*
     * Show a telemetry message
     */

    protected void showMessage(String message) {
        telemetry.addData(this.getClass().getSimpleName(), message);
        telemetry.update();
    }
}


