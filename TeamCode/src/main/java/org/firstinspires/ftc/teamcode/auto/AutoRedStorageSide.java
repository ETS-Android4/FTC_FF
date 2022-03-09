package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.ArmSubsystem;
import org.firstinspires.ftc.teamcode.robot.DriveTrainStrafeSimple;
import org.firstinspires.ftc.teamcode.robot.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.vision.FFObjectDector;
import org.firstinspires.ftc.teamcode.vision.FFPipelineWebcam;

@Autonomous(name = "Red Storage", group = "Freight Frenzy")
//@Disabled
public class AutoRedStorageSide extends AutoBase {

    @Override
    public void runOpMode() {
        initAutoMode();
        alliance = Alliance.RED;
        delaySmall = 500;

        //wait for start
        waitForStart();

        sleep(1000);
        FFPipelineWebcam.FFPosition pos = ffod.getDetectedObject();

        if (pos == FFPipelineWebcam.FFPosition.LEFT ) {  // level1
            storageSideLevel1();
        } else if (pos == FFPipelineWebcam.FFPosition.CENTER) { // level2
           storageSideLevel2();
        } else {    // level3
            storageSideLevel3();
        }
    }
}