package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.StartShooterStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.StopShooterStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - basic StepRunner autonomous test
 * Starts and Stops particle shooter.
 */

@Autonomous(name = "Shooter", group = "StepTests")
public class StepAuto_Shooter extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {



        // This step does one side, pauses, turns, pauses...
        Step mainStep = new SequenceStep(
                new StartShooterStep(1.0),
                new WaitStep(5000),
                new StopShooterStep()
        );

        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("Shooter", robot, mainStep);
    }

}
