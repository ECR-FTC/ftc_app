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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static java.lang.Math.abs;
import static java.lang.Math.max;

// code for custom sensors
//telemetry.addData("rightRed", "%.3f", robot.rightRedSensor.getVoltage());
//telemetry.addData("rightBlue", "%.3f", robot.rightBlueSensor.getVoltage());

@Autonomous(name="BeaconsRed", group="Linear Opmode")  // @Autonomous(...) is the other common choice
//@Disabled
public class Build2Auto3 extends LinearOpMode {
        HardwareBuild2 robot;
        private double ENCODER_PORT_RESET;
        DcMotor motorFrontRight;
        DcMotor motorFrontLeft;
        DcMotor motorBackRight;
        DcMotor motorBackLeft;
        double color = 0;
        private ElapsedTime runtime = new ElapsedTime();
        // here we declare 2 things, the hardware map, and an elapsed time function
        boolean lineSeen;

        @Override
        public void runOpMode() throws InterruptedException {
            robot = new HardwareBuild2();          // Use Build2's Hardware file


            try {
                robot.init(hardwareMap);
            } catch (InterruptedException e) {
            }
            // we call the hardware map here
            motorFrontRight = hardwareMap.dcMotor.get("frontRight");
            motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
            motorBackRight = hardwareMap.dcMotor.get("backRight");
            motorBackLeft = hardwareMap.dcMotor.get("backLeft");

            motorFrontRight.setDirection(DcMotor.Direction.REVERSE);
            motorBackRight.setDirection(DcMotor.Direction.REVERSE);

            // Set all motors to run without encoders.
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motorBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // Set all motors to zero power
            motorFrontLeft.setPower(0);
            motorFrontRight.setPower(0);
            motorBackRight.setPower(0);
            motorBackLeft.setPower(0);

            resetEncoderValue();

            telemetry.addData("Status", "Waiting for Start");
            telemetry.update();

            waitForStart();   // we set our servos in init and wait for start (the driver hitting play)

            //this is a sample autonomous where the robot shoots 2 balls and pushes one beacon.
            // this is a red code

            /* Old Full Code
            autoFire(true, false); // spin up motor and shoot 1st ball
            delayLoop(4.0);
            autoFire(false, true); // shoot second ball
            autoGyroTurn(42);
            autoGoOneTileForward(-2.3);
            autoGyroTurn(-20);
            autoGoOneTileForward(-.2);
            autoGyroTurn(-25);
            autoDrive(0, (float) -1.0, 0, 800); // push into wall
            autoGoToWhiteLine(1000, true, false);
            autoBeacon(false);
            autoGoOneTileForward(-1.0);
            autoDrive(0, (float) -1.0, 0, 800); // push into wall
            autoGoToWhiteLine(2000, true, false);
            autoBeacon(false);
            */
            autoGoOneTileForward(-0.8);

            autoFire();
            autoFire();
            robot.motorShoot.setPower(0.00);

            autoGyroTurn(-45);
            autoGoOneTileForward(-2.9);
            autoGyroTurn(45);
            autoDrive(0, (float)0.5, 0, 1000);

            autoGoToWhiteLine(3.5, 0.2);
            color = detectColor();
            if(color == 1) //red
            {
                pushBeacon();
                delayLoop(1);
                pushBeacon();
            }
            else if (color == 2)
            {
                autoGoOneTileForward(-0.2);

                color = detectColor();
                if(color == 1) {
                    pushBeacon();
                    delayLoop(1);
                    pushBeacon();
                }
            }
/*
            while(opModeIsActive())
            {
                telemetry.addData("Red side, Red Sensor:", "%.3f", robot.rightRedSensor.getVoltage());
                telemetry.addData("Red side, Blue Sensor:", "%.3f", robot.rightBlueSensor.getVoltage());
                telemetry.addData("returnValue", "%.3f", color);
                if(color == 0)
                {
                    telemetry.addData("I see nothing", 0);
                }
                if(color == 1)
                {
                    telemetry.addData("I see red", 1);
                }
                if(color == 2)
                {
                    telemetry.addData("I see blue", 2);
                }
                if(color == 3)
                {
                    telemetry.addData("I see both", 3);
                }
                telemetry.update();
            }
            */
         }

    //
    public void autoGoToWhiteLine (double waitFor, double speed) // when this code is called, the robot goes to the white line and stops
    {
        runtime.reset(); // this is called allot, it resets 'runtime' to 0

        while((robot.ODS.getRawLightDetected() < 0.2) & (runtime.seconds() < waitFor) & opModeIsActive())
        {
            motorBackRight.setPower(speed);
            motorBackLeft.setPower(speed);
            motorFrontLeft.setPower(speed);
            motorFrontRight.setPower(speed);
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
    public void autoGoOneTileForward(double tiles)
    {
        float portTarget;
        double speedPort, speedStar;
        float ENCODER_ACCEL_CONST = 500;
        //double ENCODER_PORT_RESET = motorFrontLeft.getCurrentPosition();
        portTarget = (float)tiles * robot.GO_ONE_TILE_PORT;
        //starTarget = (float)currentParameter * GO_ONE_TILE_STAR;

        resetEncoderValue();

        while ((Math.abs(returnEncoderValue()) < Math.abs(portTarget) - 5) & opModeIsActive())// ||
        {
            // speed is proportional to number of encoder steps away from target
            speedPort = 0.5 / ENCODER_ACCEL_CONST * (portTarget - returnEncoderValue());
            // don't go faster than SPEED_NOMINAL
            speedPort = Math.signum(speedPort) * Math.min(Math.abs(speedPort), robot.driveMaxPower);
            // don't go slower than SPEED_MIN
            speedPort = Math.signum(speedPort) * Math.max(Math.abs(speedPort), robot.driveMinPower);

            // set the motor speeds
            motorFrontRight.setPower(speedPort);
            motorFrontLeft.setPower(speedPort);
            motorBackRight.setPower(speedPort);
            motorBackLeft.setPower(speedPort);

            telemetry.addData("Encoder Value:", returnEncoderValue());
            telemetry.update();
        }
        driveMotorStop();
    }

    public void autoGyroTurn (int degrees) {
        int heading;
        int headingTolerance = 5;
        double speedPort;
        robot.gyro.resetZAxisIntegrator();
        heading = robot.gyro.getIntegratedZValue(); // getIntegratedZValue() works for any turn angle

        degrees = -degrees;
        while ( (Math.abs(heading) < (Math.abs(degrees)-headingTolerance)) && opModeIsActive())
        {
            // speed is proportional to angle away from target
            speedPort = (float)(.5 / 15 * (degrees - heading));
            // don't go faster than SPEED_NOMINAL
            speedPort = Math.signum(speedPort) * Math.min(Math.abs(speedPort), .5);
            // don't go slower than SPEED_MIN
            speedPort = Math.signum(speedPort) * Math.max(Math.abs(speedPort), .2);

            // set the speeds
            motorBackLeft.setPower(-speedPort);
            motorFrontLeft.setPower(-speedPort);

            motorBackRight.setPower(speedPort);
            motorFrontRight.setPower(speedPort);

            heading = robot.gyro.getIntegratedZValue(); // getIntegratedZValue() works for any turn angle
            telemetry.addData("Gyro Value:", heading);
            telemetry.addData("Gyro_target:", degrees);
            telemetry.update();

        }
        driveMotorStop();
    }
    public int detectColor()
    {
        robot.rightServo.setPosition((robot.rightPress + robot.rightStore) / 2);
        robot.leftServo.setPosition((robot.leftPress + robot.leftStore) / 2);
        delayLoop(0.5);
        int returnValue = 0;
        double measurementRed = 0.0;
        double measurementBlue = 0.0;
        int n = 0;
        for(n = 0; n < 10; n++)
        {
            measurementRed += robot.rightRedSensor.getVoltage();
            measurementBlue += robot.rightBlueSensor.getVoltage();
        }
        measurementBlue = measurementBlue / 10.0;
        measurementRed = measurementRed / 10.0;
        if (measurementBlue > robot.colorThreshold)
        {
            returnValue += 2;
        }
        if (measurementRed > robot.colorThreshold)
        {
            returnValue += 1;
        }
        return returnValue;
    }
    public void pushBeacon()
    {
        runtime.reset();
        while ((runtime.seconds() < 0.5)) {
            robot.rightServo.setPosition(robot.rightPress);
            robot.leftServo.setPosition(robot.leftPress);
        }
        // put the servos back when we are done
        robot.rightServo.setPosition(robot.rightStore);
        robot.leftServo.setPosition(robot.leftStore);
    }
    public void driveAlongWall(boolean isRed)
    {
        double speed = .20, sideSpeed = .5;
        runtime.reset();
        while ((runtime.seconds() < 0.4)) {
        if (isRed)
            {
            motorBackLeft.setPower(speed + sideSpeed);
            motorBackRight.setPower(speed - sideSpeed);
            motorFrontLeft.setPower(speed + sideSpeed);
            motorFrontRight.setPower(speed - sideSpeed);
            }
        if (!isRed)
            {
            motorBackLeft.setPower(speed - sideSpeed);
            motorBackRight.setPower(speed + sideSpeed);
            motorFrontLeft.setPower(speed - sideSpeed);
            motorFrontRight.setPower(speed + sideSpeed);
            }
        }
    }



    public void autoBeacon (boolean isAllianceRed) { //this will (when called) tell the robot to push the beacon
        // our color sensor is in front of the servo
        boolean beaconFound = false;
        int seeWhat;
        seeWhat = detectColor();
        if (isAllianceRed) {// red alliance

        if (seeWhat == 0) { //no color

        }
        if (seeWhat == 1) {//red color
            pushBeacon();
        }
        if (seeWhat == -1) {//blue color
            driveAlongWall(isAllianceRed);

            if (seeWhat == 0) {

            }
            if (seeWhat == 1) {
                pushBeacon();
            }
        }
        }
        if(!isAllianceRed)
            if (seeWhat == 0) {

            }
        if (seeWhat == -1) {
            pushBeacon();
        }
        if (seeWhat == 1) {
            driveAlongWall(isAllianceRed);

            if (seeWhat == 0) {

            }
            if (seeWhat == 1) {
                pushBeacon();
            }
        }
    }

    // autoFire(): this code fires the particles
    // input arguments:
    //      motorSpinUp: boolean, set to true to spin up the motor from stop
    //          set false to shoot without spinning up the motor
    //      motorTurnOff: boolean, set to true to turn off the motor at the end
    //          set false to leave motor on
    // output arguments: none

    public void autoFire() {
        robot.motorShoot.setPower(0.4);

        delayLoop(4);
        robot.fireServo.setPosition(robot.fireGo);// the servo is retracted after firing here.
        delayLoop(2 * robot.fireServoTime);
        robot.fireServo.setPosition(robot.fireStay);

    }


    public void autoDrive(float foward, float left, float turn, long waitFor){
        // this code runs the robot in all directions using a combination of forward, left, and turn
        // combined with a time. It runs off of time.
        float a, b, c;
        // holonomic tank drive

        a = foward;
        b = left;
        c = turn;//change variables into smaller ones

        MotorPower returnMotorPower; // we call another program here to determine our motor powwers for us
        returnMotorPower = motorScale(a - b - c, a + b - c, a + b + c, a - b + c, (float) 1.0);

        // write the values to the motors
        motorBackRight.setPower(returnMotorPower.getMotorBackRight());
        motorBackLeft.setPower(returnMotorPower.getMotorBackLeft());
        motorFrontLeft.setPower(returnMotorPower.getMotorFrontLeft());
        motorFrontRight.setPower(returnMotorPower.getMotorFrontRight());

        delayLoop(waitFor/1000.0);

        driveMotorStop();
        delayLoop(0.25);

    }

    public void driveMotorStop()
    { // this stops our motors to make it easier to write (one line instead of 4)
        motorBackRight.setPower(0.0);
        motorBackLeft.setPower(0.0);
        motorFrontLeft.setPower(0.0);
        motorFrontRight.setPower(0.0);
    }

    public void delayLoop(double delayTime) {
        runtime.reset();
        while ((runtime.seconds() < delayTime) && opModeIsActive()) {
            idle();
        }
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

    public double returnEncoderValue(){

        return motorFrontLeft.getCurrentPosition() - ENCODER_PORT_RESET;
    }

    public void resetEncoderValue(){
        ENCODER_PORT_RESET = motorFrontLeft.getCurrentPosition();
    }


}

