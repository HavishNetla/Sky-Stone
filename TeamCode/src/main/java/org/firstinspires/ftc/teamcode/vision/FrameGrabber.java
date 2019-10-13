package org.firstinspires.ftc.teamcode.vision;

import org.corningrobotics.enderbots.endercv.OpenCVPipeline;
import org.firstinspires.ftc.teamcode.util.Vector2d;
import org.firstinspires.ftc.teamcode.vision.filters.ColorFilter;
import org.firstinspires.ftc.teamcode.vision.filters.Filter;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class FrameGrabber extends OpenCVPipeline {
  private enum Status {
    YELLOW,
    BLACK,
  }

  public Status statusLeft = Status.BLACK;
  public Status statusRight = Status.BLACK;

  public Vector2d offset = new Vector2d(50, 50);
  public Vector2d offset1 = new Vector2d(0, 0);
  public double threshold = 100;

  private Filter colorFiler = new ColorFilter();
  private Mat workingMat = new Mat();
  private Mat displayMat = new Mat();
  private Mat yellowMask = new Mat();

  private double sum = 0;
  private double sum1 = 0;

  @Override
  public Mat processFrame(Mat rgba, Mat gray) {
    rgba.copyTo(displayMat);
    rgba.copyTo(workingMat);
    rgba.release();

    colorFiler.processFrame(workingMat.clone(), yellowMask, threshold);

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


    for (int i = (int) offset.getX(); i < 20 + offset.getX(); i++) {
      for (int j = (int) offset.getY(); j < 20 + offset.getY(); j++) {
        sum += yellowMask.get(i, j)[0];
      }
    }

    for (int i = (int) offset1.getX(); i < 20 + offset1.getX(); i++) {
      for (int j = (int) offset1.getY(); j < 20 + offset1.getY(); j++) {
        sum1 += yellowMask.get(i, j)[0];
      }
    }

    sum /= 400;
    sum1 /= 400;


    statusLeft = sum > 100 ? Status.YELLOW : Status.BLACK;
    statusRight = sum1 > 100 ? Status.YELLOW : Status.BLACK;

    Imgproc.putText(displayMat, "" + statusLeft + ", " + statusRight, new Point(0, displayMat.height() - 40), 0, 2, new Scalar(255, 255, 255));
    Imgproc.putText(displayMat, "" + statusLeft, new Point(offset.getX() - 20, offset.getY() + 20), 0, 1, new Scalar(255, 255, 255));
    Imgproc.putText(displayMat, "" + statusRight, new Point(offset1.getX() - 20, offset1.getY() + 20), 0, 1, new Scalar(255, 255, 255));

    sum = 0;
    sum1 = 0;

    return displayMat;
  }
}
