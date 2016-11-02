/*
East Cobb Robotics 11096
10/29/2016

this code is used for developing autonomous functions for the competition robot


*/
package org.firstinspires.ftc.teamcode;

//these are all the imports that we use to allow us to use all of the sensors and motors
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.rint;

@Autonomous(name="Build2Auto1b", group="Linear Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class Build2Auto1b extends LinearOpMode {
        HardwareBuild2 robot;

        private ElapsedTime runtime = new ElapsedTime();
    // here we declare 2 things, the hardware map, and an elapsed time funtion
    boolean lineSeen;
        @Override
        public void runOpMode() throws InterruptedException {
            robot = new HardwareBuild2();          // Use Build2's Hardware file

            try {
                robot.init(hardwareMap);
            } catch (InterruptedException e) {
            }
            // we call the hardware map here

            telemetry.addData("Status", "Initialized");
            telemetry.update();

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */

            waitForStart();   // we set our servos in init and wait for start (the driver hitting play)

            //this is a sample autonomous where the robot shoots 2 balls and pushes both beacons.
            // this is a blue code
            autoFire(true,false);
            autoFire(false,true);
            /*
            autoFire(true, false); // spin up motor and shoot 1st ball
            autoFire(false, true); // shoot second ball
            autoGoOneTileForward(2);
            autoGyroTurn(90);
            autoGoOneTileForward(2);
            autoGyroTurn(-90); // rotate parallel to wall
            autoDrive(0, (float) 0.5, 0, 1000); // push into wall
            autoGoToWhiteLine(1000, true, false);
            if (lineSeen) {
                autoBeacon(false);

                autoGoToWhiteLine(2000, true, false);
                if (lineSeen) {
                    autoBeacon(false);
                }
            }
            */
            autoGoOneTileForward(1);
         }


    //
    public void autoGoToWhiteLine (double waitFor, boolean isBlue, boolean isRed) // when this code is called, the robot goes to the white line and stops
    {
        runtime.reset(); // this is called allot, it resets 'runtime' to 0

        while((robot.ODS.getRawLightDetected() < 0.2) & (runtime.seconds() < waitFor) ) {
            if (isBlue)
            {
                robot.motorBackRight.setPower(.25);
                robot.motorBackLeft.setPower(.15);
                robot.motorFrontLeft.setPower(.25);
                robot.motorFrontRight.setPower(.15);
            }
            if (isRed)
            {
                robot.motorBackRight.setPower(.25);
                robot.motorBackLeft.setPower(.15);
                robot.motorFrontLeft.setPower(.25);
                robot.motorFrontRight.setPower(.15);
            }
            lineSeen = false;
            telemetry.addData("No white line:", robot.ODS.getRawLightDetected());
            // if the robot doesn't see the line, and still has time, it goes forward.
        }

        driveMotorStop();
        lineSeen = true;
        telemetry.addData("I see the line:", robot.ODS.getRawLightDetected());
        telemetry.update();
        // if the robot does see the line, it stops.
        return;
    }
    public void autoGoOneTileForward(int tiles)
    {
        float portTarget;
        double speedPort, speedStar;
        float ENCODER_ACCEL_CONST = 500;
        //double ENCODER_PORT_RESET = robot.motorFrontLeft.getCurrentPosition();
        portTarget = (float)tiles * robot.GO_ONE_TILE_PORT;
        //starTarget = (float)currentParameter * GO_ONE_TILE_STAR;

        while ((Math.abs(robot.motorFrontLeft.getCurrentPosition()) < Math.abs(portTarget) - 5) )// ||
        {
            // speed is proportional to number of encoder steps away from target
            speedPort = .5 / ENCODER_ACCEL_CONST * (portTarget - (robot.motorFrontLeft.getCurrentPosition()));
            speedStar = speedPort;
            //speedStar = MOTOR_POWER / ENCODER_ACCEL_CONST * (starTarget - (robot.rightMotor.getCurrentPosition()-ENCODER_STAR_RESET));

            // don't go faster than SPEED_NOMINAL
            speedPort = Math.signum(speedPort) * Math.min(Math.abs(speedPort), .5);
            speedStar = Math.signum(speedStar) * Math.min(Math.abs(speedStar), .5);

            // don't go slower than SPEED_MIN
            speedPort = Math.signum(speedPort) * Math.max(Math.abs(speedPort), .15);
            speedStar = Math.signum(speedStar) * Math.max(Math.abs(speedStar), .15);

            // set the motor speeds

            robot.motorFrontRight.setPower(speedStar);
            robot.motorFrontLeft.setPower(speedPort);
            robot.motorBackRight.setPower(speedStar);
            robot.motorBackLeft.setPower(speedPort);
        }
        driveMotorStop();
    }

    public void autoGyroTurn (int degrees) {
        int heading;
        int headingTolerance = 5;
        double speedPort;
        heading = robot.gyro.getIntegratedZValue(); // getIntegratedZValue() works for any turn angle

        while ( (Math.abs(heading) < (Math.abs(degrees)-headingTolerance)))
        {
            // speed is proportional to angle away from target
            speedPort = (float)(.5 / 15 * (degrees - heading));
            // don't go faster than SPEED_NOMINAL
            speedPort = Math.signum(speedPort) * Math.min(Math.abs(speedPort), .5);
            // don't go slower than SPEED_MIN
            speedPort = Math.signum(speedPort) * Math.max(Math.abs(speedPort), .15);

            // set the speeds
            robot.motorBackLeft.setPower(-speedPort);
            robot.motorBackRight.setPower(speedPort);
            robot.motorFrontLeft.setPower(-speedPort);
            robot.motorFrontRight.setPower(speedPort);
        }
        driveMotorStop();
    }

    public void autoBeacon (boolean isRed) { //this will (when called) tell the robot to push the beacon
        // our color sensor is in front of the servo

        // if we see our color (red) we drive forward, if we don't, we do nothing
        if ((robot.colorR.red() > 2) && isRed) {
            while ((runtime.seconds() < .5)) {
                robot.motorBackRight.setPower(-.25);
                robot.motorBackLeft.setPower(-.15);
                robot.motorFrontLeft.setPower(-.25);
                robot.motorFrontRight.setPower(-.15);
            }
            driveMotorStop();
        runtime.reset();
        }
        if ((robot.colorR.blue() > 2) && !isRed) {
            while ((runtime.seconds() < .5)) {
                robot.motorBackRight.setPower(-.15);
                robot.motorBackLeft.setPower(-.25);
                robot.motorFrontLeft.setPower(-.15);
                robot.motorFrontRight.setPower(-.25);
            }
            driveMotorStop();
            runtime.reset();
        }// once we are positioned, we press the beacon
        while ((runtime.seconds() < 0.5)) {
            telemetry.update();
            robot.rightServo.setPosition(0.1);
        }
        robot.rightServo.setPosition(.7);
    }

    // autoFire(): this code fires the particles
    // input arguments:
    //      motorSpinUp: boolean, set to true to spin up the motor from stop
    //          set false to shoot without spinning up the motor
    // output arguments: none

    public void autoFire(boolean motorSpinUp, boolean motorTurnOff) {
        if (motorSpinUp) {//spins up the motor at full speed for 2 seconds ro make it faster.
            runtime.reset();// unless we tell it not to (eg: autofire(false);)
            while ((runtime.seconds() < robot.shootRampTime)) {
                robot.motorShoot.setPower(robot.shootRampPower);
            }
        }
        robot.motorShoot.setPower(robot.shootPower);//this sets the motor to firing speed
        runtime.reset();
        while ((runtime.seconds() < robot.fireServoTime)) {
            robot.fireServo.setPosition(robot.fireGo);// and we fire it here
        }
        runtime.reset();
        while ((runtime.seconds()) < 2*robot.fireServoTime) {
            robot.fireServo.setPosition(robot.fireStay);// the servo is retracted after firing here.
        }
        if (motorTurnOff)
        {
            robot.motorShoot.setPower(0.0);
        }
    }

    public void autoDrive(float foward, float left, float turn, long waitFor){
        telemetry.addData("Say", "teledrive! 0");
        telemetry.update();
        // this code runs the robot in all directions using a combination of forward, left, and turn
        // combined with a time. It runs off of time.
        float a, b, c;
//		float norm;
        // holonomic tank drive

        a = foward;
        b = left;
        c = turn;//change variables into smaller ones

        MotorPower returnMotorPower; // we call another program here to determine our motor powwers for us
        returnMotorPower = motorScale(a - b - c, a + b - c, a + b + c, a - b + c, (float) 1.0);

        // write the values to the motors
        robot.motorBackRight.setPower(returnMotorPower.getMotorBackRight());
        robot.motorBackLeft.setPower(returnMotorPower.getMotorBackLeft());
        robot.motorFrontLeft.setPower(returnMotorPower.getMotorFrontLeft());
        robot.motorFrontRight.setPower(returnMotorPower.getMotorFrontRight());
        runtime.reset();  // then we set our motor powers
        while ((runtime.seconds() < waitFor/1000.0)) {
            telemetry.update();
        }

        driveMotorStop();
        runtime.reset();
        while ((runtime.seconds() < 0.25)) {
            telemetry.update(); // make sure we stop
        }

    }

    public void driveMotorStop()
    { // this stops our motors to make it easier to write (one line instead of 4)
        robot.motorBackRight.setPower(0.0);
        robot.motorBackLeft.setPower(0.0);
        robot.motorFrontLeft.setPower(0.0);
        robot.motorFrontRight.setPower(0.0);
    }

    public MotorPower motorScale(float motorFL, float motorBL, float motorFR, float motorBR, float maxSpeed) {
        float norm; // this is a code that normalizes motor speeds.
        float motorFLadj = 0, motorBLadj = 0, motorFRadj = 0, motorBRadj = 0;
        // Normalise by the largest motor power.
        norm = max(max(abs(motorFL), abs(motorBL)), max(abs(motorFR), abs(motorBR)));
        norm = max(norm, (float) 1.0);

        motorFLadj = (Range.clip((motorFL) / norm, -maxSpeed, maxSpeed));
        motorBLadj = (Range.clip((motorBL) / norm, -maxSpeed, maxSpeed));// clips our speeds to -1 to 1
        motorFRadj = (Range.clip((motorFR) / norm, -maxSpeed, maxSpeed));
        motorBRadj = (Range.clip((motorBR) / norm, -maxSpeed, maxSpeed));
        MotorPower returnMotorPower = new MotorPower(motorBLadj, motorBRadj, motorFLadj, motorFRadj);
        return returnMotorPower;
    }

    private void WaitForInputDebug()
    {   // this code can be inserted into our master code to make it so the robot waits for you
        // to push a button (y) on the gamepad
        while(!gamepad1.y)
        {
            runtime.reset();
            while ((runtime.seconds() < 0.1)) {
                telemetry.update();
            }
        }

    }

}

