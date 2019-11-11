package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.localization.ThreeWheelLocalizer;
import org.firstinspires.ftc.teamcode.path.PathFollower;
import org.firstinspires.ftc.teamcode.path.PathSegment;
import org.firstinspires.ftc.teamcode.util.Pose2d;
import org.firstinspires.ftc.teamcode.util.Vector2d;

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
  private double[] powers = new double[]{0,0,0,0};
  private Pose2d position;
  private ThreeWheelLocalizer localizer;
  private Telemetry telemetry;
  private double f = 0;
  public MecanumDrive(HardwareMap map, Telemetry telemetry) {
    frontLeft = map.get(DcMotor.class, "FL");
    frontRight = map.get(DcMotor.class, "FR");
    backLeft = map.get(DcMotor.class, "BL");
    backRight = map.get(DcMotor.class, "BR");
    setMode(Mode.OPEN_LOOP);
    setMode(LocalizerMode.THREE_WHEEL_LOCALIZER);

    localizer = new ThreeWheelLocalizer(this, telemetry);

    frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    this.telemetry = telemetry;

    resetEncoders();
  }

  // ===============================================================================================

  // ===============================================================================================

  // Odometry
  public List<Double> getTrackingWheelPositions() {
    double ratio = (Math.PI * 5.08) / 1440;

    return Arrays.asList(
        -frontLeft.getCurrentPosition() * ratio,
        -frontRight.getCurrentPosition() * ratio,
        -backLeft.getCurrentPosition() * ratio
    );
  }

  public Pose2d getPosition() {
    double ratio = (Math.PI * 5.08) / 1440;
    return new Pose2d(position.getX(), position.getY(), position.getHeading());
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

  public void resetEncoders() {
    frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

  }
  public void setVelocity(Vector2d vel, double omega) {
    internalSetVelocity(vel, omega);
    setMode(Mode.OPEN_LOOP);

    telemetry.addData("vel", vel);
  }

  private void internalSetVelocity(Vector2d vel, double omega) {
    targetPower = vel;
    targetC = omega;
  }
    // ===============================================================================================

  private void updatePowers() {
    powers[0] = targetPower.getY() + targetPower.getX() + targetC;
    powers[1] = targetPower.getY() - targetPower.getX() - targetC;
    powers[2] = targetPower.getY() - targetPower.getX() + targetC;
    powers[3] = targetPower.getY() + targetPower.getX() - targetC;

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

  public String getF() {
    return "f";
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

    updatePowers();
    frontLeft.setPower(powers[0]);
    frontRight.setPower(powers[1]);
    backLeft.setPower(powers[2]);
    backRight.setPower(powers[3]);
  }
}


