package org.firstinspires.ftc.teamcode.ComputerDebuging;

import org.firstinspires.ftc.teamcode.Util.Pose2d;
import org.firstinspires.ftc.teamcode.Util.Vector2d;

public class Robot {
  public static double x = 0, y = 0, c = 0;
  public static Pose2d robotPos;
  private long lastTime = 0;

  public Robot() {
    robotPos = new Pose2d(0, 0, 90);
  }

  public void update() {
    long time = System.currentTimeMillis();
    double elapsedTime = (time - lastTime) / 1000.0;
    lastTime = time;

    if (elapsedTime > 1) return;

    Vector2d powers = new Vector2d(x, y);

    Vector2d rotatedPowers = powers.rotated(Math.toRadians(-robotPos.heading));

    robotPos.x += rotatedPowers.x * elapsedTime * 1000 * 0.2;
    robotPos.y += rotatedPowers.y * elapsedTime * 1000 * 0.2;
    robotPos.heading += c * elapsedTime * 100;

    ComputerDebugging.sendNoClear(robotPos.pos());
  }
}
