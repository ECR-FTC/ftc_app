package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.eastcobbrobotics.ftc.relic.RelicBot;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by ECR-FTC on 10/30/2017.
 */

public class CheckImageStep extends Step {

    @Override
    public void start(StepRobot r) {
        super.start(r);
    }

    @Override
    public void run() {
        super.run();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(((RelicBot) robot).relicTemplate);
        tell("%b Image Found", vuMark.ordinal());
        setFlag("imageFound", vuMark.ordinal());
    }
}
