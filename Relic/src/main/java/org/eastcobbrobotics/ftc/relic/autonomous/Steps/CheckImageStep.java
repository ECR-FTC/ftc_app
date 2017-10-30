package org.eastcobbrobotics.ftc.relic.autonomous.Steps;

import org.eastcobbrobotics.ftc.ecrlib.steprunner.Step;
import org.eastcobbrobotics.ftc.ecrlib.steprunner.StepRobot;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by ECR-FTC on 10/30/2017.
 */

public class CheckImageStep extends Step {

    private VuforiaLocalizer vuforia;
    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicTemplate;

    @Override
    public void start(StepRobot r) {
        super.start(r);
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = "ATzOAID/////AAAAGWB+pRDolE/Mlr+59IMYtjx6LhM5Ct9clbf5okK+ie5MhZ7gTp7z0hdxcRP/DAzErKsfTg3Cz3JNZMUVM2LL5Aj5Nx3r0awwiSDS5/FRxdDurfddsF4wVzgzDyyIk3jIW3LQu96DVlcsGS2NzCcnclfft/kwfcQt6J5lGBbbWOp65h/cSopGehPckyTjrOUuIDQGQnrmqM+QjdL2eardbNfvQQ3/DGLHHsO4f/ZYXXHxahD4r6vCNBCW282upQVl8dflrEVcGaQ9G39MbBOJSsxpFsece0P+MsHoF6Y58GQDxBXQzRrNbP2OBU14lhSTb0mZBl52MLEhCZGgzWXgMkKronzDwp2g4QwVAngF8XzU";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        // Load images
        relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        relicTrackables.activate();
    }

    @Override
    public void run() {
        super.run();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        tell("%v Image Found", vuMark.ordinal());
        setFlag("imageFound", vuMark.ordinal());
    }
}
