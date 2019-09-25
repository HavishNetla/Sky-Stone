package org.firstinspires.ftc.teamcode.Util;

public class Vector2d {
  public double x, y;

  public Vector2d(double x, double y) {
    this.x = x;
    this.y = y;
  }

  // =============================================

  public double norm() {
    return Math.sqrt(x * x + y * y);
  }

  public double angle() {
    return Math.atan2(y, x);
  }

  public Vector2d plus(Vector2d other) {
    return new Vector2d(x + other.x, y + other.y);
  }

  public Vector2d minus(Vector2d other) {
    return new Vector2d(x - other.x, y - other.y);
  }

  public Vector2d times(double scalar) {
    return new Vector2d(x * scalar, y * scalar);
  }

  public Vector2d div(double scalar) {
    return new Vector2d(x / scalar, y / scalar);
  }

  public Vector2d unaryMinus() {
    return new Vector2d(-x, -y);
  }

  public double dot(Vector2d other) {
    return x * other.x + y * other.y;
  }

  public double distanceTo(Vector2d other) {
    return this.minus(other).norm();
  }

  public Vector2d rotated(double angle) {
    // 10 * 1              - 0
    double newX = x * Math.cos(angle) - y * Math.sin(angle);
    // 10 * 0              + 0
    double newY = x * Math.sin(angle) + y * Math.cos(angle);
    return new Vector2d(newX, newY);
  }

  @Override
  public String toString() {
    return String.format("(%.3f, %.3f)", x, y);
  }

  public boolean equals(Vector2d other) {
    if (other instanceof Vector2d) {
      return Math.abs(x - other.x) < 1e-4 && Math.abs(y - other.y) < 1e-4;
    } else {
      return false;
    }
  }
}
