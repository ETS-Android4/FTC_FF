package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TestDrive", group = "Freight Frenzy")
//@Disabled
public class TesetDrive extends LinearOpMode {
    DriveTrainStrafeSimple robot = new DriveTrainStrafeSimple();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, this, "teleop");

        waitForStart();
        while (opModeIsActive()) {

            telemetry.addData("Drive45: ", "q1");
            robot.encoderDriveStrafe45(0.5, 10, "q1", 5);
            sleep(3000);

            telemetry.addData("Drive45: ", "q2");
            robot.encoderDriveStrafe45(0.5, 10, "q2", 5);
            sleep(3000);

            telemetry.addData("Drive45: ", "q3");
            robot.encoderDriveStrafe45(0.5, 10, "q3", 5);
            sleep(3000);

            telemetry.addData("Drive45: ", "q4");
            robot.encoderDriveStrafe45(0.5, 10, "q4", 5);
            sleep(3000);

            telemetry.addData("Drive: ", "left");
            robot.encoderDriveStrafe(0.5, 10, "left", 5);
            sleep(3000);

            telemetry.addData("Drive: ", "right");
            robot.encoderDriveStrafe(0.5, 10, "right", 5);
            sleep(3000);

            telemetry.addData("Drive: ", "forward");
            robot.encoderDrive(0.5, 0.5, -10, -10, 5);
            sleep(3000);

            telemetry.addData("Drive: ", "backward");
            robot.encoderDrive(0.5, 0.5, 10, 10, 5);
            sleep(3000);

            telemetry.update();
        }
    }
}
