package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TestRobot", group = "Freight Frenzy")
//@Disabled
public class TestRobot extends LinearOpMode {
    ArmSubsystem arm = new ArmSubsystem();

    @Override
    public void runOpMode() {
        arm.init(hardwareMap, this, "teleop");

        waitForStart();
        while (opModeIsActive()) {

            // Test touch senors
            if (arm.isPressedH()) {
                telemetry.addData("Arm H", "Is Pressed");
                //arm.resetMotorH();
            } else {
                telemetry.addData("Arm H", "Is NOT Pressed");
            }

            if (arm.isPressedVLow()) {
                telemetry.addData("Arm VLow", "Is Pressed");
                //arm.resetMotorV();
            } else {
                telemetry.addData("Arm VLow", "Is NOT Pressed");
            }
            if (arm.isPressedVHigh()) {
                telemetry.addData("Arm VHigh", "Is Pressed");
                //arm.resetMotorV();
            } else {
                telemetry.addData("Arm VHigh", "Is NOT Pressed");
            }

            // Test arm
            if(gamepad2.a){
                arm.moveHNoWait(ArmSubsystem.LEFT90_POS);
            }else if(gamepad2.b){
                arm.moveHNoWait(ArmSubsystem.RIGHT90_POS);
            }

            if(gamepad2.x){
                arm.moveV(ArmSubsystem.LOW_POS);
            }else if(gamepad2.y || arm.moving_high){
                arm.moveVToHighPos();
            }

            if(gamepad2.start || arm.moving_down){
               // arm.moveResetNoWait();
                arm.moveVToResetPos();
                telemetry.addData("Start button", "Is Pressed");
            }

            arm.printPosH();
            arm.printPosV();

            telemetry.update();
        }
    }

}
