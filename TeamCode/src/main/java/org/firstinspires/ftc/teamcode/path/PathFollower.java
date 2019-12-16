package org.firstinspires.ftc.teamcode.path;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.util.MyMath;
import org.firstinspires.ftc.teamcode.util.Pose2d;
import org.firstinspires.ftc.teamcode.util.Vector2d;

import java.util.ArrayList;

import static org.firstinspires.ftc.teamcode.util.MyMath.AngleWrap;

public class PathFollower {
    public static Vector2d lookAheadPoint;
    public static boolean first = true;
    public static double ogDist;
    public static boolean hasReachedEnd = false;
    public static Vector2d followPoint = new Vector2d(0.0, 0.0);
    public static double relTurnAngle = 0;

    static double movement_x, movement_y, movement_turn;
    boolean done = false;
    private ArrayList<PathSegment> path;
    private double lookAheadDist;

    public PathFollower(ArrayList<PathSegment> path, double lookAheadDist) {
        this.lookAheadDist = lookAheadDist;
        this.path = path;
        lookAheadPoint = path.get(0).start;
    }

    public static double[] goToPoint(
            Vector2d goal, Pose2d pose, double preferredAngle, double speed, double turnSpeed) {

        if (first) {
            ogDist = Math.hypot(goal.getX() - pose.getX(), goal.getY() - pose.getY());
            first = false;
        }

        double dist = Math.hypot(goal.getX() - pose.getX(), goal.getY() - pose.getY()) / ogDist;

        double absoluteAngleToTarget = Math.atan2(goal.getY() - pose.getY(), goal.getX() - pose.getX());

        double relativeAngleToPoint = AngleWrap(pose.getHeading() - absoluteAngleToTarget);

        double relativeYToPoint = Math.sin(relativeAngleToPoint) * dist;
        double relativeXToPoint = Math.cos(relativeAngleToPoint) * dist;

        //    double movementXPower =
        //        relativeXToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));
        //    double movementYPower =
        //        relativeYToPoint / (Math.abs(relativeXToPoint) + Math.abs(relativeYToPoint));

        movement_x = relativeXToPoint;
        movement_y = relativeYToPoint;

        double relativeTurnAngle = relativeAngleToPoint + preferredAngle;
        relTurnAngle = relativeTurnAngle;

        movement_turn = Range.clip((relativeTurnAngle / Math.toRadians(30)), -1, 1) /* turnSpeed*/;

        return new double[] {movement_x * speed, movement_y * speed, movement_turn * speed};
    }

    public double[] followCurve(double followAngle, Pose2d pose, double speed, double turnSpeed) {
        Vector2d point = getLookAheadPoint(pose);
        lookAheadPoint = point;

        String f = getStage(lookAheadPoint);
        if (!hasReachedEnd) {
            if (f.equals("extend")) {
                followPoint = path.get(path.size() - 2).end;
                hasReachedEnd = true;
            } else {
                followPoint = point;
            }
        }

        return goToPoint(followPoint, pose, followAngle, speed, turnSpeed);
    }

    public String getStage(Vector2d point) {
        for (PathSegment segment : this.path) {
            if (segment.start.dist(point) + segment.end.dist(point) == segment.start.dist(segment.end)) {
                return segment.label;
            }
        }
        return "none: " + point;
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
                double angle =
                        Math.toDegrees(
                                Math.atan2(intersection.getY() - pose.getY(), intersection.getX() - pose.getX()));

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
