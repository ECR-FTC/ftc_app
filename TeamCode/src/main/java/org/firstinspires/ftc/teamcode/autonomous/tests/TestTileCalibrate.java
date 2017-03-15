package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.autonomous.StepAutoCore;
import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
import org.firstinspires.ftc.teamcode.steprunner.Step;
import org.firstinspires.ftc.teamcode.steprunner.TurnStep;
import org.firstinspires.ftc.teamcode.steprunner.WaitStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - basic StepRunner autonomous test
 * This one just drives one tile distance for calibration.
 */

@Autonomous(name = "TestTileCalibrate3", group = "StepTests")
public class TestTileCalibrate extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        Step mainStep = new RamperDriveStep(1.0, 0.5);
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("TestTileCalibrate2", robot, mainStep);
    }

}
