package org.firstinspires.ftc.teamcode.computerDebuging;

import org.firstinspires.ftc.teamcode.util.Pose2d;
import org.firstinspires.ftc.teamcode.util.Vector2d;

public class Robot {
  public static double x = 0, y = 0, c = 0;
  public static Pose2d robotPos;
  private long lastTime = 0;

  public Robot() {
    robotPos = new Pose2d(300, 300, Math.toRadians(90));
  }

  public void update() {
    long time = System.currentTimeMillis();
    double elapsedTime = (time - lastTime) / 1000.0;
    lastTime = time;

    if (elapsedTime > 1) return;

    Vector2d powers = new Vector2d(x, y);

    Vector2d rotatedPowers = powers.rotated(robotPos.getHeading() - Math.PI / 2);

    robotPos.setX(robotPos.getX() + rotatedPowers.getX() * elapsedTime * 1000 * 0.2);
    robotPos.setY(robotPos.getY() + rotatedPowers.getY() * elapsedTime * 1000 * 0.2);
    robotPos.setHeading(robotPos.getHeading() + Math.toRadians(-c) * elapsedTime * 100);

    ComputerDebugging.sendNoClear(robotPos.pos());
  }
}
