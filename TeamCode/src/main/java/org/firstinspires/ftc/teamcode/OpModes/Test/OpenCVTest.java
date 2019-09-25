package org.firstinspires.ftc.teamcode.OpModes.Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.teamcode.Vision.FrameGrabber;

@TeleOp(name = "OpenCV Test" + "Drive", group = "T")
public class OpenCVTest extends OpMode {
  public double offSetX = 0;
  public double offSetY = 0;
  FrameGrabber frameGrabber = new FrameGrabber();

  @Override
  public void init() {
    // can replace with ActivityViewDisplay.getInstance() for fullscreen
    frameGrabber.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
    // start the vision system
    frameGrabber.enable();
  }

  @Override
  public void init_loop() {
    if (gamepad1.dpad_up) {
      offSetY -= 0.5;
    }
    if (gamepad1.dpad_down) {
      offSetY += 0.5;
    }
    if (gamepad1.dpad_left) {
      offSetX -= 0.5;
    }
    if (gamepad1.dpad_right) {
      offSetX += 0.5;
    }

    frameGrabber.offset.x = offSetX;
    frameGrabber.offset.y = offSetY;

    telemetry.addData("OFFSET", frameGrabber.len);

    telemetry.addData(
        "array", frameGrabber.color);
  }

  @Override
  public void start() {
    frameGrabber.disable();
  }

  @Override
  public void loop() {

    telemetry.addData("STATUS", "RUNNING OPENCV TEST");
  }
}
