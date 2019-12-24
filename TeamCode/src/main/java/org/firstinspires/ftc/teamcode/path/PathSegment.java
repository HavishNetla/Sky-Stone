package org.firstinspires.ftc.teamcode.path;

import org.firstinspires.ftc.teamcode.util.Vector2d;

public class PathSegment {
  public Vector2d start, end;
  public String label;
  public double followAngle, speed, turnSpeed;

  public PathSegment(
      Vector2d start,
      Vector2d end,
      double followAngle,
      double speed,
      double turnSpeed,
      String label) {
    this.start = start;
    this.end = end;
    this.label = label;
    this.followAngle = followAngle;
    this.speed = speed;
    this.turnSpeed = turnSpeed;
  }

  @Override
  public String toString() {
    return ("(" + start + ", " + end + ": " + label + ")");
  }
}
