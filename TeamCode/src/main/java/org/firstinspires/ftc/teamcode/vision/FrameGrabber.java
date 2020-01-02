package org.firstinspires.ftc.teamcode.vision;

import org.corningrobotics.enderbots.endercv.OpenCVPipeline;
import org.firstinspires.ftc.teamcode.util.Vector2d;
import org.firstinspires.ftc.teamcode.vision.filters.ColorFilter;
import org.firstinspires.ftc.teamcode.vision.filters.Filter;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class FrameGrabber extends OpenCVPipeline {
  public Status statusLeft = Status.BLACK;
  public Status statusRight = Status.BLACK;

  public State locations = State.PATTERN_UGHASG;
  public Vector2d offset = new Vector2d(50, 50);
  public Vector2d offset1 = new Vector2d(0, 0);
  public double threshold = 100;
  public double sum = 0;
  public double sum1 = 0;
  public double sumsum = 0;
  private Filter colorFiler = new ColorFilter();
  private Mat displayMat = new Mat();
  private Mat workingMat = new Mat();
  private Mat yellowMask = new Mat();

  @Override
  public Mat processFrame(Mat rgba, Mat gray) {
    rgba.copyTo(displayMat);
    rgba.release();

    colorFiler.processFrame(displayMat.clone(), yellowMask, threshold);

    Imgproc.rectangle(
        displayMat,
        new Point(0 + offset.getX(), 0 + offset.getY()),
        new Point(20 + offset.getX(), 20 + offset.getY()),
        new Scalar(255, 0, 0),
        1,
        0);

    Imgproc.rectangle(
        displayMat,
        new Point(0 + offset1.getX(), 0 + offset1.getY()),
        new Point(20 + offset1.getX(), 20 + offset1.getY()),
        new Scalar(0, 255, 0),
        1,
        0);

    workingMat = yellowMask.submat(new Rect((int) offset.getX(), (int) offset.getY(), 20, 20));
    sum = Core.mean(workingMat).val[0];

    workingMat = yellowMask.submat(new Rect((int) offset1.getX(), (int) offset1.getY(), 20, 20));
    sum1 = Core.mean(workingMat).val[0];

    statusLeft = sum == 255 ? Status.YELLOW : Status.BLACK;
    statusRight = sum1 == 255 ? Status.YELLOW : Status.BLACK;

    System.out.println("asdasd: " + sumsum);

    Imgproc.putText(
        displayMat,
        "" + statusLeft + ", " + statusRight,
        new Point(0, displayMat.height() - 40),
        0,
        2,
        new Scalar(255, 255, 255));
    Imgproc.putText(
        displayMat,
        "" + statusLeft,
        new Point(offset.getX() - 20, offset.getY() + 20),
        0,
        1,
        new Scalar(255, 255, 255));
    Imgproc.putText(
        displayMat,
        "" + statusRight,
        new Point(offset1.getX() - 20, offset1.getY() + 20),
        0,
        1,
        new Scalar(255, 255, 255));

    sum = 0;
    sum1 = 0;
    sumsum = 0;


    return displayMat;
  }

  public enum State {
    PATTERM_A,
    PATTERN_B,
    PATTERN_C,
    PATTERN_UGHASG,
  }

  private enum Status {
    YELLOW,
    BLACK,
  }
}
