

package org.firstinspires.ftc.teamcode.robot;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "servoPos", group = "Pushbot")
//@Disabled
public class servoPos extends LinearOpMode {

    double speed = 0.5;
    public Servo servo = null;

    double servoPos = 0;
    boolean prev[] = {false, false};

    @Override
    public void runOpMode() {
        //  arm = hardwareMap.get(DcMotor.class, "arm");
        servo = hardwareMap.get(Servo.class, "serfo");

        waitForStart();
        servo.setPosition(0);

        while (opModeIsActive()) {
            if (singleClick(gamepad1.right_bumper, 0)) {
                servoPos += 0.1;
            } else if (singleClick(gamepad1.left_bumper, 1)) {
                servoPos -= 0.1;
            }
            telemetry.addData("servo", servoPos);

            servo.setPosition(servoPos);
            sleep(100);
            telemetry.update();


        }
    }

    public boolean singleClick(boolean cur, int loc) {
        boolean temp = cur && !prev[loc];
        prev[loc] = cur;
        return temp;
    }
}