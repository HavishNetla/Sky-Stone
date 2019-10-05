package org.firstinspires.ftc.teamcode.Vision.Filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ColorFilter extends Filter {
  private List<Mat> channels = new ArrayList<>();

  @Override
  public void processFrame(Mat input, Mat mask, double threshold) {
    channels = new ArrayList<>();

    if (threshold == 0) {
      // default
      threshold = 70;
    }

    Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2YUV);
    Imgproc.GaussianBlur(input, input, new Size(3, 3), 0);
    Core.split(input, channels);

    if (channels.size() > 0) {
      Imgproc.threshold(channels.get(1), mask, threshold, 255, Imgproc.THRESH_BINARY_INV);
    }

    channels.get(0).release();
    channels.get(1).release();
    channels.get(2).release();

    input.release();
  }
}
