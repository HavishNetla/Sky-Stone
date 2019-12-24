package org.firstinspires.ftc.teamcode.computerDebuging;

import org.firstinspires.ftc.teamcode.path.PathBuilder;
import org.firstinspires.ftc.teamcode.path.PathFollower;
import org.firstinspires.ftc.teamcode.path.PathSegment;
import org.firstinspires.ftc.teamcode.util.Pose2d;
import org.firstinspires.ftc.teamcode.util.Vector2d;

import java.util.ArrayList;

public class MyOpMode extends OpMode {
  public static ArrayList<PathSegment> path;
  public static PathBuilder t = new PathBuilder(new Pose2d(15.0, 15.0, Math.toRadians(90)));

  @Override
  public void init() {
    path =
        t.addPoint(new Vector2d(15, 100.0), "moving forward")
            .addPoint(new Vector2d(100.0, 100.0), "f")
            .create();

    ComputerDebugging.sendPaths(path);
    ComputerDebugging.sendPacket();
  }

  @Override
  public void loop() {
    PathFollower pathFollower = new PathFollower(path, 10.0, "asd");

    ComputerDebugging.sendPaths(path);

    double[] powers = pathFollower.followCurve(0, Robot.robotPos, 0.5, 2);
    ComputerDebugging.sendPoint(pathFollower.getLookAheadPoint());

    System.out.println("Pos: " + Robot.robotPos);
    System.out.println("0: " + powers[0] + ", 1:" + powers[1] + ", 2:" + powers[2]);
    Robot.x = powers[1];
    Robot.y = powers[0];
    Robot.c = powers[2];
  }
}
