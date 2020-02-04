package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.StringUtils;

public class Intake extends Subsystem {
  private DcMotor left;
  private DcMotor right;

  private double powerLeft = 0;
  private double powerRight = 0;
  private boolean hasBlock = false;

  public Intake(HardwareMap map) {
    left = map.get(DcMotor.class, "R");
    right = map.get(DcMotor.class, "L");

    right.setDirection(DcMotorSimple.Direction.REVERSE);
  }

  public double getPower() {
    return powerLeft;
  }

  public void setPower(double power) {
    this.powerLeft = power;
    this.powerRight = power;
  }

  public boolean hasBlock() {
    return hasBlock;
  }

  public String getStatus() {
    return StringUtils.caption("Power", powerLeft) + StringUtils.caption("Has Block", hasBlock);
  }

  public void openIntakeBois() {
    this.powerLeft = 1.0;
  }

  @Override
  public void update() {
    left.setPower(powerLeft);
    right.setPower(powerRight);

    hasBlock = true /*TODO: implement condition*/ ? false : true;
  }
}
