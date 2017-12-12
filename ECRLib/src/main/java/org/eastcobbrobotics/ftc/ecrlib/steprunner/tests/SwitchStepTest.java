package org.eastcobbrobotics.ftc.ecrlib.steprunner.tests;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.SayStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SwitchStep;

public class SwitchStepTest {

    public static void main(String[] args) {
        System.out.println("SwitchStep Test");

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

        // Try a switch step
        Step mainStep = new SwitchStep("testFlag",
                new SayStep("Choice Zero"),
                new SayStep("Choice One"),
                new SayStep("Choice Two")
        );

        // Fake the choice.
        mainStep.setFlag("testFlag", 2);

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
