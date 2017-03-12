


package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;
import org.firstinspires.ftc.teamcode.steprunner.PIDShootStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.UntilOneDoneStep;
import org.firstinspires.ftc.teamcode.steprunner.ServoStep;
import org.firstinspires.ftc.teamcode.steprunner.StopShooterStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - basic StepRunner autonomous code
 * <p>
 * Our autonomous code implements PID control in our shoot motor to wait for 2 seconds, allow the fire servo to be at the position of Fire Go,
 * waits for 1 second, and allows the fire servo to be at the position of Fire Stay.
 */

@Autonomous(name = "Auto 2", group = "Competition")
public class StepAuto_Autonomous2 extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {
        Step fireServoStep = new SequenceStep(
                new WaitStep(2000),
                new ServoStep(3, MorganaBot.FIRE_GO),
                new WaitStep(1000),
                new ServoStep(3, MorganaBot.FIRE_STAY)
        );
        Step shootStep = new UntilOneDoneStep(
                new PIDShootStep(),
                fireServoStep
        );


        Step mainStep = new SequenceStep(
                new RamperDriveStep(4000, 0.8),
                shootStep,
                shootStep,
                new StopShooterStep(),
                new RamperDriveStep(5000, 1)
        );

        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("Autonomous 2", robot, mainStep);
    }

}
