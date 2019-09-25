package org.firstinspires.ftc.teamcode.Vision;

import org.corningrobotics.enderbots.endercv.OpenCVPipeline;
import org.firstinspires.ftc.teamcode.Util.Vector2d;
import org.firstinspires.ftc.teamcode.Vision.Filters.ColorFilter;
import org.firstinspires.ftc.teamcode.Vision.Filters.Filter;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class FrameGrabber extends OpenCVPipeline {
  public static Vector2d offset = new Vector2d(0, 0);
  public static double[] f = {0};
  public static double len = 0;
  public static String color = "";
  public double threshold = 100;
  private Filter colorFiler = new ColorFilter();
  private Mat workingMat = new Mat();
  private Mat displayMat = new Mat();
  private Mat yellowMask = new Mat();
  private Mat hiarchy = new Mat();
  private double sum = 0;

  @Override
  public Mat processFrame(Mat rgba, Mat gray) {
    rgba.copyTo(displayMat);
    rgba.copyTo(workingMat);
    rgba.release();

    colorFiler.processFrame(workingMat.clone(), yellowMask, threshold);

    Imgproc.rectangle(
        yellowMask,
        new Point(20 + offset.x, 20 + offset.y),
        new Point(40 + offset.x, 40 + offset.y),
        new Scalar(255, 0, 0),
        1,
        0);

    for (int i = (int) offset.x; i < 10; i++) {
      for (int j = (int) offset.y; j < 10; j++) {
        sum += yellowMask.get(i, j)[0];
      }
    }
    sum /= 100;
    len = sum;
    if (sum > 100) {
      color = "yellow";
    } else {
      color = "not yellow";
    }

    return yellowMask;
  }
}
