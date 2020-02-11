package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.PIDController;

public class Lift extends Subsystem {
  double scalar = 1.0;
  private Telemetry telemetry;
  private DigitalChannel touchSensor;
  private Servo linkage;
  private Servo rotater;
  private Servo grabber;
  private Servo capStone;
  private DcMotorEx liftMotor;
  private double linkagePos = 0.0;
  private double rotaterPos = 0.0;
  private double grabberPos = 0.0;
  private double liftPower = 0.0;
  private double capStonePos = 0.0;
  private int liftPos = 0;
  private double currentPosition;

  private LIFT_STATUS liftStatus;

  private PIDController pidController = new PIDController(0.01, 0.002, 0.002);
  private double joyStickPower = 0.0;

  public Lift(HardwareMap map, Telemetry telemetry) {
    linkage = map.get(Servo.class, "LL");
    rotater = map.get(Servo.class, "LR");
    grabber = map.get(Servo.class, "LG");
    capStone = map.get(Servo.class, "SS");

    liftMotor = map.get(DcMotorEx.class, "LM");

    touchSensor = map.get(DigitalChannel.class, "LT");
    this.telemetry = telemetry;

    liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    setRotaterPos(1.0);
    setLinkagePos(0.36);
    open();
    stowCap();

    liftMotor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

    // liftMotor.setTargetPosition(-240 * 3 * 2);
    // liftMotor.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

    setLiftStatus(LIFT_STATUS.RESETING);
  }

  public void stowCap() {
    setCapstonePosition(0.6);
  }

  public void openCap() {
    setCapstonePosition(0.35);
  }

  public double getLinkagePos() {
    return linkagePos;
  }

  public void setLinkagePos(double pos) {
    linkagePos = pos;
  }

  public void setRotaterPos(double pos) {
    rotaterPos = pos;
  }

  public void setGrabberPos(double pos) {
    grabberPos = pos;
  }

  public void grap() {
    setGrabberPos(0.6);
  }

  public void open() {
    setGrabberPos(0.4);
  }

  public void spinRotater(ROTATER_POSITION pos) {
    if (pos == ROTATER_POSITION.LOADING) {
      rotaterPos = 0.57;
    } else if (pos == ROTATER_POSITION.LEFT) {
      rotaterPos = 0.05;
    } else if (pos == ROTATER_POSITION.RIGHT) {
      rotaterPos = 1.0;
    }
  }

  public void lift(double power) {
    if (touchSensor.getState() && -power < 0.0) {
      liftPower = 0.0;
    } else {
      liftPower = power;
    }
  }

  public boolean getTouchSensorState() {
    return touchSensor.getState();
  }

  public void setCapstonePosition(double pos) {
    this.capStonePos = pos;
  }

  public void setLiftPower(double power) {
    this.liftPower = power;
  }

  public void setTargetPosition(int position) {
    this.liftPos = position;
  }

  public LIFT_STATUS getLiftStatus() {
    return liftStatus;
  }

  public void setLiftStatus(LIFT_STATUS status) {
    this.liftStatus = status;
  }

  public int getCurrentPosition() {
    int pos = 0;
    try {
      pos = liftMotor.getCurrentPosition();

    } catch (Exception e) {
      System.out.println("ERROR1: " + e);
    }
    return pos;
  }

  public double getLiftEncoderPos() {
    return liftMotor.getCurrentPosition();
  }

  @Override
  public void update() {
    linkage.setPosition(linkagePos);
    // rotater.setPosition(rotaterPos);
    grabber.setPosition(grabberPos);
    capStone.setPosition(capStonePos);

    switch (liftStatus) {
      case RESETING:
        if (liftMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
          liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
          telemetry.addData("reseting", "settong mode");
        }

        if (!getTouchSensorState()) {
          liftMotor.setPower(1.0);
        } else {
          setLiftStatus(LIFT_STATUS.NOTHING);
        }

        break;
      case MANUAL:
        if (liftMotor.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
          liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
          telemetry.addData("manual", "settong mode");
        }

        if (getTouchSensorState()) {
          if (liftPower >= 0) {
            liftMotor.setPower(0);
          } else {
            liftMotor.setPower(liftPower);
          }
        } else {
          liftMotor.setPower(liftPower);
        }
        telemetry.addData("MANUAL", liftMotor.getPower());

        break;
      case RUN_TO_POSITION:
        liftMotor.setTargetPosition(liftPos);

        if (liftMotor.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
          liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
          telemetry.addData("run2pos", "settong mode");
        }
        liftMotor.setPower(-1.0);

        telemetry.addData("RUN TO POSITION", liftMotor.getPower());
        break;
      case NOTHING:
        liftMotor.setPower(0);
        break;
    }
    System.out.println("touch sensor: " + getTouchSensorState());

    //    liftMotor.setPower(pidController.getError(liftPos, liftMotor.getCurrentPosition()));
  }

  public enum ROTATER_POSITION {
    LOADING,
    LEFT,
    RIGHT
  }

  public enum LINKAGE_POSITION {
    LOADING,
    LEFT,
    RIGHT
  }

  public enum LIFT_STATUS {
    RESETING,
    MANUAL,
    RUN_TO_POSITION,
    NOTHING
  }
}
