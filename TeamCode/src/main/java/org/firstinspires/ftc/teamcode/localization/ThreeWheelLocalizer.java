package org.firstinspires.ftc.teamcode.localization;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.util.Pose2d;

import java.util.Collections;
import java.util.List;

/*
 *    This class takes in 3 tracking encoder wheel positions and and outputs the pose of the robot
 *    in the field coordinates with the origin being where the robot started.
 *
 *
 *    ^
 *    |
 *    |
 *    | +X
 *    |
 *    |     +Y
 *    ---------------->  +90 degrees
 *
 *   Going forward increases the x value and going to the right increases the y value
 *   turning right increases the theta.
 */
public class ThreeWheelLocalizer {
  public static double dTheta = 0;
  double x = 0, y = 0, theta = Math.toRadians(0);
  Telemetry telemetry;
  private double chassisWidth = 38.5; // cm
  private Pose2d poseEstimate;
  private List<Integer> lastWheelPositions;
  private MecanumDrive drive;

  public ThreeWheelLocalizer(MecanumDrive drive) {
    this.telemetry = telemetry;
    this.drive = drive;

    lastWheelPositions = Collections.emptyList();

    // poseEstimate = new Pose2d(143, 143, MyMath.toRadians(135));
    poseEstimate = new Pose2d(0, 0, Math.toRadians(0));
  }

  public Pose2d update() {
    List<Integer> wheelPositions = drive.getTrackingWheelPositions();
    if (!lastWheelPositions.isEmpty()) {

      double dL = wheelPositions.get(0) - lastWheelPositions.get(0);
      double dR = wheelPositions.get(1) - lastWheelPositions.get(1);
      double dM = wheelPositions.get(2) - lastWheelPositions.get(2);

      double dS = (dR + dL) / 2.0;
      dTheta = ((dR - dL) / chassisWidth);

      double avgTheta = theta + dTheta / 2.0;

      double dX = dS * Math.cos(avgTheta) + dM * Math.sin(avgTheta);
      double dY = dS * Math.sin(avgTheta) - dM * Math.cos(avgTheta);

      // Update current robot position.
      x += dX;
      y += dY;
      theta += dTheta;

      poseEstimate = new Pose2d(x, y, theta);
      lastWheelPositions = wheelPositions;
    }
    lastWheelPositions = wheelPositions;

    return poseEstimate;
  }

  public void setEstimatedPosition(Pose2d position) {
    poseEstimate = position;
  }
}
