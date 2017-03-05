


package org.firstinspires.ftc.teamcode.autonomous;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

        import org.firstinspires.ftc.teamcode.MorganaBot;
        import org.firstinspires.ftc.teamcode.steprunner.CountLoopStep;
        import org.firstinspires.ftc.teamcode.steprunner.RamperDriveStep;
        import org.firstinspires.ftc.teamcode.steprunner.SequenceStep;
        import org.firstinspires.ftc.teamcode.steprunner.Step;
        import org.firstinspires.ftc.teamcode.steprunner.TurnStep;
        import org.firstinspires.ftc.teamcode.steps.WaitStep;
        import org.firstinspires.ftc.teamcode.steprunner.ServoStep;
        import org.firstinspires.ftc.teamcode.steprunner.StartShooterStep;
        import org.firstinspires.ftc.teamcode.steprunner.StopShooterStep;

/**
 * ECR FTC 11096 - 2016 - 2017 Velocity Vortex - basic StepRunner autonomous test
 * <p>
 * This one just drives in a square, waiting a second after each side.
 */

@Autonomous(name = "Autonomous2", group = "StepTests")
public class StepAuto_Autonomous2 extends StepAutoCore {

    @Override
    public void runOpMode() throws InterruptedException {

        Step shootStep = new SequenceStep(
                new ServoStep(MorganaBot.FIRE_SERVO, MorganaBot.FIRE_GO),
                new WaitStep(500),
                new ServoStep(MorganaBot.FIRE_SERVO, MorganaBot.FIRE_STAY),
                new WaitStep(500)

        );

        // ... so our main step repeats that four times.
        Step mainStep = new SequenceStep(
                new RamperDriveStep(4000, 0.8),
                new StartShooterStep(1),
                new WaitStep(1000),
                new StartShooterStep(0.425),
                shootStep,
                new WaitStep(4000),
                shootStep,
                new StopShooterStep(),
                new RamperDriveStep(5000, 1)
        );

        // Create the robot and run our routine
        MorganaBot robot = new MorganaBot();
        runStepAutonomous("Square", robot, mainStep);
    }

}

