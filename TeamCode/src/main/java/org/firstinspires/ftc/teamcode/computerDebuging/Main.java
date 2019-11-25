package org.firstinspires.ftc.teamcode.computerDebuging;

import org.firstinspires.ftc.teamcode.util.Pose2d;

public class Main {
  public static void main(String[] args) {
    ComputerDebugging computerDebugging = new ComputerDebugging();
    Robot robot = new Robot();
    OpMode opmode = new MyOpMode();

    opmode.init();

    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    while (true) {
      opmode.loop();
      try {
        Thread.sleep(30);
      } catch (InterruptedException e) {

      }
      robot.update();
      Pose2d fixPos = new Pose2d(Robot.robotPos.getX(), Robot.robotPos.getY(), Robot.robotPos.getHeading() * (180 / Math.PI));
      computerDebugging.sendRobotLocation(fixPos);
      computerDebugging.sendPacket();
    }
  }
}
