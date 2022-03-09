package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TestBackToCarousel", group = "Freight Frenzy")
//@Disabled
public class TestBackToCarousel extends LinearOpMode {
    DriveTrainStrafeSimple robot = new DriveTrainStrafeSimple();
    ArmSubsystem arm = new ArmSubsystem();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, this, "teleop");
        arm.init(hardwareMap, this, "teleop");

        waitForStart();
        while (opModeIsActive()) {
            // slowly back to carousel
            robot.encoderDrive(0.1,0.1,-5.5,-5.5,5);

            robot.setDriveRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.setDrivePower(-0.05);

            // run carousel
            arm.startCarouselRed();
            sleep(4000);
            arm.stopCarousel();

            robot.setDrivePower(0);
            robot.setDriveRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(5000);
        }
    }
}