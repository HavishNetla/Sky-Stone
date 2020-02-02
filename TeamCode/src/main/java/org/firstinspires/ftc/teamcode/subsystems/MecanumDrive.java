package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.localization.ThreeWheelLocalizer;
import org.firstinspires.ftc.teamcode.path.PathBuilder;
import org.firstinspires.ftc.teamcode.path.PathFollower;
import org.firstinspires.ftc.teamcode.path.PathSegment;
import org.firstinspires.ftc.teamcode.util.Pose2d;
import org.firstinspires.ftc.teamcode.util.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MecanumDrive extends Subsystem {
    public static double[] pathPowers = new double[]{0, 0, 0};
    public static boolean moveToFoundation = false;
    public static boolean moveToEnd = false;
    public static boolean moveOutALittle = false;
    private double angleToTurn = 0.0;
    // Drive Motors
    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;
    // Encoder Wheels
    private DcMotor left;
    private DcMotor right;
    private DcMotor center;
    // Foundation Grabber
    private Servo foundationGrabber;
    // Auxiliary block picker upper
    private Servo rotater;
    private Servo grabber;
    private CRServo tapeCap1;
    private CRServo tapeCap2;

    private DigitalChannel touchSensor;
    private PathFollower pathfollower;
    private Mode mode = Mode.OPEN_LOOP;
    private LocalizerMode localizerMode = LocalizerMode.THREE_WHEEL_LOCALIZER;
    private Vector2d targetPower = new Vector2d(0, 0);
    private double targetC = 0;
    private double[] powers = new double[]{0, 0, 0, 0};
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
    private double rotaterRedPos = 0.0;
    private double grabberRedPos = 0.0;

    private boolean turn = true;
    private boolean turnFunc = false;
    private boolean specialAngle = false;

    private Vector2d pointToGoTo;
    private double goToPreferredAngle;
    private double goToSpeed;
    private double goToTurnSpeed;

    private double turnToAngle;

    private Servo rotaterRed;
    private Servo grabberRed;

    private double tapeCapPower;

    private int loopCount = 0;
    private boolean forwardDirection = false;

    private ElapsedTime eTime = new ElapsedTime();

    public MecanumDrive(Pose2d ogPos, HardwareMap map, Telemetry telemetry) {
        frontLeft = map.get(DcMotorEx.class, "FL");
        frontRight = map.get(DcMotorEx.class, "FR");
        backLeft = map.get(DcMotorEx.class, "BL");
        backRight = map.get(DcMotorEx.class, "BR");

        left = map.get(DcMotor.class, "L");
        right = map.get(DcMotor.class, "R");
        center = map.get(DcMotor.class, "C");

        rotater = map.get(Servo.class, "CR");
        grabber = map.get(Servo.class, "CG");
        tapeCap1 = map.get(CRServo.class, "TC1");
        tapeCap2 = map.get(CRServo.class, "TC2");

        rotaterRed = map.get(Servo.class, "CRR");
        grabberRed = map.get(Servo.class, "CGR");

        foundationGrabber = map.get(Servo.class, "FG");

        touchSensor = map.get(DigitalChannel.class, "CT");

        setMode(Mode.OPEN_LOOP);
        setMode(LocalizerMode.THREE_WHEEL_LOCALIZER);

        localizer = new ThreeWheelLocalizer(this, ogPos, telemetry);

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

        frontLeft.setVelocityPIDFCoefficients(10.0, 3.0, 1.0, 2.0);
        frontRight.setVelocityPIDFCoefficients(10.0, 3.0, 1.0, 2.0);
        backLeft.setVelocityPIDFCoefficients(10.0, 3.0, 1.0, 2.0);
        backRight.setVelocityPIDFCoefficients(10.0, 3.0, 1.0, 2.0);

        resetEncoders();

        stowBlock();
        stowBlockRed();
        openFoundationGrabber();

        // frontLeft.get
    }

    public void velocityControlls() {
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setVelocityPIDFCoefficients(10.0, 3.0, 0.0, 0.0);
        frontRight.setVelocityPIDFCoefficients(10.0, 3.0, 0.0, 0.0);
        backLeft.setVelocityPIDFCoefficients(10.0, 3.0, 0.0, 0.0);
        backRight.setVelocityPIDFCoefficients(10.0, 3.0, 0.0, 0.0);
    }

    public String getPID() {
        return "" + frontLeft.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
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

    public void goToPoint(Vector2d goal, double preferredAngle, double speed, double turnSpeed) {
        PathBuilder t = new PathBuilder(position);
        ArrayList<PathSegment> path =
                t.addPoint(new Vector2d(0.0, 0.0), 0.0, 0.0, 0.0, "THIS IS NOT BIENG USED").create();

        PathFollower pf = new PathFollower(path, 55, "NOT BIENG USED");

        this.isPathFollowingDone = false;
        this.pathfollower = pf;

        this.pointToGoTo = goal;
        this.goToPreferredAngle = preferredAngle;
        this.goToSpeed = speed;
        this.goToTurnSpeed = turnSpeed;
        setMode(Mode.GO_TO_POINT);
    }

    public void turn(double angle) {
        PathBuilder t = new PathBuilder(position);
        ArrayList<PathSegment> path =
                t.addPoint(new Vector2d(0.0, 0.0), 0.0, 0.0, 0.0, "THIS IS NOT BIENG USED").create();

        PathFollower pf = new PathFollower(path, 55, "NOT BIENG USED");

        this.pathfollower = pf;
        this.turnToAngle = angle;
        this.mode = Mode.TURN;
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

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setMode(LocalizerMode mode) {
        this.localizerMode = mode;
    }

    public LocalizerMode getLocalizer() {
        return localizerMode;
    }

    public void setLocalizerConfig(boolean turn) {
        this.turn = turn;
    }

    public boolean getPathStatus() {
        return isPathFollowingDone;
    }

    public void stop() {
        setVelocity(new Vector2d(0, 0), 0);
    }

    public void waitForPathFollower() {
        while (!Thread.currentThread().isInterrupted()
                && (getMode() == Mode.FOLLOW_PATH
                || getMode() == Mode.GO_TO_POINT
                || getMode() == Mode.TURN)) {
            delay((long) 0.005);
        }
        return;
    }

    public void grabFoundation() {
        foundationGrabberPosition = 0.9;
        delay((long) 1.0);
    }

    public void grabFoundationTele() {
        foundationGrabberPosition = 0.9;
    }

    public void openFoundationGrabber() {
        foundationGrabberPosition = 0.5;
        delay((long) 1.0);
    }

    public void openFoundationGrabberTele() {
        foundationGrabberPosition = 0.5;
    }

    public void setRotaterPos(double pos) {
        rotaterPos = pos;
    }

    public void setGrabberPos(double pos) {
        grabberPos = pos;
    }

    public void setRotaterRedPos(double pos) {
        rotaterRedPos = pos;
    }

    public void setGrabberRedPos(double pos) {
        grabberRedPos = pos;
    }

    public boolean getTouchSensorState() {
        return touchSensor.getState();
    }

    public void setTapeCapPower(double power) {
        this.tapeCapPower = power;
    }

    public void readyBlock() {
        setRotaterPos(0.82);
        setGrabberPos(0.0);
        // delay((long) 0.5);
    }

    public void readyBlockNoDelay() {
        setRotaterPos(0.82);
        setGrabberPos(0.0);
    }

    public void grabBlock() {
        setRotaterPos(1.0);
        setGrabberPos(0.8);
        // while (grabber.getPosition()<0.5){
        // delay((long)0.000001);
        // telemetry.addData("rotater:", grabber.getPosition());
        // telemetry.update();
        // }
        specialDelay(0.4);
    }

    public void stowBlock() {
        setRotaterPos(0.3);
        setGrabberPos(0.8);
        // while (rotater.getPosition()>0.4){
        // delay((long)0.000001);
        // telemetry.addData("rotater:", rotater.getPosition());
        // telemetry.update();
        // }
        // delay((long) 1.0);
    }

    public void stowBlockNoDelay() {
        setRotaterPos(0.3);
        setGrabberPos(0.8);
    }

    public void halfPlaceBlock() {
        // Place -Added my Matthew
        setRotaterPos(0.8);
    }

    public void setRotaterForPrep() {
        setRotaterPos(0.6);
    }

    public void releaseBlock() {
        setRotaterPos(0.75);
        setGrabberPos(0.0);
        specialDelay(0.25);
    }

    public void throwBlock() {
        setRotaterPos(0.5);
        setGrabberPos(0.0);
        specialDelay(0.3);
    }

    public void preRelease() {
        setRotaterPos(0.7);
        //setGrabberPos(0.0);
        //specialDelay(0.25);
    }

    public void readyBlockRed() {
        setRotaterRedPos(0.93);
        setGrabberRedPos(0.0);
        delay((long) 1.0);
    }

    public void readyBlockRedNoDelay() {
        setRotaterRedPos(0.93);
        setGrabberRedPos(0.0);
    }

    public void grabBlockRed() {
        setRotaterRedPos(0.96);
        setGrabberRedPos(0.0);
        delay((long) 1.0);
        setGrabberRedPos(1.0);
        delay((long) 1.0);
    }

    public void stowBlockRed() {
        setRotaterRedPos(0.58);
        setGrabberRedPos(1.0);
        delay((long) 1.0);
    }

    public void releaseBlockRed() {
        setRotaterRedPos(0.78);
        setGrabberRedPos(0.0);
        delay((long) 1.0);
    }

    public void stowBlockRedTele() {
        setRotaterRedPos(0.5);
        setGrabberRedPos(0.0);
    }

    public void delay(long s) {
        eTime.reset();
        while (eTime.time() < s && !Thread.currentThread().isInterrupted()) {
            //System.out.println("TIME: " + eTime.time());
        }
    }

    public void specialDelay(double s) {
        System.out.println("TIME: GOT IN HERE");
        eTime.reset();
        System.out.println("TIME: GOT IN HERE2 " + eTime.time() + "time i want to delay: " + s);

        while (eTime.time() < s && !Thread.currentThread().isInterrupted()) {
            System.out.println("TIME: " + eTime.time());
        }
    }


    public void setSpecialAngle(boolean option) {
        this.specialAngle = option;
        System.out.println("statut2: uh");
    }

    public int getLoopCount() {
        return loopCount;
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
                loopCount = 0;
                break;
            case FOLLOW_PATH:
                pathPowers = pathfollower.update(position);
                isPathFollowingDone = pathfollower.getStatus();

                if (!isPathFollowingDone) {
                    internalSetVelocity(
                            new Vector2d(pathPowers[1], -pathPowers[0]), turn || turnFunc ? pathPowers[2] : 0);
                } else {
                    stop();
                }
                break;
            case GO_TO_POINT:
                pathPowers =
                        pathfollower.goToPoint(
                                this.pointToGoTo,
                                position,
                                this.goToPreferredAngle,
                                this.goToSpeed,
                                this.goToTurnSpeed);
                isPathFollowingDone = pathfollower.getStatus();

                if (!isPathFollowingDone) {
                    internalSetVelocity(
                            new Vector2d(pathPowers[1], -pathPowers[0]), turn ? pathPowers[2] : 0.0);
                } else {
                    stop();
                }
                break;
            case TURN:
                pathPowers = pathfollower.turn(position, turnToAngle);
                isPathFollowingDone = pathfollower.getStatus();

                if (!isPathFollowingDone) {
                    internalSetVelocity(new Vector2d(0, 0), pathPowers[2]);
                } else {
                    stop();
                }
                break;
            case JOSTLE:
                loopCount++;
                if (loopCount % 25 != 0) {
                    if (forwardDirection) {
                        internalSetVelocity(new Vector2d(0.05, 0.0), 0.0);
                    } else {
                        internalSetVelocity(new Vector2d(-0.05, 0.0), 0.0);
                    }
                } else {
                    forwardDirection = !forwardDirection;
                }
        }
        updatePowers();

        frontLeft.setPower(powers[0]);
        frontRight.setPower(powers[1]);
        backLeft.setPower(powers[2]);
        backRight.setPower(powers[3]);

        foundationGrabber.setPosition(foundationGrabberPosition);
        rotater.setPosition(rotaterPos);
        grabber.setPosition(grabberPos);

        rotaterRed.setPosition(rotaterRedPos);
        grabberRed.setPosition(grabberRedPos);

        tapeCap1.setPower(tapeCapPower);
        tapeCap2.setPower(tapeCapPower);
    }

    public enum Mode {
        OPEN_LOOP,
        FOLLOW_PATH,
        GO_TO_POINT,
        TURN,
        JOSTLE
    }

    //  public String getStatus() {
    //    return StringUtils.caption("Power", power) + StringUtils.caption("Has Block", hasBlock);
    //  }

    public enum LocalizerMode {
        THREE_WHEEL_LOCALIZER,
        NONE,
    }
}
