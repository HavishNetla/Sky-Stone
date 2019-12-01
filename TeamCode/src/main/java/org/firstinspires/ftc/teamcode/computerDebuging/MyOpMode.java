package org.firstinspires.ftc.teamcode.computerDebuging;

import org.firstinspires.ftc.teamcode.path.PathBuilder;
import org.firstinspires.ftc.teamcode.path.PathFollower;
import org.firstinspires.ftc.teamcode.path.PathSegment;
import org.firstinspires.ftc.teamcode.util.Pose2d;
import org.firstinspires.ftc.teamcode.util.Vector2d;

import java.util.ArrayList;

public class MyOpMode extends OpMode {
  public static ArrayList<PathSegment> path;
  public static PathBuilder t = new PathBuilder(new Pose2d(15.0, 15.0, 0));

  @Override
  public void init() {
        path = t.addPoint(new Vector2d(61 * 2, 30.48 * 2), "moving forward")
                .addPoint(new Vector2d(30 * 2, 45.72 * 2), "moving forward")
                .addPoint(new Vector2d(45.72 * 2, 152.4 * 2), "moving forward")
                .create();

//    path = t.addPoint(new Vector2d(15, 200), "moving forward").create();

    ComputerDebugging.sendPaths(path);
    ComputerDebugging.sendPacket();
  }

  @Override
  public void loop() {
    PathFollower pathFollower = new PathFollower(path, 10.0);

    ComputerDebugging.sendPaths(path);

        double[] powers = pathFollower.followCurve(0, Robot.robotPos, 0.5, 2);
//    double[] powers =
//        PathFollower.goToPoint(new Vector2d(15.0, 200.0), Robot.robotPos, 0, 0.5, 2.0);
    ComputerDebugging.sendPoint(pathFollower.lookAheadPoint);

    Robot.x = powers[0];
    Robot.y = powers[1];
    Robot.c = powers[2];
  }
}
