package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by ECR-FTC on 10/30/2017.
 */

public class CheckImageStep extends Step {

    // Use Vuforia to detect the image. Sets the flag imageFound to 0 for UNKNOWN,
    // 1 for LEFT, 2 for CENTER, and 3 for RIGHT. Step STOPS when it sees something
    // besides UNKNOWN.

    @Override
    public void run() {
        super.run();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(((RelicBot) robot).relicTemplate);
        int imageFound = vuMark.ordinal();

        // SwitchSteps should assuming that UNKNOWN=0, LEFT=1, CENTER=2, RIGHT=3. TODO: is this
        // correct?
        setFlag("imageFound", imageFound);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
            tell("%b Image Found", imageFound);
            stop();
        } else {
            tell("no image detected yet");
        }
    }
}
