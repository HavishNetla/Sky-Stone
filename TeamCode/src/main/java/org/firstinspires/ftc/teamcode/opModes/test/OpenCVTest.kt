package org.firstinspires.ftc.teamcode.opModes.test

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

import org.corningrobotics.enderbots.endercv.CameraViewDisplay
import org.firstinspires.ftc.teamcode.vision.FrameGrabber

@TeleOp(name = "OpenCV Test" + "Drive", group = "T")
class OpenCVTest : OpMode() {

    internal var frameGrabber = FrameGrabber()

    override fun init() {
        // can replace with ActivityViewDisplay.getInstance() for fullscreen
        frameGrabber.init(hardwareMap.appContext, CameraViewDisplay.getInstance())
        // start the vision system
        frameGrabber.enable()
    }

    override fun init_loop() {

        if (gamepad1.dpad_up) {
            frameGrabber.offset.y = frameGrabber.offset.y - 0.5
        }
        if (gamepad1.dpad_down) {
            frameGrabber.offset.y = frameGrabber.offset.y + 0.5
        }
        if (gamepad1.dpad_left) {
            frameGrabber.offset.x = frameGrabber.offset.x - 0.5
        }
        if (gamepad1.dpad_right) {
            frameGrabber.offset.x = frameGrabber.offset.x + 0.5
        }


        if (gamepad2.y) {
            frameGrabber.threshold++
        } else if (gamepad2.a) {
            frameGrabber.threshold--
        }

        if (gamepad2.dpad_up) {
            frameGrabber.offset1.y = frameGrabber.offset1.y - 0.5
        }
        if (gamepad2.dpad_down) {
            frameGrabber.offset1.y = frameGrabber.offset1.y + 0.5
        }
        if (gamepad2.dpad_left) {
            frameGrabber.offset1.x = frameGrabber.offset1.x - 0.5
        }
        if (gamepad2.dpad_right) {
            frameGrabber.offset1.x = frameGrabber.offset1.x + 0.5
        }


        telemetry.addData("THRESHOLD", frameGrabber.threshold)

        telemetry.addData(
                "stat", frameGrabber.statusLeft.toString() + ", " + frameGrabber.statusRight)
    }

    override fun start() {
        frameGrabber.disable()
    }

    override fun loop() {

        telemetry.addData("STATUS", "RUNNING OPENCV TEST")
    }
}
