package org.firstinspires.ftc.teamcode.ComputerDebuging;

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
      computerDebugging.sendRobotLocation(robot.robotPos);
      computerDebugging.sendPacket();
    }
  }
}
