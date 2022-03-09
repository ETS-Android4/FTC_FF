package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class IntakeSubsystem {
    LinearOpMode op_mode = null;

    public DcMotor intake = null;
    public Servo platform = null;
    public ModernRoboticsI2cRangeSensor rangeSensor;

    private ArmSubsystem arm = null;

    HardwareMap hwMap = null;
    ElapsedTime runtime = new ElapsedTime();

    boolean intakeFlipping = false;

    boolean hasMineral = false;
    public void init(HardwareMap ahwMap, LinearOpMode opMode, String runeMode) {
        op_mode = opMode;
        hwMap = ahwMap;
        intake = hwMap.get(DcMotor.class, "intake");
        platform = hwMap.get(Servo.class, "platform");
        rangeSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");
    }

    public void setArm(ArmSubsystem a) {
        arm = a;
    }

    ;

    public void turnOnIn() {
        intake.setPower(0.8);
    }

    public void turnOnOut() {
        intake.setPower(-0.8);
    }

    public void turnOff() {
        intake.setPower(0);
    }

    public void turnOffOnCaptured(double delayMS) {
        if (rangeSensor.cmOptical() < 3 && !intakeFlipping && !hasMineral) {
            intakeFlipping = true;
            runtime.reset();
        }
        else if(runtime.seconds() > 0.25 && runtime.seconds() < delayMS && intakeFlipping){
            arm.moveVNoWait(ArmSubsystem.LOW_POS);
        }
        else if (intakeFlipping && delayMS < runtime.seconds()) {
            intake.setPower(0);
            runtime.reset();
            intakeFlipping = false;
            hasMineral = true;
        }
        else if(rangeSensor.cmOptical() > 5){
            hasMineral = false;
        }

        op_mode.telemetry.addData("raw ultrasonic", rangeSensor.rawUltrasonic());
        op_mode.telemetry.addData("raw optical", rangeSensor.rawOptical());
        op_mode.telemetry.addData("cm optical", "%.2f cm", rangeSensor.cmOptical());
        op_mode.telemetry.addData("cm", "%.2f cm", rangeSensor.getDistance(DistanceUnit.CM));
        op_mode.telemetry.update();
    }

    // forward up
    public void platformUPF(){
        platform.setPosition(0.16);
    }

    // down (parallel to the ground)
    public void platformDown(){
        platform.setPosition(0.09);
    }

    // backward up
    public void platformUPB(){
        platform.setPosition(0);
    }

    // dump cube
    public void dumpCube(int lengthMS, boolean flipPlatformFirst) {
        if (flipPlatformFirst)
            platformUPB();

        if (lengthMS != 0) {
            turnOnOut();
            op_mode.sleep(lengthMS);
            turnOff();
        }

        if (!flipPlatformFirst)
            platformUPB();
    }
}