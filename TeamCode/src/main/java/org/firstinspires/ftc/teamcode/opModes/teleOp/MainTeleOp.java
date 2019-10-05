package org.firstinspires.ftc.teamcode.OpModes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Robot;
import org.firstinspires.ftc.teamcode.Util.Vector2d;

@TeleOp(name = "Mecanum Drive", group = "T")
public class MainTeleOp extends OpMode {
  private Robot robot;

  @Override
  public void init() {
    robot = new Robot(this);
    robot.start();
  }

  @Override
  public void loop() {
    robot.drive.setVelocity(
        new Vector2d(gamepad1.left_stick_x, gamepad1.left_stick_y), gamepad1.right_stick_x);

    telemetry.addData("Position", robot.drive.getPosition());


  }
}
