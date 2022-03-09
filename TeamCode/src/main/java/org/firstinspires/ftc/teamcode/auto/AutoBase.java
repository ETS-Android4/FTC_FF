package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robot.ArmSubsystem;
import org.firstinspires.ftc.teamcode.robot.DriveTrainStrafeSimple;
import org.firstinspires.ftc.teamcode.robot.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.vision.FFObjectDector;

public class AutoBase extends LinearOpMode {
    FFObjectDector ffod = new FFObjectDector();
    DriveTrainStrafeSimple robot = new DriveTrainStrafeSimple();
    IntakeSubsystem intake = new IntakeSubsystem();
    ArmSubsystem arm = new ArmSubsystem();

    public int delayForOtherTeam = 1000;
    public int delaySmall = 1000;
    public Alliance alliance = Alliance.RED;

    public enum Alliance
    {
        RED,
        BLUE
    }

    @Override
    public void runOpMode() {}

    public void initAutoMode(){
        //init robot and vision detector
        robot.init(hardwareMap, this, "autonomous");
        ffod.init(hardwareMap, this, "autonomous");
        intake.init(hardwareMap, this, "autonomous");
        arm.init(hardwareMap, this, "autonomous");

        intake.platformDown();
    }

    public void storageSideLevel1(){
        // Move arm up and left/right
        arm.moveV(ArmSubsystem.LOW_POS);

        int pos = alliance == Alliance.RED ? ArmSubsystem.LEFT60_POS : ArmSubsystem.RIGHT60_POS;
        arm.moveH(pos);
        sleep(delayForOtherTeam);

        // 45 degree forward and strafe
        String dir = alliance == Alliance.RED ? "q2" : "q1";
        robot.encoderDriveStrafe45(0.5, 18, dir, 5);

        dir = alliance == Alliance.RED ? "left" : "right";
        robot.encoderDriveStrafe(0.5, 15, dir, 5);

        dumpCubeAndReturnArmToMiddle(0, false);

        sleep(delaySmall);
        runCarouselAndPark();
    }

    public void storageSideLevel2(){
        // Move arm up and left/right
        arm.moveV(ArmSubsystem.MID_POS);

        int pos = alliance == Alliance.RED ? ArmSubsystem.LEFT60_POS : ArmSubsystem.RIGHT60_POS;
        arm.moveH(pos);
        sleep(delayForOtherTeam);

        // 45 degree forward and strafe
        String dir = alliance == Alliance.RED ? "q2" : "q1";
        robot.encoderDriveStrafe45(0.5, 18, dir, 5);

        dir = alliance == Alliance.RED ? "left" : "right";
        robot.encoderDriveStrafe(0.5, 17, dir, 5);

        dumpCubeAndReturnArmToMiddle(0, false);

        // move to the same position as LEFT
        dir = alliance == Alliance.RED ? "right" : "left";
        robot.encoderDriveStrafe(0.2, 2, dir, 5);

        sleep(delaySmall);
        runCarouselAndPark();
    }

    public void storageSideLevel3(){
        // Move arm up and left/right
        arm.moveVToHighPosWait();
        sleep(500);

        int pos = alliance == Alliance.RED ? ArmSubsystem.LEFT60_POS : ArmSubsystem.RIGHT60_POS;
        arm.moveH(pos);
        sleep(delayForOtherTeam);

        // 45 degree forward and strafe
        String dir = alliance == Alliance.RED ? "q2" : "q1";
        robot.encoderDriveStrafe45(0.5, 19, dir, 5);

        dir = alliance == Alliance.RED ? "left" : "right";
        robot.encoderDriveStrafe(0.5, 21, dir, 5);

        dumpCubeAndReturnArmToMiddle(1500, true);

        // move to the same position as LEFT
        dir = alliance == Alliance.RED ? "right" : "left";
        robot.encoderDriveStrafe(0.2, 6, dir, 5);
        robot.encoderDrive(0.2,0.2,-1,-1,5);

        sleep(delaySmall);
        runCarouselAndPark();
    }

    public void warehouseSideLevel1(){
        // Move arm up and left/right
        arm.moveV(ArmSubsystem.LOW_POS);

        int pos = alliance == Alliance.RED ? ArmSubsystem.LEFT90_POS : ArmSubsystem.RIGHT90_POS;
        arm.moveH(pos);
        sleep(delayForOtherTeam);

        // 45 degree backward and strafe
        String dir = alliance == Alliance.RED ? "q3" : "q4";
        robot.encoderDriveStrafe45(0.5, 18, dir, 5);

        dir = alliance == Alliance.RED ? "left" : "right";
        robot.encoderDriveStrafe(0.5, 15, dir, 5);

        sleep(delaySmall);
        dumpCubeAndReturnArmToMiddle(0, false);

        dir = alliance == Alliance.RED ? "right" : "left";
        robot.encoderDriveStrafe(0.5, 14, dir, 5);

        sleep(delaySmall);
        runToWarehouse();
    }

    public void warehouseSideLevel2(){
        // Move arm up and left/right
        arm.moveV(ArmSubsystem.MID_POS);

        int pos = alliance == Alliance.RED ? ArmSubsystem.LEFT90_POS : ArmSubsystem.RIGHT90_POS;
        arm.moveH(pos);
        sleep(delayForOtherTeam);

        // 45 degree backward and strafe
        String dir = alliance == Alliance.RED ? "q3" : "q4";
        robot.encoderDriveStrafe45(0.5, 18, dir, 5);

        dir = alliance == Alliance.RED ? "left" : "right";
        robot.encoderDriveStrafe(0.5, 17, dir, 5);

        dumpCubeAndReturnArmToMiddle(0, false);

        dir = alliance == Alliance.RED ? "right" : "left";
        robot.encoderDriveStrafe(0.5, 17, dir, 5);

        sleep(delaySmall);
        runToWarehouse();
    }

    public void warehouseSideLevel3(){
        // Move arm up and left/right
        arm.moveVToHighPosWait();
        sleep(500);

        int pos = alliance == Alliance.RED ? ArmSubsystem.LEFT90_POS : ArmSubsystem.RIGHT90_POS;
        arm.moveH(pos);
        sleep(delayForOtherTeam);

        // 45 degree backward and strafe
        String dir = alliance == Alliance.RED ? "q3" : "q4";
        robot.encoderDriveStrafe45(0.5, 18, dir, 5);

        dir = alliance == Alliance.RED ? "left" : "right";
        robot.encoderDriveStrafe(0.5, 21, dir, 5);

        dumpCubeAndReturnArmToMiddle(1500, true);

        dir = alliance == Alliance.RED ? "right" : "left";
        robot.encoderDriveStrafe(0.5, 20, dir, 5);

        sleep(delaySmall);
        runToWarehouse();
    }

    public void dumpCubeAndReturnArmToMiddle(int lengthMS, boolean flipPlatformFirst){
        intake.dumpCube(lengthMS,flipPlatformFirst);
        sleep(1000);

        // move arm to the middle
        arm.moveH(ArmSubsystem.RESET_POS);
        arm.moveVToLowPosWait();
        intake.platformDown();
    }

    public void runCarouselAndPark(){

        // move back to carousel
        String dir = alliance == Alliance.RED ? "q4" : "q3";
        robot.encoderDriveStrafe45(0.5, 47, dir, 5);
        sleep(delaySmall);

        // turn 45
        int turnDistanceLeft  = alliance == Alliance.RED ? -12 : 12;
        int turnDistanceRight = alliance == Alliance.RED ? 12 : -12;
        robot.encoderDrive(0.3,0.3,turnDistanceLeft,turnDistanceRight,5);
        sleep(delaySmall);

        // slowly back to carousel
        robot.encoderDrive(0.2,0.2,-8,-8,5);
        // keep a very small backward pressure to make sure there is a good contact with the carousel
        robot.setDriveRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.setDrivePower(-0.05);

        // run carousel
        if(alliance == Alliance.RED) arm.startCarouselRed();
        else arm.startCarouselBlue();
        sleep(4000);
        arm.stopCarousel();

        robot.setDrivePower(0);
        robot.setDriveRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(delaySmall);

        // turn back 45
        turnDistanceLeft  = alliance == Alliance.RED ? 11 : -11;
        turnDistanceRight = alliance == Alliance.RED ? -11 : 11;
        robot.encoderDrive(0.2,0.2,turnDistanceLeft,turnDistanceRight,5);
        sleep(delaySmall);

        // park in storage
        dir = alliance == Alliance.RED ? "left" : "right";
        robot.encoderDriveStrafe(0.5, 22, dir, 5);
        sleep(delaySmall);

        robot.encoderDrive(0.2,0.2,-3,-3,5);
    }

    public void runToWarehouse(){
        String dir = alliance == Alliance.RED ? "right" : "left";
        robot.encoderDriveStrafe(0.15, 11, dir, 5); // wall align
        robot.encoderDrive(0.5,0.5,45,45,5);
    }
}
