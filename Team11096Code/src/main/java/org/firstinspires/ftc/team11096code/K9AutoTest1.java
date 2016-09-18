// K9 auto test 1
// adapt 2015-2016 auto code to the K9 robot.
// 

package org.firstinspires.ftc.team11096code;

/*
instructions                           parameter
'straight' drive Straight encoder      distance in tiles
'gyro'     gyro turn                   z angle to turn
'touch'    drive straight touch        direction, motor speed scaling
'lift'     lift the scoop              motor speed
'climber'  climber drop                not used
'wait'     wait                        not used
'end'      end                         not used
'timeGo'   drive straight timed        motor speed
'reset'    reset encoders              not used
*/

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
//import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import java.lang.System;


/*
 * This is autonomous LinearOpMode
 */

@Autonomous(name="K9: Auto Test 1", group="K9")
//@Disabled
public class K9AutoTest1 extends LinearOpMode {

  final static float MOTOR_POWER = (float) 0.60;
  final static float MOTOR_POWER_TURN = (float) 0.3;
  final static float SPEED_MIN = (float) 0.06; // min motor speed, straight drive
  final static float SPEED_MIN_TURN = (float) 0.08; // min motor speed, turning
  final static int ENCODER_TOLERANCE = 40;
  final static int ENCODER_ACCEL_CONST = 500;
  final static int GO_ONE_TILE_PORT = 3375;
  //final static int GO_ONE_TILE_STAR = 3375;
  final static float GYRO_ACCEL_CONST = 15;
  final static int HEADING_TOLERANCE = 3;

  Servo servoPeople, servoRedChute, servoBlueChute;
  //ModernRoboticsI2cGyro gyroSensor;
  TouchSensor portTouchSensor, starBoardTouchSensor;
  long startTime;

  @Override
  public void runOpMode() throws InterruptedException {
      HardwareK9botECR   robot        = new HardwareK9botECR();          // Use a K9's hardware

      //String[] autoInstructions = {"wait","straight","wait","gyro", "straight",  "gyro","lift", "wait","lift","wait", "lift","touch","climber", "end"};
    //double[] autoParameter =    {    0 ,         2,     0,    45,        1.8,     45 ,  -0.25,      0, -0.25,     0,    0.3,   0.35,        1,     0};
    //double[] autoTimeLimit =    { 1000 ,      4000,  6000,  3000,       3600,    3000,    400,    400,   450,   400,    600,   4000,     2000,     0};
     // telemetry.addData("Say", "Hello Driver -1");
     // telemetry.update();
     // wait(1000);

      String[] autoInstructions = {"straight", "end"};
      double[] autoParameter =    {         2,     0};
      double[] autoTimeLimit =    {      4000,     0};

      telemetry.addData("Say", "Hello Driver 0");
      telemetry.update();
      wait(1000);

      String currentInstruction;
      int instructionIndex = 0;
      double currentParameter;
      double currentTimeLimit;
      int numberOfInstructions;
      boolean doNextInstruction = false;
      boolean Terminate = false;

      int heading;
      float speedPort, speedStar;
      float portTarget;//, starTarget;
//      double peopleDeploy = 1;
    //  double peopleStore  = 0;

      int ENCODER_PORT_RESET;
      int ENCODER_STAR_RESET;

      //telemetry.addData("Say", "Hello Driver 1");
      //telemetry.update();
      wait(1000);

      // write some device information (connection info, name and type)
      // to the log file.
      hardwareMap.logDevices();
      // get a reference to our GyroSensor object.
//      gyroSensor = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyroSensor");

      // calibrate the gyro.
//      gyroSensor.calibrate();

      // initialize the instructions
      numberOfInstructions = autoInstructions.length;
      currentInstruction = autoInstructions[0];
      currentParameter = autoParameter[0];
      currentTimeLimit = autoTimeLimit[0];

      // reset encoders
      ENCODER_PORT_RESET = robot.leftMotor.getCurrentPosition();
      ENCODER_STAR_RESET = robot.rightMotor.getCurrentPosition();

      // reset gyro heading.
  //    gyroSensor.resetZAxisIntegrator();

     // servoBlueChute.setPosition(SERVO_BLUE_CHUTE_HOLD);
      //servoRedChute.setPosition(SERVO_RED_CHUTE_HOLD);
      //servoPeople.setPosition(peopleStore);

  //    telemetry.addData("Say", "Hello Driver 2");
    //  telemetry.update();
      wait(1000);

      // wait for the start button to be pressed.
      waitForStart();

      // make sure the gyro is done calibrating
    /*  while (gyroSensor.isCalibrating())  {
          Thread.sleep(50);
      }*/
      startTime = System.currentTimeMillis();
      while (opModeIsActive() && (!Terminate))  {

          switch (currentInstruction) {
              case "straight":      //  drive straight using encoders
                  telemetry.addData("Text", "Straight");
                  portTarget = (float)currentParameter * GO_ONE_TILE_PORT;
                  //starTarget = (float)currentParameter * GO_ONE_TILE_STAR;

                  if ((Math.abs(robot.leftMotor.getCurrentPosition()-ENCODER_PORT_RESET) < Math.abs(portTarget) - ENCODER_TOLERANCE) )// ||
                  //      (Math.abs(robot.rightMotor.getCurrentPosition()-ENCODER_STAR_RESET) < Math.abs(starTarget) - ENCODER_TOLERANCE))
                  // disabled starboard encoder since the two were not in sync
                  {
                      // speed is proportional to number of encoder steps away from target
                      speedPort = MOTOR_POWER / ENCODER_ACCEL_CONST * (portTarget - (robot.leftMotor.getCurrentPosition()-ENCODER_PORT_RESET));
                      speedStar = speedPort;
                      //speedStar = MOTOR_POWER / ENCODER_ACCEL_CONST * (starTarget - (robot.rightMotor.getCurrentPosition()-ENCODER_STAR_RESET));

                      // don't go faster than SPEED_NOMINAL
                      speedPort = Math.signum(speedPort) * Math.min(Math.abs(speedPort), MOTOR_POWER);
                      speedStar = Math.signum(speedStar) * Math.min(Math.abs(speedStar), MOTOR_POWER);

                      // don't go slower than SPEED_MIN
                      speedPort = Math.signum(speedPort) * Math.max(Math.abs(speedPort), SPEED_MIN);
                      speedStar = Math.signum(speedStar) * Math.max(Math.abs(speedStar), SPEED_MIN);

                      // set the motor speeds
                      robot.leftMotor.setPower(speedStar);
                      robot.rightMotor.setPower(speedPort);
                  }
                  else{
                      robot.rightMotor.setPower(0.0);
                      //motorStarboardRear.setPower(0.0);
                      robot.leftMotor.setPower(0.0);
                      //motorPortRear.setPower(0.0);
                      doNextInstruction=true;
                  }
                  break;
/*              case "gyro":      //  gyro turn
                  telemetry.addData("Text", "Gyro");
                  heading = gyroSensor.getIntegratedZValue(); // getIntegratedZValue() works for any turn angle
                  if ( (Math.abs(heading) < (Math.abs(currentParameter)-HEADING_TOLERANCE)) && (elapsedTime() < currentTimeLimit))
//                    if ( (Math.abs(heading) < (Math.abs(currentParameter)-HEADING_TOLERANCE)) )
                  {
                      // speed is proportional to angle away from target
                      speedPort = (float)(MOTOR_POWER / GYRO_ACCEL_CONST * (currentParameter - heading));
                      // don't go faster than SPEED_NOMINAL
                      speedPort = Math.signum(speedPort) * Math.min(Math.abs(speedPort), MOTOR_POWER_TURN);
                      // don't go slower than SPEED_MIN
                      speedPort = Math.signum(speedPort) * Math.max(Math.abs(speedPort), SPEED_MIN_TURN);

                      // set the speeds
                      robot.rightMotor.setPower(-speedPort);
                      //motorStarboardRear.setPower(-speedPort);
                      robot.leftMotor.setPower(speedPort);
                      //motorPortRear.setPower(speedPort);
                  }
                  else{
                      robot.rightMotor.setPower(0.0);
                      //motorStarboardRear.setPower(0.0);
                      robot.leftMotor.setPower(0.0);
                     // motorPortRear.setPower(0.0);
                      Thread.sleep(50);
                      doNextInstruction = true;
                  }
                  break;
  */            case "touch":	   //  go straight using touch sensor
                  telemetry.addData("Text", "Touch");
                  if(portTouchSensor.isPressed () || starBoardTouchSensor.isPressed()) {
                      driveStop();
                      Thread.sleep(200); // give time to stop motors
                      doNextInstruction = true;
                  }
                  else {
                      //drive
                      robot.rightMotor.setPower(currentParameter*MOTOR_POWER);
                      //motorStarboardRear.setPower(currentParameter*MOTOR_POWER);
                      robot.leftMotor.setPower(currentParameter*MOTOR_POWER);
                      //motorPortRear.setPower(currentParameter*MOTOR_POWER);
                  }
                  if(elapsedTime()>=currentTimeLimit)
                  {
                      driveStop();
                      doNextInstruction=true;
                  }
                  break;
      /*        case "lift":      //  raise and lower the scoop
                  motorScore.setPower(currentParameter);
                  if(elapsedTime()>=currentTimeLimit)
                  {
                      doNextInstruction = true;
                      motorScore.setPower(0);
                  }
                  break;
              case "climber":      //  climber in bin
                  telemetry.addData("Text", "Climber");
                  servoPeople.setPosition(peopleDeploy);
                  Thread.sleep(1050); // give time to stop
                  doNextInstruction = true;
        */  //        break;
              case "resetEncoder":      //  reset encoder
                  telemetry.addData("Text", "Reset Encoder");
                  ENCODER_PORT_RESET = robot.leftMotor.getCurrentPosition();
                  ENCODER_STAR_RESET = robot.rightMotor.getCurrentPosition();
                  doNextInstruction = true;
                  break;
              case "timeGo":      //  reset encoder
                  telemetry.addData("Text", "Time Go");
                  //motorSt.setPower(currentParameter);
                  robot.rightMotor.setPower(currentParameter);
                  robot.leftMotor.setPower(currentParameter);
                  //robot.leftMotor.setPower(currentParameter);
                  if(elapsedTime()>=currentTimeLimit)
                  {
                      driveStop();
                      doNextInstruction = true;
                  }
                  break;
              case "wait":      //  wait
                  telemetry.addData("Text", "Waiting");
                  if(elapsedTime()>=currentTimeLimit)
                  {
                      doNextInstruction = true;
                  }
                  driveStop(); // added for failproof stopping
                  break;
              case "end":      //  end
                  telemetry.addData("Text", "End");
                  Terminate = true;
                  break;
              default:       //  if instructions don't work, or the code is done, we wait for the rest of eternity, or not...
                  telemetry.addData("Text", "Bad instruction");
                  Terminate = true;
                  break;
          }

          if(doNextInstruction)
          {
              instructionIndex++;
              if(instructionIndex>=numberOfInstructions)
              {// catch if the last instruction just finished
                  Terminate = true;
              }
              else {
                  // load next instruction
                  currentParameter = autoParameter[instructionIndex];
                  currentInstruction = autoInstructions[instructionIndex];
                  currentTimeLimit = autoTimeLimit[instructionIndex];
                  // reset encoders
                  ENCODER_PORT_RESET = robot.leftMotor.getCurrentPosition();
                  ENCODER_STAR_RESET = robot.rightMotor.getCurrentPosition();
                  // reset gyro heading. UNTESTED
                 // gyroSensor.resetZAxisIntegrator();
                  // reset start time
                  startTime = System.currentTimeMillis();
              }
              doNextInstruction = false; // reset doNextInstruction for the next step
          }

         // telemetry.addData("gyro rotation ", gyroSensor.getIntegratedZValue());
          telemetry.addData("Port encoder ", robot.leftMotor.getCurrentPosition()-ENCODER_PORT_RESET);
          telemetry.addData("Star encoder ", robot.rightMotor.getCurrentPosition() - ENCODER_STAR_RESET);
          telemetry.update();
          // may need something here // waitOneFullHardwareCycle();
      }
  }

    private void driveStop() {

        //robot.leftMotor.setPower(0);
        //motorPortRear.setPower(0);
        //robot.rightMotor.setPower(0);
        //motorStarboardRear.setPower(0);
    }

    private long elapsedTime() {

        return System.currentTimeMillis() - startTime;
    }

}
