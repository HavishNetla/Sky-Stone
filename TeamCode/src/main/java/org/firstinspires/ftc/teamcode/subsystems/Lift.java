package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.PIDController;

public class Lift extends Subsystem {
  private Telemetry telemetry;

  private DigitalChannel touchSensor;

  private Servo linkage;
  private Servo rotater;
  private Servo grabber;

  private DcMotor liftMotor;

  private double linkagePos = 0.0;
  private double rotaterPos = 0.0;
  private double grabberPos = 0.0;
  private double liftPower = 0.0;

  public Lift(HardwareMap map, Telemetry telemetry) {
    linkage = map.get(Servo.class, "LL");
    rotater = map.get(Servo.class, "LR");
    grabber = map.get(Servo.class, "LG");

    liftMotor = map.get(DcMotor.class, "LM");

    touchSensor = map.get(DigitalChannel.class, "LT");
    this.telemetry = telemetry;

    liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    // `liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    // liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    setRotaterPos(1.0);
    setLinkagePos(0.36);
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

  public double liftToPos(double pos) {
    PIDController pidController = new PIDController(0.1, 0, 0);

    return pidController.getError(pos, -liftMotor.getCurrentPosition());
  }

  public boolean getTouchSensorState() {
    return touchSensor.getState();
  }

  public double getEncoderValue() {
    return liftMotor.getCurrentPosition();
  }

  @Override
  public void update() {
    linkage.setPosition(linkagePos);
    // rotater.setPosition(rotaterPos);
    grabber.setPosition(grabberPos);

    if (Math.abs(liftPower) < 0.001 && !touchSensor.getState()) {
      if (Math.abs(liftMotor.getCurrentPosition()) < 540) {
        liftPower = 0.1;
      } else {
        liftPower = 0.00021 * liftMotor.getCurrentPosition() - 0.01272065;
      }
    }
    liftMotor.setPower(liftPower);
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
}
