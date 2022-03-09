package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.teamcode.vision.UGRectDetector;

@TeleOp(name = "FFTeleOp", group = " Freight Frenzy")
//@Disabled
public class FFTeleop extends LinearOpMode {
    IntakeSubsystem intake = new IntakeSubsystem();
    ArmSubsystem arm = new ArmSubsystem();

    double speed = 0.5;
    ElapsedTime runtime = new ElapsedTime();
    boolean buttonPrev[] = {false, false, false, false, false, false}; //bumper_left, bumper_right, gamepad2_y, dpad_up
    // TODO: fix those variable names
    //forward speed
    double ch3 = 0;
    //turn speed
    double ch1;
    //strafe speed
    double ch4 = 0;

    boolean carousel_on = false;
    boolean moving = false;

    boolean highMode = true;

    boolean manualV = false;
    boolean manualH = false;

    @Override
    public void runOpMode() {
        DriveTrainStrafeSimple robot = new DriveTrainStrafeSimple();
        robot.init(hardwareMap,this,"teleop");
        intake.init(hardwareMap, this, "teleop");
        arm.init(hardwareMap, this, "teleop");
        arm.setIntake(intake);
        intake.setArm(arm);

        telemetry.addData("Say", "Hello Driver");
        telemetry.update();
        robot.setDriveRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            // Control speed
            if (gamepad1.b) {
                speed = 0.2;
            } else if (gamepad1.dpad_up) {
                speed = 1;
            } else if (gamepad1.a) {
                speed = 0.5;
            }
            if(highMode) {
                // Move arm horizontal
                if (gamepad2.right_stick_x > 0.9) {
                    arm.moveHNoWait(ArmSubsystem.RIGHT90_POS);
                } else if (gamepad2.right_stick_x < -0.9) {
                    arm.moveHNoWait(ArmSubsystem.LEFT90_POS);
                }

                // Move arm vertical
                if (gamepad2.right_stick_y < -0.6 || arm.moving_high) {
                    arm.moveVToHighPos();
                } else if (gamepad2.right_stick_y > 0.6) {
                    arm.moveVNoWait(ArmSubsystem.MID_POS);
                }
            }
            else{
                // Move arm horizontal
                if (gamepad2.right_stick_x > 0.9) {
                    arm.moveHNoWait(ArmSubsystem.RIGHT135_POS);
                } else if (gamepad2.right_stick_x < -0.9) {
                    arm.moveHNoWait(ArmSubsystem.LEFT135_POS);
                }
                if (gamepad2.right_stick_y < -0.6 || arm.moving_high) {
                    arm.moveVNoWait(ArmSubsystem.LOW_POS);
                }

            }
            if(buttonClick(gamepad2.right_stick_button, 0)) highMode = !highMode;
            if(buttonClick(gamepad2.left_stick_button, 1)) arm.resetHorizontal();

            //reset arm
            if(gamepad2.left_stick_x>0.6){
                manualH = true;
                arm.armH_setPower(0.2);
            }
            else if(gamepad2.left_stick_x<-0.6){
                manualH = true;
                arm.armH_setPower(-0.2);
            }
            else if(manualH){
                manualH = false;
                arm.armH_setPower(0);
                arm.armH_runWithEncoder();
            }

            //manually arm up and down
            if(gamepad2.left_stick_y>0.6){
                manualV = true;
                arm.armV_setPower(0.2);
            }
            else if(gamepad2.left_stick_y<-0.6){
                manualV = true;
                arm.armV_setPower(-0.2);
            }
            else if(manualV){
                manualV = false;
                arm.armV_setPower(0);
                arm.armV_runWithEncoder();
            }

            if(gamepad2.start || arm.moving_down){
                arm.moveResetNoWait();
            }


            arm.printPosH();
            telemetry.update();
            //move intake platform
            if(gamepad2.dpad_up){
                intake.platformUPF();
            }else if(gamepad2.dpad_down) {
                intake.platformUPB();
            }else if(gamepad2.dpad_right){
                intake.platformDown();
            }

            //intake
            if(gamepad2.x){
                intake.platformDown();
                intake.turnOnIn();
            }
            else if(gamepad2.y){
                intake.turnOnOut();
            }
            else{
                intake.turnOffOnCaptured(0.75);
            }

            //spin carousel wheel
            if(!carousel_on) {
                if (buttonClick(gamepad2.a, 2)) {
                    arm.startCarouselRed();
                    carousel_on = true;
                } else if (buttonClick(gamepad2.b, 3)) {
                    arm.startCarouselBlue();
                    carousel_on = true;
                }
            }
            else{
                if (buttonClick(gamepad2.a, 2) || buttonClick(gamepad2.b, 3)) {
                    arm.stopCarousel();
                    carousel_on = false;
                }
            }
            if(gamepad2.right_bumper){
                intake.turnOff();
            }

            // ### Drive the robot
            ch3 = gamepad1.left_stick_y > 0.4 ? 0.9 : gamepad1.left_stick_y < -0.4 ? -0.9 : 0;
            ch4 = gamepad1.left_bumper || gamepad1.left_stick_x< -0.6 ? 0.9 : gamepad1.right_bumper|| gamepad1.left_stick_x>0.6? -0.9 : 0;
            ch1 = gamepad1.right_stick_x > 0.4 ? -0.6 : gamepad1.right_stick_x < -0.4 ? 0.6 : 0;
            robot.leftDriveFront.setPower((ch3 + ch1 + ch4) * speed);
            robot.leftDriveBack.setPower((ch3 + ch1 - ch4) * speed);
            robot.rightDriveFront.setPower((ch3 - ch1 - ch4) * speed);
            robot.rightDriveBack.setPower((ch3 - ch1 + ch4) * speed);
        }
    }

    public boolean buttonClick(boolean button, int loc) {
        boolean returnVal = button && !buttonPrev[loc];
        buttonPrev[loc] = button;
        return returnVal;
    }
}