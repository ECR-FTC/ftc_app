


package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex
 */

@Autonomous(name = "BlueShootBeacons", group = "Competition")
public class BlueShootBeacons extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        MorganaBot robot = new MorganaBot();

        Step mainStep = new SequenceStep(
                driveToShootPosition,
                startShooter,
                shootParticle,
                shootParticle,
                driveToBeacon

                // TODO: push blue side
        );

        runStepAutonomous("BlueShootBeacons", robot, mainStep);
    }

}
