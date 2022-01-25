package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ArmSubsystem {

    public static final int RESET_POS = 0;
    public static final int LOW_POS  = -300;
    public static final int MID_POS  = -500;
    public static final int HIGH_POS = -750;

    public static final int LEFT90_POS   = -1000;
    public static final int LEFT60_POS   = -667;
    public static final int LEFT45_POS   = -500;

    public static final int RIGHT90_POS  = 1000;
    public static final int RIGHT60_POS  = 667;
    public static final int RIGHT45_POS  = 500;

    private DcMotor arm_v   = null;     // vertical movement (up & down)
    private DcMotor arm_h   = null;     // horizontal movement (left & right)
    private DcMotor duck    = null;     // get ducks from the carousel
    private DigitalChannel touch_vlow = null;       // touch sensor for vertical movement (low pos)
    private DigitalChannel touch_vhigh = null;      // touch sensor for vertical movement (high pos)
    private DigitalChannel touch_h  = null;         // touch sensor for horizontal movement

    private LinearOpMode op_mode = null;
    private int waitMS = 500;

    HardwareMap hwMap = null;
    ElapsedTime runtime = new ElapsedTime();
    int pos;
    int pos_v;
    int pos_h;

    boolean moving_high = false;
    boolean moving_down = false;
    public void init(HardwareMap ahwMap, LinearOpMode opMode, String runeMode) {
        op_mode = opMode;
        pos     = 0;
        pos_v   = 0;
        pos_h   = 0;
        hwMap   = ahwMap;
        arm_v   = hwMap.get(DcMotor.class, "arm_v");
        arm_h   = hwMap.get(DcMotor.class, "arm_h");
        duck    = hwMap.get(DcMotor.class, "duck");

        // connect to digital port 1 and 3 (odd #)
        touch_vlow = hwMap.get(DigitalChannel.class, "touch_vl");
        touch_vhigh = hwMap.get(DigitalChannel.class, "touch_vh");
        touch_h = hwMap.get(DigitalChannel.class, "touch_h");
        touch_vlow.setMode(DigitalChannel.Mode.INPUT);
        touch_vhigh.setMode(DigitalChannel.Mode.INPUT);
        touch_h.setMode(DigitalChannel.Mode.INPUT);

        resetMotorH();
        resetMotorV();
    }

    public void startCarousel()
    {
        duck.setPower(1);
    }
    public void stopCarousel()
    {
        duck.setPower(0);
    }

    public void resetMotorH(){
        resetMotor(arm_h);
        pos_h = RESET_POS;
    }
    public void resetMotorV(){
        resetMotor(arm_v);
        pos_v = RESET_POS;
    }

    public boolean isMotorVBusy(){
        return arm_v.isBusy();
    }
    public boolean isMotorHBusy(){
        return arm_h.isBusy();
    }

    public void moveReset() {
        moveH(ArmSubsystem.RESET_POS);
        moveV(ArmSubsystem.RESET_POS);

        resetMotorH();
        resetMotorV();
    }
    public void moveResetNoWait() {
        moving_down = true;
        moveHNoWait(ArmSubsystem.RESET_POS);
        if(!isMotorHBusy()) {
            moveVToResetPos();
            if(!isMotorVBusy()) {
                resetMotorH();
                resetMotorV();
                moving_down = false;
            }
        }
    }

    public void moveH(int newPos) {
        move(arm_h, newPos, waitMS);
        pos_h = newPos;
    }
    public void moveV(int newPos) {
        move(arm_v, newPos, waitMS);
        pos_v = newPos;
    }

    public void moveHNoWait(int newPos){
        move(arm_h, newPos, 0);
        pos_h = newPos;
    }
    /*
    public void moveVNoWait(int newPos) {
        move(arm_v, newPos, 0);
        pos_v = newPos;
    }*/

    public void moveVToResetPos(){
        arm_v.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm_v.setPower(0.5);
        moving_down = true;

        //op_mode.sleep(100);
        if(isPressedVLow()) {
            moving_down = false;
            arm_v.setPower(0);
            arm_v.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            arm_v.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public void moveVToHighPos(){
        arm_v.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm_v.setPower(-0.5);
        moving_high = true;
        //op_mode.sleep(100);
        if(isPressedVHigh()) {
            moving_high = false;
            arm_v.setPower(0);
            arm_v.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            arm_v.setTargetPosition(HIGH_POS);
        }
    }

    //utility
    public void printPosH(){
        op_mode.telemetry.addData("Arm H pos_h", pos_h);
        op_mode.telemetry.addData("Arm H getPos", arm_h.getCurrentPosition());
    }
    public void printPosV(){
        op_mode.telemetry.addData("Arm V pos_v", pos_v);
        op_mode.telemetry.addData("Arm V getPos", arm_v.getCurrentPosition());
    }

    public boolean isPressedH(){
        return !touch_h.getState();
    }
    public boolean isPressedVLow(){
        return !touch_vlow.getState();
    }
    public boolean isPressedVHigh(){
        return !touch_vhigh.getState();
    }
    public void setPos(int newPos){
        pos = newPos;
    }
    public int getPos(){
        return pos;
    }

    private void resetMotor(DcMotor m)
    {
        m.setDirection(DcMotor.Direction.FORWARD);
        m.setPower(0);
        m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    private void move(DcMotor m, int newPos, int waitMS){
        m.setTargetPosition(newPos);
        m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m.setPower(0.8);
        if(waitMS != 0) while(m.isBusy()) {op_mode.sleep(waitMS);}
    }
}
