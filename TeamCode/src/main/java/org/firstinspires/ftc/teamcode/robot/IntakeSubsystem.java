package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class IntakeSubsystem {
    LinearOpMode op_mode = null;

    public Servo s0 = null;
    public Servo s1 = null;

    HardwareMap hwMap = null;
    ElapsedTime runtime = new ElapsedTime();

    public void init(HardwareMap ahwMap, LinearOpMode opMode, String runeMode) {
        op_mode = opMode;
        hwMap = ahwMap;
        s0 = hwMap.get(Servo.class, "intake_s0");
        s1 = hwMap.get(Servo.class, "intake_s1");
    }

    public void turnOnIn(){
        s0.setPosition(0);
    }
    public void turnOnOut(){
        s0.setPosition(1);
    }
    public void turnOff() {
        s0.setPosition(0.5);
    }

    public void platformUP(){
        s1.setPosition(0);
    }
    public void platformDown(){
        s1.setPosition(0.1);
    }
}
