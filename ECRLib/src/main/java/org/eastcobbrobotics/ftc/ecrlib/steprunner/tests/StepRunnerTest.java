package org.eastcobbrobotics.ftc.ecrlib.steprunner.tests;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.SayStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.WaitForFlagStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;

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


        // Test CountLoopStep
        // Step mainStep = new CountLoopStep(new WaitStep(200), 4);

        // Test SwitchStep
//        Step oneTest = new SwitchStep(new RandomResultStep(0, 3),
//                new SayStep("rolled a zero"),
//                null,
//                new SayStep("rolled a two")
//                );
//        Step mainStep = new CountLoopStep(new SequenceStep(
//                oneTest,
//                new SayStep("--------------------")
//        ), 4);

        // Test WaitForFlagStep; "roll" D20 until we see a 10 or a 15.

        Step mainStep = new UntilOneDoneStep(
            new RandomFlagValueStep("test_flag", 1, 20),
            new SequenceStep(new WaitForFlagStep("test_flag", 10), new SayStep("got 10")),
            new SequenceStep(new WaitForFlagStep("test_flag", 15), new SayStep("got 15"))
        );


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
