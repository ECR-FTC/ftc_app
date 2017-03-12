


package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex
 */

@Autonomous(name = "RedShootBeacons", group = "Competition")
public class RedShootBeacons extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        MorganaBot robot = new MorganaBot();

        Step mainStep = new SequenceStep(
                initialWait,
                driveToShootPosition,
                startShooter,
                shootParticle,
                shootParticle,
                driveToBeacon

                // TODO: push red side
        );

        runStepAutonomous("RedShootBeacons", robot, mainStep);
    }

}
