package org.firstinspires.ftc.teamcode.opModes.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.corningrobotics.enderbots.endercv.CameraViewDisplay;
import org.firstinspires.ftc.teamcode.vision.FrameGrabber;

@TeleOp(name = "OpenCV Test" + "Drive", group = "T")
public class OpenCVTest extends OpMode {

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
      frameGrabber.offset.setY(frameGrabber.offset.getY() - 0.5);
    }
    if (gamepad1.dpad_down) {
      frameGrabber.offset.setY(frameGrabber.offset.getY() + 0.5);
    }
    if (gamepad1.dpad_left) {
      frameGrabber.offset.setX(frameGrabber.offset.getX() - 0.5);
    }
    if (gamepad1.dpad_right) {
      frameGrabber.offset.setX(frameGrabber.offset.getX() + 0.5);
    }


    if(gamepad2.y) {
      frameGrabber.threshold++;
    } else if(gamepad2.a) {
      frameGrabber.threshold--;
    }

    if (gamepad2.dpad_up) {
      frameGrabber.offset1.setY(frameGrabber.offset1.getY() - 0.5);
    }
    if (gamepad2.dpad_down) {
      frameGrabber.offset1.setY(frameGrabber.offset1.getY() + 0.5);
    }
    if (gamepad2.dpad_left) {
      frameGrabber.offset1.setX(frameGrabber.offset1.getX() - 0.5);
    }
    if (gamepad2.dpad_right) {
      frameGrabber.offset1.setX(frameGrabber.offset1.getX() + 0.5);
    }


    telemetry.addData("THRESHOLD",  frameGrabber.threshold);

    telemetry.addData(
        "array", frameGrabber.sum);
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
