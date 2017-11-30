/*
East Cobb Robotics 11096
10/29/2016

this code is used for developing autonomous functions for the competition robot

*/
package org.eastcobbrobotics.ftc.relic.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Blinker;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SwitchStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.TelMessage;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilAllDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.ServoStep;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.GlyphterStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.ReadColorSensorLeftStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.ReadColorSensorRightStep;
import org.eastcobbrobotics.ftc.relic.autonomous.Steps.ReadColorSensorStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.DriveStep;

import java.util.List;

import static org.eastcobbrobotics.ftc.relic.RelicBot.LEFT_ARM_ELBOW_SERVO;
import static org.eastcobbrobotics.ftc.relic.RelicBot.LEFT_ARM_WRIST_SERVO;
import static org.eastcobbrobotics.ftc.relic.RelicBot.LEFT_GRAB_SERVO;
import static org.eastcobbrobotics.ftc.relic.RelicBot.RIGHT_ARM_ELBOW_SERVO;
import static org.eastcobbrobotics.ftc.relic.RelicBot.RIGHT_ARM_WRIST_SERVO;
import static org.eastcobbrobotics.ftc.relic.RelicBot.RIGHT_GRAB_SERVO;
import static org.eastcobbrobotics.ftc.relic.RelicBot.RIGHT_JEWEL_STORE;

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

    protected Step deployLeftArmStep;
    protected Step retractLeftArmStep;
    protected Step flickLeftBallStep;

    protected Step flickRightBallStep;
    protected Step deployRightArmStep;
    protected Step retractRightArmStep;

    protected Step grabGlyph;
    protected Step releaseGlyph;

    public StepAutoCore() {

        // Define all the common steps we use in our routines

        // Two-second pause for troubleshooting.
        pause = new WaitStep(2000);

        // TODO: define other common steps that are used in multiple
        // autonomous routines. See StepAutoCore.java in Velocity project
        // for examples.

        //Left side code
        deployLeftArmStep = new UntilAllDoneStep(
                new WaitStep(2000),
                new SequenceStep(
                        new WaitStep(100),
                        new ServoStep(LEFT_ARM_WRIST_SERVO, RelicBot.LEFT_WRIST_CENTER)
                ),
                new ServoStep(LEFT_ARM_ELBOW_SERVO, RelicBot.LEFT_JEWEL_DOWN)
        );
        retractLeftArmStep = new UntilAllDoneStep(
                new WaitStep(1000),
                new ServoStep(RelicBot.LEFT_ARM_ELBOW_SERVO, ((RelicBot.LEFT_JEWEL_STORE+RelicBot.LEFT_JEWEL_DOWN)/2)),
                new WaitStep(1000),
                new ServoStep(RelicBot.LEFT_ARM_ELBOW_SERVO, RelicBot.LEFT_JEWEL_STORE),
                new SequenceStep
                        (
                                new WaitStep(1000),
                                new ServoStep(LEFT_ARM_WRIST_SERVO, RelicBot.LEFT_WRIST_STORE),
                                new WaitStep(1000)
                        )
        );
        flickLeftBallStep = new SequenceStep(
                new UntilOneDoneStep(
                        new WaitStep(3000),
                        new UntilAllDoneStep(
                                new ReadColorSensorLeftStep(),
                                new WaitStep(1000)
                        ),
                        new UntilAllDoneStep(
                                new WaitStep(1000),
                                new SwitchStep("colorFound",
                                        new ServoStep(LEFT_ARM_WRIST_SERVO, RelicBot.LEFT_WRIST_LEFT),
                                        new WaitStep(500),
                                        new ServoStep(LEFT_ARM_WRIST_SERVO, RelicBot.LEFT_WRIST_RIGHT)
                                )
                        ))
        );

        //Right side code
        deployRightArmStep = new UntilAllDoneStep(
                new WaitStep(2000),
                new SequenceStep(
                        new WaitStep(200),
                        new ServoStep(RIGHT_ARM_ELBOW_SERVO, RelicBot.RIGHT_JEWEL_DOWN)
                ),
                new ServoStep(RIGHT_ARM_WRIST_SERVO, RelicBot.RIGHT_WRIST_CENTER)
        );
        retractRightArmStep = new UntilAllDoneStep(
                new WaitStep(1000),
                new ServoStep(RelicBot.RIGHT_ARM_ELBOW_SERVO, ((RelicBot.RIGHT_JEWEL_STORE+RelicBot.RIGHT_JEWEL_DOWN)/2)),
                new WaitStep(1000),
                new ServoStep(RelicBot.RIGHT_ARM_ELBOW_SERVO, RelicBot.RIGHT_JEWEL_STORE),
                new SequenceStep
                (
                        new WaitStep(1000),
                        new ServoStep(RIGHT_ARM_WRIST_SERVO, RelicBot.RIGHT_WRIST_STORE),
                        new WaitStep(1000)
                )
        );
        flickRightBallStep = new SequenceStep(
                new UntilOneDoneStep(
                        new WaitStep(3000),
                        new UntilAllDoneStep(
                                new ReadColorSensorRightStep(),
                                new WaitStep(1000)
                        ),
                        new UntilAllDoneStep(
                                new WaitStep(1000),
                                new SwitchStep("colorFound",
                                        new ServoStep(RIGHT_ARM_WRIST_SERVO, RelicBot.RIGHT_WRIST_LEFT),
                                        new WaitStep(500),
                                        new ServoStep(RIGHT_ARM_WRIST_SERVO, RelicBot.RIGHT_WRIST_RIGHT)
                                )
                        ))
        );

        grabGlyph = new SequenceStep(
                new UntilAllDoneStep(
                        new WaitStep(1000),
                        new ServoStep(LEFT_GRAB_SERVO, 0.22),//todo this is the value for the old servos
                        new ServoStep(RIGHT_GRAB_SERVO, 0.59)//todo this is the value for the old servos
                ),
                new UntilOneDoneStep(
                        new WaitStep(500),
                        new GlyphterStep(-1)
                )
        );

        releaseGlyph = new SequenceStep(
                new UntilOneDoneStep(
                        new WaitStep(200),
                        new GlyphterStep(1)
                ),
                new UntilAllDoneStep(
                        new WaitStep(500),
                        new ServoStep(LEFT_GRAB_SERVO, 0.80),//todo this is the value for the old servos
                        new ServoStep(RIGHT_GRAB_SERVO, 0.07)    //todo this is the value for the old servos
                )
        );
    }

    /*
     * Main wrapper to run a StepRunner-based autonomous routine as an opmode.
     */

    public void runStepAutonomous(String autoName, StepRobot robot, Step mainStep) {

        // Initialize the robot.
        try {
            robot.setTelemetry(telemetry);
            robot.init(hardwareMap);
        } catch (InterruptedException e) {
            return;         // just exit if interrupted
        }

        // Wait for Start button to be pressed
        robot.tell(autoName + ": Waiting for start");
        telemetry.update();
        waitForStart();

        // Start our main step
        robot.tell(autoName + ": Starting main step");
        telemetry.update();

        mainStep.start(robot);
        telemetry.update();

        // Run until we're done
        while (opModeIsActive() && mainStep.isRunning()) {
            mainStep.run();
            telemetry.update();
            idle();
        }

        // Stop the main step
        robot.tell(autoName + ": Stopping main step");
        mainStep.stop();
        robot.tell(autoName + ": Shutting down");
        telemetry.update();
        robot.shutDown();
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

}


