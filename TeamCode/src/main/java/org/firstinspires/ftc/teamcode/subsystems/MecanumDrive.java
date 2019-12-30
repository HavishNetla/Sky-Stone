package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.localization.ThreeWheelLocalizer;
import org.firstinspires.ftc.teamcode.path.PathFollower;
import org.firstinspires.ftc.teamcode.util.Pose2d;
import org.firstinspires.ftc.teamcode.util.Vector2d;

import java.util.Arrays;
import java.util.List;

public class MecanumDrive extends Subsystem {
  public static double[] pathPowers = new double[] {0, 0, 0};

  // Drive Motors
  private DcMotor frontLeft;
  private DcMotor frontRight;
  private DcMotor backLeft;
  private DcMotor backRight;

  // Encoder Wheels
  private DcMotor left;
  private DcMotor right;
  private DcMotor center;

  // Foundation Grabber
  private Servo foundationGrabber;

  // Auxiliary block picker upper
  private Servo rotater;
  private Servo grabber;

  private DigitalChannel touchSensor;

  private PathFollower pathfollower;
  private Mode mode = Mode.OPEN_LOOP;
  private LocalizerMode localizerMode = LocalizerMode.THREE_WHEEL_LOCALIZER;
  private Vector2d targetPower = new Vector2d(0, 0);
  private double targetC = 0;
  private double[] powers = new double[] {0, 0, 0, 0};
  private Pose2d position;
  private ThreeWheelLocalizer localizer;
  private Telemetry telemetry;
  private double followAngle = 0;
  private double pathSpeed = 0.5;
  private double turnSpeed = 0.75;
  private boolean isPathFollowingDone = false;
  private double foundationGrabberPosition = 0.5;
  private double rotaterPos = 0.0;
  private double grabberPos = 0.0;
  private boolean turn = true;

  public MecanumDrive(HardwareMap map, Telemetry telemetry) {
    frontLeft = map.get(DcMotor.class, "FL");
    frontRight = map.get(DcMotor.class, "FR");
    backLeft = map.get(DcMotor.class, "BL");
    backRight = map.get(DcMotor.class, "BR");

    left = map.get(DcMotor.class, "L");
    right = map.get(DcMotor.class, "R");
    center = map.get(DcMotor.class, "C");

    rotater = map.get(Servo.class, "CR");
    grabber = map.get(Servo.class, "CG");

    // foundationGrabber = map.get(Servo.class, "FG");

    touchSensor = map.get(DigitalChannel.class, "CT");

    setMode(Mode.OPEN_LOOP);
    setMode(LocalizerMode.THREE_WHEEL_LOCALIZER);

    localizer = new ThreeWheelLocalizer(this, telemetry);

    frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

    frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    this.telemetry = telemetry;

    resetEncoders();

    setRotaterPos(0.5);
    setGrabberPos(0.3);
  }

  // Odometry
  public List<Double> getTrackingWheelPositions() {
    double ratio = (Math.PI * 5.08) / 1440;

    return Arrays.asList(
        -left.getCurrentPosition() * ratio,
        -right.getCurrentPosition() * ratio,
        -center.getCurrentPosition() * ratio);
  }

  // ===============================================================================================

  // ===============================================================================================

  public Pose2d getPosition() {
    double ratio = (Math.PI * 5.08) / 1440;
    return new Pose2d(position.getX(), position.getY(), position.getHeading());
  }

  public void setPosition(Pose2d pos) {
    localizer.setEstimatedPosition(pos);
  }

  public void followPath(PathFollower pf) {
    isPathFollowingDone = false;
    this.pathfollower = pf;
    setMode(Mode.FOLLOW_PATH);
  }

  public boolean isRobotStuck() {
    return false;
  }

  public void resetEncoders() {
    left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    center.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    center.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
  }

  public void setVelocity(Vector2d vel, double omega) {
    internalSetVelocity(vel, omega);
    setMode(Mode.OPEN_LOOP);
  }

  private void internalSetVelocity(Vector2d vel, double omega) {
    targetPower = vel;
    targetC = omega;
  }

  private void updatePowers() {
    powers[0] = targetPower.getY() - targetPower.getX() - targetC;
    powers[1] = targetPower.getY() + targetPower.getX() + targetC;
    powers[2] = targetPower.getY() + targetPower.getX() - targetC;
    powers[3] = targetPower.getY() - targetPower.getX() + targetC;
  }

  // GETTERS =======================================================================================
  public double[] getPowers() {
    return powers;
  }
  // ===============================================================================================

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

  public void setLocalizerConfig(double followAngle, double speed, double turnSpeed, boolean turn) {
    this.followAngle = followAngle;
    this.pathSpeed = speed;
    this.turnSpeed = turnSpeed;
    this.turn = turn;
  }

  public boolean getPathStatus() {
    return isPathFollowingDone;
  }

  public void stop() {
    setVelocity(new Vector2d(0, 0), 0);
  }

  public void waitForPathFollower() {
    while (!Thread.currentThread().isInterrupted() && getMode() == Mode.FOLLOW_PATH) {
      try {
        Thread.sleep(5);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    return;
  }

  public void grabFoundation() {
    foundationGrabberPosition = 1.0;
  }

  public void openFoundationGrabber() {
    foundationGrabberPosition = 0.0;
  }

  public void setRotaterPos(double pos) {
    rotaterPos = pos;
  }

  public void setGrabberPos(double pos) {
    grabberPos = pos;
  }

  public boolean getTouchSensorState() {
    return touchSensor.getState();
  }

  public void grabBlock() {
    setRotaterPos(1.0);
    setGrabberPos(0.55);
    delay((long) 1.0);
    setGrabberPos(0.3);
    delay((long) 1.0);
  }

  public void stowBlock() {
    setRotaterPos(0.5);
    setGrabberPos(0.3);
    delay((long) 1.0);
  }

  public void releaseBlock() {
    setRotaterPos(0.8);
    setGrabberPos(0.55);
    delay((long) 1.0);
  }

  public void delay(long s) {
    try {
      Thread.sleep(s * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
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
        pathPowers = pathfollower.update(followAngle, position, pathSpeed, turnSpeed);

        isPathFollowingDone = pathfollower.getStatus();

        if (!isPathFollowingDone) {
          internalSetVelocity(
              new Vector2d(pathPowers[1], -pathPowers[0]), turn ? pathPowers[2] : 0);
        } else {
          stop();
        }
        updatePowers();

        break;
    }

    frontLeft.setPower(powers[0]);
    frontRight.setPower(powers[1]);
    backLeft.setPower(powers[2]);
    backRight.setPower(powers[3]);

    // foundationGrabber.setPosition(foundationGrabberPosition);
    rotater.setPosition(rotaterPos);
    grabber.setPosition(grabberPos);
  }

  public enum Mode {
    OPEN_LOOP,
    FOLLOW_PATH,
  }

  //  public String getStatus() {
  //    return StringUtils.caption("Power", power) + StringUtils.caption("Has Block", hasBlock);
  //  }

  public enum LocalizerMode {
    THREE_WHEEL_LOCALIZER,
    NONE,
  }
}
