package org.eastcobbrobotics.ftc.ecrlib.steprunner;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 */
public class MotorStep extends Step {

    protected double speed = 0.0;
    protected int motorID;

    public MotorStep(int theMotorId, double theSpeed) {
        motorID = theMotorId;
        speed = theSpeed;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.setMotor(motorID, speed);
        tell("servoId=%d, position=%.2f", motorID, speed);
    }

    @Override
    public void run() {
        super.run();
        // Do we need to wait for the servo to get to position? Assuming not, just stop
        stop();

    }

    @Override
    public void stop() {
        super.stop();
    }

    ;

}
