package org.firstinspires.ftc.teamcode.path;

import org.firstinspires.ftc.teamcode.util.Vector2d;

public class PathSegment {
  public Vector2d start, end;
  public String label;

  public PathSegment(Vector2d start, Vector2d end, String label) {
    this.start = start;
    this.end = end;
    this.label = label;
  }

  @Override
  public String toString() {
    return ("(" + start + ", " + end + ": " + label + ")");
  }
}
