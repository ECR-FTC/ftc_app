package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.DriveStep;
import org.firstinspires.ftc.teamcode.steprunner.EncoderStep;
import org.firstinspires.ftc.teamcode.steprunner.JavaWaitStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.TelMessage;
import org.firstinspires.ftc.teamcode.steprunner.TurnStep;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;

import java.util.List;

public class StepRunnerTest {

    public static void main(String[] args) {
        System.out.println("StepRunner Test");

        MockRobot robot = new MockRobot();

         // Initialize the robot.
        try {
            robot.init(null);
        } catch (InterruptedException e) {
            return;         // just exit if interrupted
        }

        // Steps use console for logging when we test, so we bypass
        // the telemetry stuff
        Step.useConsole(true);

        Step oneSide = new SequenceStep(
                new RamperDriveStep(1000, 0.75),
                new TurnStep(90, 0.75),
                new JavaWaitStep(200.0)
        );

        Step mainStep = new CountLoopStep(oneSide, 4);

        long startTime = System.currentTimeMillis();

        // Start our main step
        System.out.println("Starting main step");
        mainStep.start(robot);

        // Run until we're done
        while (mainStep.isRunning()) {
            long now = System.currentTimeMillis();
            System.out.println(String.format("t=%d ms", now - startTime));

            mainStep.run();

            // For now, at least, sleep 100ms between iterations to reduce
            // the number of trace messages.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }

            // Let the rest of the robot do whatever it wants
            // idle();
        }

        // Stop the main step
        System.out.println("Stopping main step");
        mainStep.stop();

        long endTime = System.currentTimeMillis();
        System.out.println(String.format("Elapsed: %d ms", endTime - startTime));

    }

}
