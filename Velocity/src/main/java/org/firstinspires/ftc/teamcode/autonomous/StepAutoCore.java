/*
East Cobb Robotics 11096
10/29/2016

this code is used for developing autonomous functions for the competition robot

*/
package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.CountLoopStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.DriveStep;
import org.firstinspires.ftc.teamcode.autonomous.steps.FindRedBlueOnBlueStep;
import org.firstinspires.ftc.teamcode.autonomous.steps.FindRedBlueStep;
import org.firstinspires.ftc.teamcode.autonomous.steps.FindWhiteLineStep;
import org.firstinspires.ftc.teamcode.autonomous.steps.RamperDriveSidewaysStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.autonomous.steps.RunShooterStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.ServoStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SetFlagStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.firstinspires.ftc.teamcode.autonomous.steps.StopShooterStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SwitchStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.TelMessage;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.TurnStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitForFlagStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;

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
    protected static final double DISTANCE_TO_SHOOT_POSITION = 1.3;
    protected static final double DISTANCE_TO_PLATFORM = 1.8;
    protected static final double DISTANCE_TO_SHOOT_FROM_WALL = 0.7;

    protected static final double SHOOTER_SPINUP_TIME = 2000;
    protected static final double SHOOTER_TPS = 800;            // firing speed in ticks per second

    protected static final double TURN_POWER = 0.8;
    protected static final double CRUISE_POWER = 0.7;
    protected static final double FULL_POWER = 1.0;

    protected static final double APPROACH_BEACON_POWER = 0.4;

    protected static final double BEACON_SCAN_SPEED = 0.125;
    protected static final int BEACON_PUSH_REPEAT = 3;          // push it this many times

    protected static final double WHITE_LINE_SCAN_SPEED = 0.2;  // how fast to go while looking for white line


    // Common steps
    protected Step pause;
    protected Step driveToShootPositionWall;
    protected Step driveToShootPosition;
    protected Step startShooter;
    protected Step shootParticle;
    protected Step stopShooter;
    protected Step driveToPlatform;
    protected Step driveToWhiteLine;
    protected Step drivePastBeacon;
    protected Step redDriveToBeaconCorner;
    protected Step redDriveToBeaconSide;
    protected Step blueDriveToBeaconSide;
    protected Step findRedBeacon;
    protected Step findBlueBeacon;
    protected Step pushBeaconButtonLeft;
    protected Step pushBeaconButtonRight;

    public StepAutoCore() {

        // Define all the common steps we use in our routines

        // Two-second pause for troubleshooting.
        pause = new WaitStep(2000);

        // Drive to the starting shoot position.
        driveToShootPosition = new RamperDriveStep(DISTANCE_TO_SHOOT_POSITION, CRUISE_POWER);

        // Drive to the starting shoot position.
        driveToShootPositionWall = new RamperDriveStep(DISTANCE_TO_SHOOT_FROM_WALL, CRUISE_POWER);

        startShooter = new RunShooterStep(SHOOTER_TPS);

        // Shoot a particle.
        shootParticle = new SequenceStep(
                new WaitForFlagStep("shooterReady"),
                new ServoStep(MorganaBot.FIRE_SERVO, MorganaBot.FIRE_GO),
                new WaitStep(500),
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

        // Drive from red shoot position to red side beacon
        redDriveToBeaconCorner = new SequenceStep(
                new TurnStep(-45, TURN_POWER),
                new RamperDriveStep(2.2, CRUISE_POWER),
                new TurnStep(45, TURN_POWER),
                new RamperDriveStep(1.4, APPROACH_BEACON_POWER),
                new TurnStep(45, TURN_POWER)
        );

        redDriveToBeaconSide = new SequenceStep(
                // this is a last-second test to try to beat the 30 second limit
                // run everything at full speed and get rid of one of the turns
                new TurnStep(-50, 1.0),
                new RamperDriveStep(2.2, FULL_POWER),
                new TurnStep(50, 1.0),
                new UntilOneDoneStep(
                        new WaitStep(1500),
                        new RamperDriveSidewaysStep(0.5, 1, -1)
                )
        );

        // Find the red beacon
        findRedBeacon = new SequenceStep(
                new ServoStep(MorganaBot.RIGHT_SERVO, MorganaBot.RIGHT_SCAN),
/*                new UntilOneDoneStep(
                        new RamperDriveSidewaysStep(.25, 1, 1),
                        new WaitStep(250)
                ),*/
                new WaitStep(500),
                new UntilOneDoneStep(
                        new DriveStep(BEACON_SCAN_SPEED),
                        new SequenceStep(
                                new FindRedBlueStep(),
                                new SwitchStep("colorFound",
                                        null,
                                        null, //red is where we want to be
                                        new WaitStep(750)
                                )
                        )
                ),
                new UntilOneDoneStep(
                        new RamperDriveSidewaysStep(.25, 1, -1),
                        new WaitStep(750)
                ),
                new ServoStep(MorganaBot.RIGHT_SERVO, MorganaBot.RIGHT_STORE)
        );

        // Drive from blue shoot position to blue side beacon

        blueDriveToBeaconSide = new SequenceStep(
                // this is a last-second test to try to beat the 30 second limit
                // run everything at full speed and get rid of one of the turns
                new TurnStep(50, 1.0),
                new RamperDriveStep(2.2, FULL_POWER),
                new TurnStep(-50, 1.0),
                new UntilOneDoneStep(
                        new WaitStep(1500),
                        new RamperDriveSidewaysStep(0.5, 1, 1)
                )
        );

        // Find the blue beacon
        findBlueBeacon = new SequenceStep(
                new ServoStep(MorganaBot.LEFT_SERVO, MorganaBot.LEFT_SCAN),
/*                new UntilOneDoneStep(
                        new RamperDriveSidewaysStep(.25, 1, 1),
                        new WaitStep(250)
                ),*/
                new WaitStep(500),
                new UntilOneDoneStep(
                        new DriveStep(BEACON_SCAN_SPEED),
                        new SequenceStep(
                                new FindRedBlueOnBlueStep(),
                                new SwitchStep("colorFound",
                                        null,
                                        null, //red is where we want to be
                                        new WaitStep(750)
                                )
                        )
                ),
                new UntilOneDoneStep(
                        new RamperDriveSidewaysStep(.25, 1, 1),
                        new WaitStep(750)
                ),
                new ServoStep(MorganaBot.RIGHT_SERVO, MorganaBot.RIGHT_STORE)
        );

        drivePastBeacon = new RamperDriveStep(1.5, CRUISE_POWER);

        driveToWhiteLine = new UntilOneDoneStep(
                new RamperDriveStep(6.0, WHITE_LINE_SCAN_SPEED),
                new FindWhiteLineStep()
        );

        // Drive until we see the red or blue beacon. Currently using non-ramping drive.
//        driveToBeacon = new UntilOneDoneStep(
//                new DriveStep(BEACON_SCAN_SPEED),
//                new FindRedBlueStep()
//        );

        // Repeatedly push the beacon button.
        pushBeaconButtonLeft = new CountLoopStep(new SequenceStep(
                new ServoStep(MorganaBot.LEFT_SERVO, MorganaBot.LEFT_PRESS),
                new WaitStep(750),
                new ServoStep(MorganaBot.LEFT_SERVO, MorganaBot.LEFT_STORE),
                new WaitStep(500)
        ), BEACON_PUSH_REPEAT);

        pushBeaconButtonRight = new CountLoopStep(new SequenceStep(
                new ServoStep(MorganaBot.RIGHT_SERVO, MorganaBot.RIGHT_PRESS),
                new WaitStep(750),
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
                for (TelMessage tm : messages) {
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


