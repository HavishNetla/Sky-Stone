package org.firstinspires.ftc.teamcode.path;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.util.MyMath;
import org.firstinspires.ftc.teamcode.util.Pose2d;
import org.firstinspires.ftc.teamcode.util.Vector2d;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.util.MyMath.AngleWrap;

public class PathFollower {
  double followAngle1 = 0.0;
  double speed1 = 0.0;
  double turnSpeed1 = 0.0;
  private double movement_x, movement_y, movement_turn;
  private Vector2d lookAheadPoint;
  private boolean first = true;
  private Vector2d powers = new Vector2d(0, 0);
  private double ogDist;
  private boolean hasReachedEnd = false;
  private Vector2d followPoint = new Vector2d(0.0, 0.0);
  private double relTurnAngle = 0;
  private boolean isDone = false;
  private boolean ended = false;
  private ArrayList<PathSegment> path;
  private double lookAheadDist;
  private String name;
  private double relativeAngleToPoint;

  public PathFollower(ArrayList<PathSegment> path, double lookAheadDist, String name) {
    this.lookAheadDist = lookAheadDist;
    this.path = path;
    lookAheadPoint = path.get(0).start;
    hasReachedEnd = false;
    ended = false;
    isDone = false;

    this.name = name;
  }

  public double getRelativeAngleToPoint() {
    return relTurnAngle;
  }

  public double[] goToPoint(
      Vector2d goal, Pose2d pose, double preferredAngle, double speed, double turnSpeed) {

    Vector2d pointToTurn;
    if (first) {
      ogDist = Math.hypot(goal.getX() - pose.getX(), goal.getY() - pose.getY());
      first = false;
    }

    double dist = Math.hypot(goal.getX() - pose.getX(), goal.getY() - pose.getY()) / ogDist;

    double absoluteAngleToTarget = Math.atan2(goal.getY() - pose.getY(), goal.getX() - pose.getX());
    double relativeAngleToPoint = AngleWrap(pose.getHeading() - absoluteAngleToTarget);
    relTurnAngle = relativeAngleToPoint;
    double relativeYToPoint;
    double relativeXToPoint;

    relativeYToPoint = Math.sin(relativeAngleToPoint) * dist;
    relativeXToPoint = Math.cos(relativeAngleToPoint) * dist;
    powers = new Vector2d(relativeXToPoint, relativeYToPoint);

    //    double movementXPower =
    //        relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
    //    double movementYPower =
    //        relativeYToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));

    movement_x = relativeXToPoint;
    movement_y = relativeYToPoint;

    double relativeTurnAngle = relativeAngleToPoint + preferredAngle;
    relTurnAngle = relativeTurnAngle;

    if (ended == false) {

      if (Math.abs(Math.hypot(goal.getX() - pose.getX(), goal.getY() - pose.getY())) < 10) {
        ended = true;
        movement_turn = 0;
      } else {
        movement_turn = Range.clip((relativeTurnAngle / Math.toRadians(30)), -1, 1) /* turnSpeed*/;
      }
    }

    if (Math.abs(movement_x) < 0.2 && Math.abs(movement_y) < 0.2) {
      isDone = true;
    }

    return new double[] {movement_x * speed, movement_y * speed, movement_turn * speed};
  }

  public boolean getStatus() {
    return isDone;
  }

  public double[] followCurve(double followAngle, Pose2d pose, double speed, double turnSpeed) {
    Vector2d point = getLookAheadPoint(pose, this.path.get(0).followAngle);
    lookAheadPoint = point;

    PathSegment f = this.path.get(getStage(lookAheadPoint));
    System.out.println("statut: " + lookAheadPoint);
    if (!hasReachedEnd) {
      if (f.label == "extend") {
        followPoint = path.get(path.size() - 2).end;
        hasReachedEnd = true;
      } else {
        followAngle1 = f.followAngle;
        speed1 = f.speed;
        turnSpeed1 = f.turnSpeed;

        followPoint = point;
        System.out.println("statut: followAngle: " + followAngle1);
      }
    }

    return goToPoint(followPoint, pose, followAngle1, speed1, turnSpeed1);
  }

  public int getStage(Vector2d point) {

    for (int i = 0; i < this.path.size(); i++) {
      PathSegment segment = path.get(i);
      if (segment.start.dist(point) + segment.end.dist(point) == segment.start.dist(segment.end)) {
        return i;
      }
    }
    return 0;
  }

  public Vector2d getLookAheadPoint() {
    return lookAheadPoint;
  }

  /**
   * @param pose The current Pose of the robot
   * @return The point for the robot to move towards
   */
  public Vector2d getLookAheadPoint(Pose2d pose, double followAngle) {
    Vector2d point = path.get(0).start;
    lookAheadPoint = point;

    // Loop through the path and find all intersections
    for (int i = 0; i < path.size(); i++) {
      ArrayList<Vector2d> intersectionsinPath =
          MyMath.lineCircleIntersection(
              pose.pos(), lookAheadDist, path.get(i).start, path.get(i).end);

      double closestAngle = 1000000;
      for (Vector2d intersection : intersectionsinPath) {
        double angle =
            Math.toDegrees(
                Math.atan2(intersection.getY() - pose.getY(), intersection.getX() - pose.getX()));

        double angleFix = Math.toDegrees(pose.getHeading() + followAngle) + 90;
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
  public double[] update(double followAngle, Pose2d pose, double speed, double turnSpeed) {
    return followCurve(followAngle, pose, speed, turnSpeed);
  }

  public String getName() {
    return this.name;
  }
}
