package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.ArmSubsystem;
import org.firstinspires.ftc.teamcode.robot.DriveTrainStrafeSimple;
import org.firstinspires.ftc.teamcode.robot.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.vision.FFObjectDector;
import org.firstinspires.ftc.teamcode.vision.FFPipelineWebcam;

@Autonomous(name = "Red WareHouse", group = "Freight Frenzy")
//@Disabled
public class AutoRedWareHouseSide extends AutoBase {


    @Override
    public void runOpMode() {
        initAutoMode();
        alliance = Alliance.RED;
        delaySmall = 500;

        //wait for start
        waitForStart();

        sleep(1000);
        FFPipelineWebcam.FFPosition pos = ffod.getDetectedObject();

        // need to swizzle the position
        if (pos == FFPipelineWebcam.FFPosition.LEFT ) { // this is right (level3)
          warehouseSideLevel3();
        } else if (pos == FFPipelineWebcam.FFPosition.CENTER) { // this is left (level1)
            warehouseSideLevel1();
        } else { // this is center (level2)
            warehouseSideLevel2();
        }
    }
}