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

        @Override
        public void runOpMode() throws InterruptedException {
            robot = new HardwareBuild2();          // Use Build2's Hardware file

            try
            {
                robot.init(hardwareMap);
            }
            catch (InterruptedException e) {
            }
            // we call the hardware map here

            telemetry.addData("Status", "Initialized");
            telemetry.update();

        /* eg: Initialize the hardware variables. Note that the strings used here as parameters
         * to 'get' must correspond to the names assigned during the robot configuration
         * step (using the FTC Robot Controller app on the phone).
         */

            robot.rightServo.setPosition(.7);
            robot.leftServo.setPosition(.3);
            waitForStart();   // we set our servos in init and wait for start (the driver hitting play)

            //this is a sample autonomous where the robot shoots 2 balls and pushes both beacons.
            // this is a blue code
             autoFire(true); // spin up motor and shoot 1st ball
             autoFire(false); // shoot second ball
             autoDrive(-1,0,0,1500);
             autoDrive(0,0,1,1500);
             autoDrive(-1,0,0,1500);
             autoDrive(0,0,-1,1500); // rotate parallel to wall
             autoDrive(0,(float)0.5,0,1000); // push into wall
             autoGoToWhiteLine(1000);
             autoBeacon();
             autoGoToWhiteLine(2000);
             autoBeacon();

//           idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }

    // TODO: put in sideways drift: will need an argument to pick alliance color
    // TODO: return false if we do not find a white line
    //
    public void autoGoToWhiteLine (double waitFor) // when this code is called, the robot goes to the white line and stops
    {
        runtime.reset(); // this is called allot, it resets 'runtime' to 0

        while((robot.ODS.getRawLightDetected() < 0.2) & (runtime.seconds() < waitFor) ) {
            robot.motorBackRight.setPower(.2);
            robot.motorBackLeft.setPower(.2);
            robot.motorFrontLeft.setPower(.2);
            robot.motorFrontRight.setPower(.2);
            telemetry.addData("No white line:", robot.ODS.getRawLightDetected());
            // if the robot doesn't see the line, and still has time, it goes forward.
        }

        driveMotorStop();

        telemetry.addData("I see the line:", robot.ODS.getRawLightDetected());
        telemetry.update();
        // if the robot does see the line, it stops.

    }

    // TODO: write with argument that lets you pick red or blue function
    // TODO: adjust so the robot only pushes a button if it sees a color. Return false
    //       if neither color is identified
    public void autoBeacon () { //this will (when called) tell the robot to push the beacon
        // our color sensor is in front of the servo

        // if we see our color (red) we drive forward, if we don't, we do nothing
        if ((robot.colorR.red() > 2)) {
            while ((runtime.seconds() < .5)) {
                robot.motorBackRight.setPower(-.2);
                robot.motorBackLeft.setPower(-.2);
                robot.motorFrontLeft.setPower(-.2);
                robot.motorFrontRight.setPower(-.2);
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
    // TODO: need an argument to conditionally turn off the motor when done.

    public void autoFire(boolean motorSpinUp) {
        if (motorSpinUp) {//spins up the motor at full speed for 2 seconds ro make it faster.
            runtime.reset();// unless we tell it not to (eg: autofire(false);)
            while ((runtime.seconds() < 1.90)) {
                robot.motorShoot.setPower(1.00);
            }
        }
        robot.motorShoot.setPower(.30);//this sets the motor to firing speed
        runtime.reset();
        while ((runtime.seconds() < 0.50)) {
            robot.fireServo.setPosition(0.7);// and we fire it here
        }
        runtime.reset();
        while ((runtime.seconds()) < 0.50) {
            robot.fireServo.setPosition(0.0);// the servo is retracted after firing here.
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
        while ((runtime.seconds() < 0.1)) {
            telemetry.update();
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

