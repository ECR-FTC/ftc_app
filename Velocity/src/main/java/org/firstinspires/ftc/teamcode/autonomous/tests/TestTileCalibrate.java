package org.firstinspires.ftc.teamcode.autonomous.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MorganaBot;
import org.firstinspires.ftc.teamcode.autonomous.StepAutoCore;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.RamperDriveStep;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - basic StepRunner autonomous test
 * This one just drives one tile distance for calibration.
 */

@Autonomous(name = "TestTileCalibrate", group = "StepTests")
public class TestTileCalibrate extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        Step mainStep = new RamperDriveStep(3.0, 0.5);
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("TestTileCalibrate", robot, mainStep);
    }

}
