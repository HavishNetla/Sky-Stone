package org.firstinspires.ftc.teamcode.Util;

public class Pose2d {
  public double x, y, heading;

  public Pose2d(double x, double y, double heading) {
    this.x = x;
    this.y = y;
    this.heading = heading;
  }

  /** @return a vector2d comprised of the x and y of the vector (position) */
  public Vector2d pos() {
    return new Vector2d(x, y);
  }

  /**
   * Adds two Poses
   *
   * @param other Another Pose
   * @return the sum of the Poses
   */
  public Pose2d plus(Pose2d other) {
    return new Pose2d(x + other.x, y + other.y, heading + other.heading);
  }

  /**
   * Subtracts two Poses
   *
   * @param other Another Pose
   * @return the diffrence of the Poses
   */
  public Pose2d minus(Pose2d other) {
    return new Pose2d(x - other.x, y - other.y, heading - other.heading);
  }

  /**
   * multiplys a Pose by a scalar
   *
   * @param scalar A double to scale the vector by
   * @return a scaled Pose2d
   */
  public Pose2d times(double scalar) {
    return new Pose2d(x * scalar, y * scalar, heading * scalar);
  }

  /**
   * divides a Pose by a scalar
   *
   * @param scalar A double to scale the vector by
   * @return a scaled Pose2d
   */
  public Pose2d div(double scalar) {
    return new Pose2d(x / scalar, y / scalar, heading / scalar);
  }

  /**
   * negates the values of the psoe
   *
   * @return a negated Pose2d
   */
  public Pose2d unaryMinus() {
    return new Pose2d(-x, -y, -heading);
  }

  /** @return the Pose translated into a string */
  @Override
  public String toString() {
    return String.format("(%.3f, %.3f, %.3f°)", x, y, heading);
  }

  /**
   * checks if the pose is equall to another pose
   *
   * @param other another pose
   * @return true or false
   */
  public boolean equals(Pose2d other) {
    if (other instanceof Pose2d) {
      return Math.abs(x - other.x) < 1e-4
          && Math.abs(y - other.y) < 1e-4
          && Math.abs(heading - other.heading) < 1e-4;
    } else {
      return false;
    }
  }
}
