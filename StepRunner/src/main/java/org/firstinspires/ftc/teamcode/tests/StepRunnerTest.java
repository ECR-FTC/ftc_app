package org.firstinspires.ftc.teamcode.tests;

import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.SayStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.SwitchStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.WhileStep;

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


        // RamperDriveStep and turn test.
//        Step oneSide = new SequenceStep(
//                new RamperDriveStep(1000, 0.75),
//                new TurnStep(90, 0.75),
//                new WaitStep(200.0)
//        );
//
//        Step mainStep = new CountLoopStep(oneSide, 4);

        // Test WhileStep
//        Step mainStep = new WhileStep(
//                new RandomTestStep(),
//                new WaitStep(200)
//        );

        // Test CountLoopStep
        // Step mainStep = new CountLoopStep(new WaitStep(200), 4);

        // Test SwitchStep
        Step oneTest = new SwitchStep(new RandomResultStep(0, 3),
                new SayStep("rolled a zero"),
                null,
                new SayStep("rolled a two")
                );
        Step mainStep = new CountLoopStep(new SequenceStep(
                oneTest,
                new SayStep("--------------------")
        ), 4);

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
