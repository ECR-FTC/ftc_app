/*
East Cobb Robotics 11096
10/29/2016

this code is used for developing autonomous functions for the competition robot

*/
package org.firstinspires.ftc.teamcode.steprunner;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.StepRobot;

import java.util.List;

/*
 *  This is the base class for all StepRunner-based autonomous classes. The actual logic
 *  for initializing the robot and running the main Step is here; child classes just create
 *  the robot and step in their runOpMode method and call runStepAutonomous with those.
 *
 */

public class StepAutoCore extends LinearOpMode {

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

    @Override
    public void runOpMode() throws InterruptedException {
        /*
         * SUBCLASSES OVERRIDE THIS METHOD LIKE:
         *
         * SomeRobot robot = new SomeRobot();
         * SomeStep step = new SomeStep(...);
         * runStepAutonomous(robot, mainStep);
         *
         */
    }

    /*
     * Show a telemetry message
     */

    protected void showMessage(String message) {
        telemetry.addData(this.getClass().getSimpleName(), message);
        telemetry.update();
    }
}


