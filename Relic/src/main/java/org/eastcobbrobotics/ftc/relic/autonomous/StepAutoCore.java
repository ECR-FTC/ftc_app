/*
East Cobb Robotics 11096
10/29/2016

this code is used for developing autonomous functions for the competition robot

*/
package org.eastcobbrobotics.ftc.relic.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.TelMessage;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;

import java.util.List;

/*
 *  StepAutoCore for ECR FTC 11096 Relic Recovery 2017-2018 ('Junior')
 *
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
    // Examples from last year:
    // protected static final double DISTANCE_TO_SHOOT_POSITION = 1.3;
    // protected static final double TURN_POWER = 0.8;

    // Common steps
    protected Step pause;

    public StepAutoCore() {

        // Define all the common steps we use in our routines

        // Two-second pause for troubleshooting.
        pause = new WaitStep(2000);

        // TODO: define other common steps that are used in multiple
        // autonomous routines. See StepAutoCore.java in Relic project
        // for examples.

    }

    /*
     * Main wrapper to run a StepRunner-based autonomous routine as an opmode.
     */

    public void runStepAutonomous(String autoName, StepRobot robot, Step mainStep) {

        // Initialize the robot.
        try {
            robot.init(hardwareMap);
        } catch (InterruptedException e) {
            return;         // just exit if interrupted
        }

        // Show telemetry and wait for Start button to be pressed
        // TODO: include name of routine in message?
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
     * Helper methods.
     */

    /*
     * Create a wait step of a specified duration; looks nicer
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

