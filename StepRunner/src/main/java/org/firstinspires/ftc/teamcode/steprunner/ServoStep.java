package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 */
public class ServoStep extends Step {

    protected double position = 0.0;
    protected int servoId;

    public ServoStep(int theServoId, double thePosition) {
        servoId = theServoId;
        position = thePosition;
    }

    @Override
    public void start(StepRobot r) {
        super.start(r);
        robot.setServo(servoId, position);
        tell("servoId=%d, position=%.2f", servoId, position);
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
