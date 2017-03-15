


package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.steprunner.PIDShootStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.ServoStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.StopShooterStep;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex
 */

@Autonomous(name = "ShootPark", group = "Competition")
public class ShootPark extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        MorganaBot robot = new MorganaBot();

        Step mainStep = new SequenceStep(
                driveToShootPosition,
                startShooter,
                shootParticle,
                waitFor(2000),
                shootParticle,
                driveToPlatform
        );

        runStepAutonomous("ShootPark", robot, mainStep);
    }

}
