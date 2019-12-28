package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.util.StringUtils;

public class Intake extends Subsystem {
  private DcMotor left;
  private DcMotor right;

  private double power = 0;
  private boolean hasBlock = false;

  public Intake(HardwareMap map) {
    left = map.get(DcMotor.class, "R");
    right = map.get(DcMotor.class, "L");

    right.setDirection(DcMotorSimple.Direction.REVERSE);
  }

  public void setPower(double power) {
    this.power = power;
  }

  public double getPower() {
      return power;
  }

  public boolean hasBlock() {
      return hasBlock;
  }

  public String getStatus() {
      return StringUtils.caption("Power", power) + StringUtils.caption("Has Block", hasBlock);
  }
  @Override
  public void update() {
      left.setPower(power);
      right.setPower(power);

      hasBlock = true /*TODO: implement condition*/ ? false : true;
  }
}
