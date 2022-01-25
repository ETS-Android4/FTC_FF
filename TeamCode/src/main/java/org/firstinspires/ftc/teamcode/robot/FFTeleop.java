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

    double speed = 1;
    ElapsedTime runtime = new ElapsedTime();
    boolean buttonPrev[] = {false, false, false, false, false, false}; //bumper_left, bumper_right, gamepad2_y, dpad_up
    // TODO: fix those variable names
    //forward speed
    double ch3 = 0;
    //turn speed
    double ch1;
    //strafe speed
    double ch4 = 0;

    boolean intake_on = false;

    @Override
    public void runOpMode() {
        DriveTrainStrafeSimple robot = new DriveTrainStrafeSimple();
        robot.init(hardwareMap,this,"teleop");
        intake.init(hardwareMap, this, "teleop");
        //shooter.init(hardwareMap);
        arm.init(hardwareMap, this, "teleop");
        //vision.init(hardwareMap, "Webcam");
        telemetry.addData("Say", "Hello Driver");
        telemetry.update();
        robot.setDriveRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            // Control speed
            if (gamepad1.dpad_down) {
                speed = 0.5;
            } else if (gamepad1.dpad_up) {
                speed = 1;
            } else if (gamepad1.dpad_right) {
                speed = 0.2;
            }

            // Move arm horizontal
            if(gamepad2.left_stick_x>0.6){
                arm.moveHNoWait(ArmSubsystem.RIGHT90_POS);
            }else if(gamepad2.left_stick_x<-0.6){
                arm.moveHNoWait(ArmSubsystem.LEFT90_POS);
            }

            // Move arm vertical
            if(gamepad2.right_stick_y<-0.6 || arm.moving_high) {
                arm.moveVToHighPos();
            }
            else if(gamepad2.right_stick_y>0.6 || arm.moving_down){
                arm.moveV(ArmSubsystem.MID_POS);
            }

            //reset arm
            if(gamepad2.start || arm.moving_down){
                arm.moveResetNoWait();
            }

            //intake
            if(gamepad2.x){
                intake.turnOnIn();
            }
            else if(gamepad2.y){
                intake.turnOnOut();
            }
            else{
                intake.turnOff();
            }
            telemetry.addData("intake_on", intake_on);
            telemetry.update();
            //move intake platform
            if (gamepad2.right_bumper) {
                intake.platformDown();
            }
            else if (gamepad2.left_bumper) {
                intake.platformUP();
            }

            //spin carousel wheel
            if(gamepad2.a){
                arm.startCarousel();
            }
            else if(gamepad2.b){
                arm.stopCarousel();
            }

            // ### Drive the robot
            ch3 = gamepad1.left_stick_y > 0.4 ? 0.9 : gamepad1.left_stick_y < -0.4 ? -0.9 : 0;
            ch4 = gamepad1.left_bumper ? 0.9 : gamepad1.right_bumper ? -0.9 : 0;
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
