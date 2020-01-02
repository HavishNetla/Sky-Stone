package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
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

    PIDFCoefficients newPIDF = new PIDFCoefficients(5.0, 0.0, 0.0, 0.0);
    liftMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, newPIDF);

    liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    liftMotor.setTargetPosition(-800);
    liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
  }

  public void stowCap() {
    setCapstonePosition(0.6);
  }

  public void openCap() {
    setCapstonePosition(0.8);
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
    if (-power < 0.0) {
      scalar = 0.3;
    } else {
      scalar = 0.5;
    }
    if (touchSensor.getState() && -power < 0.0) {
      liftPower = 0.0;
    } else {
      liftPower = power * scalar;
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

  public void setCapstonePosition(double pos) {
    this.capStonePos = pos;
  }

  public String getPID() {
    PIDFCoefficients pidOrig = liftMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION);

    return ("P,I,D (orig): "
        + " "
        + pidOrig.p
        + ", "
        + pidOrig.i
        + ", "
        + pidOrig.d
        + ", "
        + pidOrig.f);
  }

  @Override
  public void update() {
    linkage.setPosition(linkagePos);
    // rotater.setPosition(rotaterPos);
    grabber.setPosition(grabberPos);
    capStone.setPosition(capStonePos);

    //    if (Math.abs(liftPower) < 0.001 && !touchSensor.getState()) {
    //      if (Math.abs(liftMotor.getCurrentPosition()) < 440) {
    //        liftPower = 0.3;
    //      } else {
    //        liftPower = 0.0003 * liftMotor.getCurrentPosition() - 0.04272065;
    //      }
    //    }

    if (liftMotor.isBusy()) {
      liftMotor.setPower(0.5);
    } else {
      liftMotor.setPower(0);
    }
    // liftMotor.setPower(liftPower);
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
