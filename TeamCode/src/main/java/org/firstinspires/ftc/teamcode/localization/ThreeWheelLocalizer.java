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
  double x = 20.32, y = 81.7, theta = -Math.PI / 2;
  Telemetry telemetry;
  private double chassisWidth = 34.235; // cm
  private Pose2d poseEstimate;
  private List<Double> lastWheelPositions;
  private MecanumDrive drive;

  public ThreeWheelLocalizer(MecanumDrive drive, Pose2d ogPos, Telemetry telemetry) {
    this.telemetry = telemetry;
    this.drive = drive;

    lastWheelPositions = Collections.emptyList();

    // poseEstimate = new Pose2d(20.32, 81.7, -Math.PI / 2);
    poseEstimate = ogPos;

    this.x = ogPos.getX();
    this.y = ogPos.getY();
    this.theta = ogPos.getHeading();
  }

  public Pose2d update() {
    List<Double> wheelPositions = drive.getTrackingWheelPositions();
    if (!lastWheelPositions.isEmpty()) {
      double c = 23.1 * 2 * Math.PI;
      double dL = wheelPositions.get(0) - lastWheelPositions.get(0);
      double dR = wheelPositions.get(1) - lastWheelPositions.get(1);

      dTheta = ((dR - dL) / chassisWidth);

      double dM = wheelPositions.get(2) - lastWheelPositions.get(2) - (c / (2 * Math.PI) * dTheta);

      double dS = (dR + dL) / 2.0;

      // double avgTheta = theta + dTheta / 2.0;

      double dX = dM, dY = dS;
      if (Math.abs(dTheta) > 0) {
        double movmentRadius = (dR + dL) / (2 * dTheta);
        double strafeRadius = dM / dTheta;

        dY = (movmentRadius * Math.sin(dTheta)) - (strafeRadius * (1 - Math.cos(dTheta)));
        dX = (movmentRadius * (1 - Math.cos(dTheta))) + (strafeRadius * Math.sin(dTheta));
      }

      // Update current robot position.
      x += (Math.cos(theta) * dY) + (Math.sin(theta) * dX);
      y += (Math.sin(theta) * dY) - (Math.cos(theta) * dX);
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
