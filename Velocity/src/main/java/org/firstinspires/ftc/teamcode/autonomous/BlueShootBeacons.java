


package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.SequenceStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.UntilOneDoneStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex
 */

@Autonomous(name = "BlueShootBeacons", group = "Competition")
public class BlueShootBeacons extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        MorganaBot robot = new MorganaBot();

        Step mainStep = new SequenceStep(

                new UntilOneDoneStep(
                        startShooter,
                        new SequenceStep(
                                driveToShootPositionWall,
                                shootParticle,
                                shootParticle
                        )
                ),
                stopShooter,
                blueDriveToBeaconSide,
                findBlueBeacon,
                pushBeaconButtonLeft,
                drivePastBeacon,
                findBlueBeacon,
                pushBeaconButtonLeft

        );

        runStepAutonomous("BlueShootBeacons", robot, mainStep);
    }

}