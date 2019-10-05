package org.firstinspires.ftc.teamcode.Path;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Util.MyMath;
import org.firstinspires.ftc.teamcode.Util.Pose2d;
import org.firstinspires.ftc.teamcode.Util.Vector2d;

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
    double absoluteAngleToTarget =
        90 - Math.toDegrees(Math.atan2(goal.y - pose.y, goal.x - pose.x));
    double relativeAngleToPoint = absoluteAngleToTarget - pose.heading;

    double relativeXToPoint = goal.x - pose.x;
    double relativeYToPoint = goal.y - pose.y;

    double movementXPower = Range.clip(relativeXToPoint / 30, -1, 1);
    double movementYPower = Range.clip(relativeYToPoint / 30, -1, 1);

    System.out.println("35" + movementXPower + ", " + movementYPower);

    Vector2d rotated =
        new Vector2d(movementXPower, movementYPower).rotated(Math.toRadians(pose.heading));

    System.out.println("41" + rotated);

    movement_x = rotated.x * speed;
    movement_y = rotated.y * speed;

    double relativeTurnAngle = relativeAngleToPoint + preferredAngle;

    movement_turn = Range.clip((relativeTurnAngle) / 30, -1, 1) * turnSpeed;

    return new double[] {movement_x, movement_y, movement_turn};
  }

  public double[] followCurve(double followAngle, Pose2d pose, double speed, double turnSpeed) {
    Vector2d point = getLookAheadPoint(pose);
    lookAheadPoint = point;
    return goToPoint(new Vector2d(point.x, point.y), pose, followAngle, speed, turnSpeed);
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
        double angle = Math.toDegrees(Math.atan2(intersection.y - pose.y, intersection.x - pose.x));
        double newAngle = pose.heading < 0 ? 360 + pose.heading : pose.heading;
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
