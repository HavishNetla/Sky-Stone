package org.firstinspires.ftc.teamcode.ComputerDebuging;

import org.firstinspires.ftc.teamcode.Path.PathBuilder;
import org.firstinspires.ftc.teamcode.Path.PathFollower;
import org.firstinspires.ftc.teamcode.Path.PathSegment;
import org.firstinspires.ftc.teamcode.Util.Pose2d;
import org.firstinspires.ftc.teamcode.Util.Vector2d;

import java.util.ArrayList;

public class MyOpMode extends OpMode {
  public static ArrayList<PathSegment> path;
  public static PathBuilder t = new PathBuilder(new Pose2d(213.36, 152.4, 0));

  @Override
  public void init() {
    path =
        t.addPoint(new Vector2d(213.36, 152.4), "moving forward")
            .addPoint(new Vector2d(335.28, 213.36), "aligning with lander")
            .addPoint(new Vector2d(335.28, 335.28), "scoring in depot")
            .addPoint(new Vector2d(30.48, 335.28), "scoring in depot")
            .create();
    ComputerDebugging.sendPaths(path);
    ComputerDebugging.sendPacket();
  }

  @Override
  public void loop() {
    PathFollower pathFollower = new PathFollower(path, 19.685 * 2.54);
    pathFollower.update(Robot.robotPos);

    ComputerDebugging.sendPaths(path);

    double[] powers = pathFollower.followCurve(0, Robot.robotPos, 0.5, 2);
    ComputerDebugging.sendPoint(pathFollower.lookAheadPoint);
    // double[] powers = pathFollower.goToPoint(new Vector2d(190, 70), Robot.robotPos, 180, 0.25,
    // 1.0);

    Robot.x = powers[0];
    Robot.y = powers[1];
    Robot.c = powers[2];

  }
}
