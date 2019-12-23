package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lift extends Subsystem {
  private Telemetry telemetry;
  private Servo linkage;
  private Servo rotater;
  private Servo grabber;

  private double linkagePos = 0.0;
  private double rotaterPos = 0.0;
  private double grabberPos = 0.0;

  public Lift(HardwareMap map, Telemetry telemetry) {
    linkage = map.get(Servo.class, "LL");
    rotater = map.get(Servo.class, "LR");
    grabber = map.get(Servo.class, "LG");

    this.telemetry = telemetry;
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

  @Override
  public void update() {
    linkage.setPosition(linkagePos);
    rotater.setPosition(rotaterPos);
    grabber.setPosition(grabberPos);
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
