package org.firstinspires.ftc.team11096code;

//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.util.Range;
import android.media.MediaPlayer;

@TeleOp(name="K9bot: Telop Bark", group="K9bot")
//@Disabled
public class triggerSoundTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        final MediaPlayer mp;
        mp = MediaPlayer.create(hardwareMap.appContext, R.raw.puppybarking);
        waitForStart();

        while (opModeIsActive()) {

            // Use gamepad Y & A raise and lower the arm
            if (gamepad1.a){
                mp.start();
            }

            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
        mp.release();
    }
}
