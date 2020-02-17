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

  //added for telementry
  public static double dY;
  public static double dX;
  public static double dL;
  public static double dR;

  public static double x = 20.32, y = 81.7, theta = -Math.PI / 2;
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
      //original -FAIL
      //double c = 23.1;
      //new -WORKS BRUH HAVISH!!!!!!!!!!
      double c= 19.05;

      dL = wheelPositions.get(0) - lastWheelPositions.get(0);
      dR = wheelPositions.get(1) - lastWheelPositions.get(1);

      dTheta = ((dR - dL) / chassisWidth);

      double dM = wheelPositions.get(2) - lastWheelPositions.get(2) - (c * dTheta);

      double dS = (dR + dL) / 2.0;

      double avgTheta = theta + dTheta/ 2.0;

      //Original
       dY = dS * Math.sin(avgTheta) - dM * Math.cos(avgTheta);
       dX = dS * Math.cos(avgTheta) + dM * Math.sin(avgTheta);

      //dY=dS * Math.cos(theta) + Math.tan(dTheta) * Math.sin(theta) * dS - dM * Math.cos(avgTheta);

//      dY = dS * Math.sin(dTheta) + dM * Math.cos(dTheta); //changed from - to + :Matt
//      dX = dS * Math.cos(dTheta) + dM * Math.sin(dTheta);

      //reverse of above :Matthew
//      dY = -1*(dS * Math.cos(dTheta) + dM * Math.sin(dTheta)); //changed from - to + :Matt
//      dX = -1*(dS * Math.sin(dTheta) + dM * Math.cos(dTheta));

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
