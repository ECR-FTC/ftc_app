


package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex
 */

@Autonomous(name = "RedShootBeacons1", group = "Competition")
public class RedShootBeacons extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        MorganaBot robot = new MorganaBot();

        Step mainStep = new SequenceStep(
                startShooter,
                driveToShootPosition,
                shootParticle,
                shootParticle,
                stopShooter,
                redDriveToBeacon,
                findRedBeacon,
                pushBeaconButtonRight,
                drivePastBeacon,
                findRedBeacon,
                pushBeaconButtonRight
        );

        runStepAutonomous("RedShootBeacons1", robot, mainStep);
    }

}
