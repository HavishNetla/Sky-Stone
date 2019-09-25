package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Localization.ThreeWheelLocalizer;
import org.firstinspires.ftc.teamcode.Path.PathFollower;
import org.firstinspires.ftc.teamcode.Path.PathSegment;
import org.firstinspires.ftc.teamcode.Util.Pose2d;
import org.firstinspires.ftc.teamcode.Util.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MecanumDrive extends Subsystem {

  public enum Mode {
    OPEN_LOOP,
    FOLLOW_PATH,
  }

  public enum LocalizerMode {
    THREE_WHEEL_LOCALIZER,
    NONE,
  }

  private DcMotor frontLeft;
  private DcMotor frontRight;
  private DcMotor backLeft;
  private DcMotor backRight;
  private PathFollower pathfollower;
  private Mode mode = Mode.OPEN_LOOP;
  private LocalizerMode localizerMode = LocalizerMode.THREE_WHEEL_LOCALIZER;
  private Vector2d targetPower = new Vector2d(0, 0);
  private double targetC = 0;
  private double[] powers;
  private Pose2d position;
  private ThreeWheelLocalizer localizer;

  public MecanumDrive(HardwareMap map) {
    frontLeft = map.get(DcMotor.class, "FL");
    frontRight = map.get(DcMotor.class, "FR");
    backLeft = map.get(DcMotor.class, "BL");
    backRight = map.get(DcMotor.class, "BR");
    setMode(Mode.OPEN_LOOP);

    localizer = new ThreeWheelLocalizer(this);
  }

  // ===============================================================================================

  // ===============================================================================================
  // Odometry
  public List<Integer> getTrackingWheelPositions() {
    return Arrays.asList(
        frontLeft.getCurrentPosition(),
        frontRight.getCurrentPosition(),
        backLeft.getCurrentPosition());
  }

  public Pose2d getPosition() {
    return position;
  }

  public void setPosition(Pose2d pos) {
    localizer.setEstimatedPosition(pos);
  }

  public void followPath(ArrayList<PathSegment> path) {
    setMode(Mode.FOLLOW_PATH);

    pathfollower = new PathFollower(path,19.685 * 2.54);
  }

  int loopCount = 0;
  public boolean isRobotStuck() {
    //loop for 100 loop counts
    if (loopCount < 100) {

    }
    loopCount++;

    return false;
  }


  public void setVelocity(Vector2d vel, double omega) {
    internalSetVelocity(vel, omega);
    setMode(Mode.OPEN_LOOP);
  }

  private void internalSetVelocity(Vector2d vel, double omega) {
    this.targetPower = vel;
    this.targetC = omega;
  }
    // ===============================================================================================

  private void updatePowers() {
    powers[0] = targetPower.x - targetPower.y - targetC;
    powers[1] = targetPower.x + targetPower.y - targetC;
    powers[2] = targetPower.x - targetPower.y + targetC;
    powers[3] = targetPower.x + targetPower.y + targetC;

    double max =
        Collections.max(
            Arrays.asList(
                1.0,
                Math.abs(powers[0]),
                Math.abs(powers[1]),
                Math.abs(powers[2]),
                Math.abs(powers[3])));
  }

  // GETTERS =======================================================================================
  public double[] getPowers() {
    return powers;
  }

  public Mode getMode() {
    return mode;
  }

  private void setMode(Mode mode) {
    this.mode = mode;
  }

  private void setMode(LocalizerMode mode) {
    this.localizerMode = mode;
  }

  public LocalizerMode getLocalizer() {
    return localizerMode;
  }



  @Override
  public void update() {
    // update odometry position
    switch (localizerMode) {
      case THREE_WHEEL_LOCALIZER:
        position = localizer.update();
        break;
      case NONE:
        break;
    }

    switch (mode) {
      case OPEN_LOOP:
        updatePowers();
        break;
      case FOLLOW_PATH:
        pathfollower.update(position);
        break;
    }

    frontLeft.setPower(powers[0]);
    frontRight.setPower(powers[1]);
    backLeft.setPower(powers[2]);
    backRight.setPower(powers[3]);
  }
}


