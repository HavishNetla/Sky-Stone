package org.firstinspires.ftc.teamcode.Path;

import org.firstinspires.ftc.teamcode.Util.Pose2d;
import org.firstinspires.ftc.teamcode.Util.Vector2d;

import java.util.ArrayList;

public class PathBuilder {
  private Vector2d currentPose;
  private ArrayList<PathSegment> path;

  public PathBuilder(Pose2d pose) {
    this.currentPose = pose.pos();
    path = new ArrayList<>();
  }

  /**
   * @param point Point to go to
   * @param label Keyword or phrase to describe to movement
   * @return Adds the segment to the path
   */
  public PathBuilder addPoint(Vector2d point, String label) {
    PathSegment t = new PathSegment(currentPose, point, label);
    path.add(t);
    currentPose = t.end;

    return this;
  }

  public ArrayList<PathSegment> create() {
    return path;
  }
}
