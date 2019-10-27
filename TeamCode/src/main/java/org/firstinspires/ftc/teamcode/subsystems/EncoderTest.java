package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class EncoderTest extends Subsystem {
  private DcMotor main;

  public EncoderTest(HardwareMap map) {
    main = map.get(DcMotor.class, "FL");
    main.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
  }

  public double getPos() {
    return main.getCurrentPosition();
  }

  @Override
  public void update() {

  }
}


