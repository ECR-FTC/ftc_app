package org.firstinspires.ftc.teamcode.steprunner;

/**
 * Created by ECR FTC 11096 on 10/31/2016.
 * This step stops when we see a white line on the floor.
 */
public class FindWhiteLineStep extends Step {

    @Override
    public void run() {
        super.run();
        if (robot.checkWhiteLine()){
            tell("white line detected");
            stop();
        }
    }

}
