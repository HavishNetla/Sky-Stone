package org.firstinspires.ftc.teamcode.path;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.util.MyMath;
import org.firstinspires.ftc.teamcode.util.Pose2d;
import org.firstinspires.ftc.teamcode.util.Vector2d;

import java.util.ArrayList;

public class PathFollower {
  public static Vector2d lookAheadPoint;
  static double movement_x, movement_y, movement_turn;
  private ArrayList<PathSegment> path;
  private double lookAheadDist;

  public PathFollower(ArrayList<PathSegment> path, double lookAheadDist) {
    this.lookAheadDist = lookAheadDist;
    this.path = path;
    lookAheadPoint = path.get(0).start;
  }

  public static double[] goToPoint(
      Vector2d goal, Pose2d pose, double preferredAngle, double speed, double turnSpeed) {
    double fixedHeading = Math.toDegrees(pose.getHeading()) + 90;
    double absoluteAngleToTarget =
        90 - Math.toDegrees(Math.atan2(goal.getY() - pose.getY(), goal.getX() - pose.getX()));
    double relativeAngleToPoint = absoluteAngleToTarget - fixedHeading;

    double relativeXToPoint = goal.getX() - pose.getX();
    double relativeYToPoint = goal.getY() - pose.getY();

    double movementXPower = Range.clip(relativeXToPoint / 30, -1, 1);
    double movementYPower = Range.clip(relativeYToPoint / 30, -1, 1);

    System.out.println("35" + movementXPower + ", " + movementYPower);

    Vector2d rotated =
        new Vector2d(movementXPower, movementYPower).rotated(Math.toRadians(fixedHeading));

    System.out.println("41" + rotated);

    movement_x = rotated.getX() * speed;
    movement_y = rotated.getY() * speed;

    double relativeTurnAngle = relativeAngleToPoint + preferredAngle;

    movement_turn = Range.clip((relativeTurnAngle) / 30, -1, 1) * turnSpeed;

    return new double[] {movement_x, movement_y, movement_turn};
  }

  public double[] followCurve(double followAngle, Pose2d pose, double speed, double turnSpeed) {
    Vector2d point = getLookAheadPoint(pose);
    lookAheadPoint = point;
    return goToPoint(new Vector2d(point.getX(), point.getY()), pose, followAngle, speed, turnSpeed);
  }
  public Vector2d getLookAheadPoint() {
    return lookAheadPoint;
  }

  /**
   * @param pose The current Pose of the robot
   * @return The point for the robot to move towards
   */
  public Vector2d getLookAheadPoint(Pose2d pose) {
    Vector2d point = path.get(0).start;
    lookAheadPoint = point;

    // Loop through the path and find all intersections
    for (int i = 0; i < path.size(); i++) {
      ArrayList<Vector2d> intersectionsinPath =
          MyMath.lineCircleIntersection(
              pose.pos(), lookAheadDist, path.get(i).start, path.get(i).end);

      double closestAngle = 1000000;
      for (Vector2d intersection : intersectionsinPath) {
        double angle = Math.toDegrees(Math.atan2(intersection.getY() - pose.getY(), intersection.getX() - pose.getX()));

        double angleFix = Math.toDegrees(pose.getHeading()) + 90;
        double newAngle = angleFix < 0 ? 360 + angleFix : angleFix;
        double deltaAngle = Math.abs(angle - newAngle);

        if (deltaAngle < closestAngle) {
          closestAngle = deltaAngle;
          point = intersection;
          lookAheadPoint = point;
        }
      }
    }
    return point;
  }

  /**
   * @param pose The current pose of the robot
   * @return the left and right powers
   */
  public double[] update(Pose2d pose) {
    double[] test = new double[] {1, 1};
    return test;
  }
}
