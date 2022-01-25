package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.ArmSubsystem;
import org.firstinspires.ftc.teamcode.robot.DriveTrainStrafeSimple;
import org.firstinspires.ftc.teamcode.robot.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.vision.FFObjectDector;
import org.firstinspires.ftc.teamcode.vision.FFPipelineWebcam;

@Autonomous(name = "RedLeft", group = "Freight Frenzy")
//@Disabled
public class AutoRedLeft extends LinearOpMode {

    FFObjectDector ffod = new FFObjectDector();
    DriveTrainStrafeSimple robot = new DriveTrainStrafeSimple();
    IntakeSubsystem intake = new IntakeSubsystem();
    ArmSubsystem arm = new ArmSubsystem();

    public int delayMS = 5000;

    @Override
    public void runOpMode() {
        //init robot and vision detector
        robot.init(hardwareMap, this, "autonomous");
        ffod.init(hardwareMap, this, "autonomous");
        intake.init(hardwareMap, this, "autonomous");
        arm.init(hardwareMap, this, "autonomous");

        //wait for start
        waitForStart();

        FFPipelineWebcam.FFPosition pos = ffod.getDetectedObject();

        if (pos == FFPipelineWebcam.FFPosition.LEFT ) {
            arm.moveV(ArmSubsystem.LOW_POS);
            arm.moveH(ArmSubsystem.LEFT60_POS);
            sleep(delayMS);
            robot.encoderDriveStrafe45(0.5, 18, "q2", 5);
            robot.encoderDriveStrafe(0.5, 15, "left", 5);

            intake.platformUP();

            // move to carousel
            robot.encoderDriveStrafe45(0.5, 18, "q4", 5);

        } else if (pos == FFPipelineWebcam.FFPosition.CENTER) {
            arm.moveV(ArmSubsystem.MID_POS);
            arm.moveH(ArmSubsystem.LEFT60_POS);
            sleep(delayMS);
            robot.encoderDriveStrafe45(0.5, 18, "q2", 5);
            robot.encoderDriveStrafe(0.5, 17, "left", 5);

            intake.platformUP();

        } else {
            arm.moveV(ArmSubsystem.HIGH_POS);
            arm.moveH(ArmSubsystem.LEFT60_POS);
            sleep(delayMS);
            robot.encoderDriveStrafe45(0.5, 18, "q2", 5);
            robot.encoderDriveStrafe(0.5, 20, "left", 5);

            intake.platformUP();
            intake.turnOnOut();
        }

        telemetry.addData("Object Pos:", pos);
        telemetry.update();

        sleep(delayMS);

    }
}