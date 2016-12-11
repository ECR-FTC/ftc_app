// this OpMode makes a puppy barking sound when you press the "a" button on the gamepad.
// you have to create the sound resource by inserting a sound file into the src/main/res/raw folder

package org.firstinspires.ftc.teamcode;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.util.Range;
import android.media.MediaPlayer; // include this class to play a sound file on the robot phone

@TeleOp(name="Telop Bark", group="Test")
@Disabled
public class triggerSoundTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        final MediaPlayer mp; // instantiate the object name
        // populate the MediaPlayer object. The resource should be in the res/raw folder
        // and should be named "puppybarking.wav"
        mp = MediaPlayer.create(hardwareMap.appContext, R.raw.puppybarking);
        waitForStart();

        while (opModeIsActive()) {

            // Use gamepad A to play the sound
            if (gamepad1.a){
                mp.start(); // this plays the sound
            }

            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
        mp.release();
    }
}
