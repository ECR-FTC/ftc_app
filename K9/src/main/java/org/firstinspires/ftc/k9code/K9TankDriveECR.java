/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.k9code;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;


//import org.firstinspires.ftc.robotcontroller.external.samples.HardwareK9bot;
//import org.firstinspires.ftc.team11096code.HardwareK9botECR;

/**
 * This OpMode uses the common HardwareK9bot class to define the devices on the robot.
 * All device access is managed through the HardwareK9bot class. (See this class for device names)
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a basic Tank Drive Teleop for the K9 bot
 * It raises and lowers the arm using the Gampad Y and A buttons respectively.
 * It also opens and closes the claw slowly using the X and B buttons.
 *
 * Note: the configuration of the servos is such that
 * as the arm servo approaches 0, the arm position moves up (away from the floor).
 * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="K9bot: Telop Tank ECR2", group="K9bot")
//@Disabled
public class K9TankDriveECR extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareK9botECR   robot        = new HardwareK9botECR();          // Use a K9'shardware
    double          armPosition     = HardwareK9botECR.ARM_HOME;       // Servo safe position
    double          clawPosition    = HardwareK9botECR.CLAW_HOME;      // Servo safe position
    final double    CLAW_SPEED      = 0.01;                            // sets rate to move servo
    final double    ARM_SPEED       = 0.01;                            // sets rate to move servo
    double          Speed           = 1.00;



    @Override
    public void runOpMode() throws InterruptedException {
        double left;
        double right;
        final MediaPlayer mp; // instantiate the object name
        // populate the MediaPlayer object. The resource should be in the res/raw folder
        // and should be named "puppybarking.wav"
        mp = MediaPlayer.create(hardwareMap.appContext, R.raw.puppybarking);

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        //telemetry.addData("Say", "Hello Driver");    //
        //telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in tank mode (note: The joystick goes negative when pushed forward, so negate it)
            // add /4 to slow down drive motors
            left = -gamepad1.left_stick_y/Speed;
            right = -gamepad1.right_stick_y/Speed;
            robot.leftMotor.setPower(left);
            robot.rightMotor.setPower(right);
            if (robot.touchR.isPressed()){
                robot.leftMotor.setPower(0);
                robot.rightMotor.setPower(0);
            }

            // Use gamepad Y & A raise and lower the arm
            if (gamepad1.a)
                armPosition += ARM_SPEED;
            else if (gamepad1.y)
                armPosition -= ARM_SPEED;

            // Use gamepad X & B to open and close the claw
            if (gamepad1.x)
              clawPosition += CLAW_SPEED;
            else if (gamepad1.b)
              clawPosition -= CLAW_SPEED;

            // Move both servos to new position.
            armPosition  = Range.clip(armPosition, HardwareK9botECR.ARM_MIN_RANGE, HardwareK9botECR.ARM_MAX_RANGE);
            robot.arm.setPosition(armPosition);
            clawPosition = Range.clip(clawPosition, HardwareK9botECR.CLAW_MIN_RANGE, HardwareK9botECR.CLAW_MAX_RANGE);
            robot.claw.setPosition(clawPosition);

            if (gamepad1.left_bumper){
                mp.start(); // this plays the sound
                robot.LED2.enable(true); // turn on LED
            }
            if (gamepad1.right_bumper){
                mp.start(); // this plays the sound
                robot.LED2.enable(false); // turn off LED
            }

            //change speed
/*            if (gamepad1.dpad_up)
            {
                Speed = Speed + 0.01;
            }
            if (gamepad1.dpad_down)
            {
                Speed = Speed + 0.01;
            }
*/

            // Send telemetry message to signify robot running;
//            telemetry.addData("isPressed",String.valueOf(robot.touchR.isPressed()));
            telemetry.addData("arm",   "%.2f", armPosition);
            telemetry.addData("claw",  "%.2f", clawPosition);
            telemetry.addData("left",  "%.2f", left);
            telemetry.addData("right", "%.2f", right);
//            telemetry.addData("touch", "%b", robot.touchR.isPressed());
            telemetry.addData("logLin1", "%.3f", robot.logLin1.getVoltage());
            telemetry.addData("4. h", "%03d", robot.gyro.getHeading());
//            telemetry.addData("speed = speed /", Speed);
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
        robot.LED2.enable(false);
    }
}
