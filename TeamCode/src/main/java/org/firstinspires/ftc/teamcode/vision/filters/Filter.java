package org.firstinspires.ftc.teamcode.vision.filters;

import org.opencv.core.Mat;

public abstract class Filter {
  public abstract void processFrame(Mat input, Mat mask, double threshold);
}
