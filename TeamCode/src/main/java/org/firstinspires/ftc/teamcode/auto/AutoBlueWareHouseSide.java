package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.ArmSubsystem;
import org.firstinspires.ftc.teamcode.robot.DriveTrainStrafeSimple;
import org.firstinspires.ftc.teamcode.robot.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.vision.FFObjectDector;
import org.firstinspires.ftc.teamcode.vision.FFPipelineWebcam;

@Autonomous(name = "Blue WareHouse", group = "Freight Frenzy")
//@Disabled
public class AutoBlueWareHouseSide extends AutoBase {


    @Override
    public void runOpMode() {
        initAutoMode();
        alliance = Alliance.BLUE;
        delaySmall = 500;

        //wait for start
        waitForStart();

        sleep(1000);
        FFPipelineWebcam.FFPosition pos = ffod.getDetectedObject();

        //  swizzle the position (because the camera is up-side down)
        if (pos == FFPipelineWebcam.FFPosition.LEFT ) {
            warehouseSideLevel1();
        } else if (pos == FFPipelineWebcam.FFPosition.CENTER) {
            warehouseSideLevel3();
        } else {
            warehouseSideLevel2();

        }
    }
}