package org.firstinspires.ftc.teamcode.opModes.test

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.corningrobotics.enderbots.endercv.CameraViewDisplay
import org.firstinspires.ftc.teamcode.vision.FrameGrabber

@TeleOp(name = "OpenCV Test" + "Drive", group = "T")
class OpenCVTest : OpMode() {
    var frameGrabber = FrameGrabber()

    enum class BlockPos {
        ZERO_THREE,
        ONE_FOUR,
        TWO_FIVE,
        NONE,
    }

    var blockPos: BlockPos = BlockPos.NONE

    override fun init() {
        frameGrabber.init(hardwareMap.appContext, CameraViewDisplay.getInstance())
        frameGrabber.enable()
    }

    override fun init_loop() {
        when {
            gamepad1.dpad_up -> frameGrabber.offset.y -= 0.5
            gamepad1.dpad_down -> frameGrabber.offset.y += 0.5
            gamepad1.dpad_left -> frameGrabber.offset.x -= 0.5
            gamepad1.dpad_right -> frameGrabber.offset.x += 0.5

            gamepad2.dpad_up -> frameGrabber.offset1.y -= 0.5
            gamepad2.dpad_down -> frameGrabber.offset1.y += 0.5
            gamepad2.dpad_left -> frameGrabber.offset1.x -= 0.5
            gamepad2.dpad_right -> frameGrabber.offset1.x += 0.5

            gamepad2.y -> frameGrabber.threshold++
            gamepad2.a -> frameGrabber.threshold--
        }

        telemetry.addData("THRESHOLD", frameGrabber.threshold)

        telemetry.addData(
                "stat", frameGrabber.statusLeft.toString() + ", " + frameGrabber.statusRight)

        var sL = frameGrabber.statusLeft.toString()
        var sR = frameGrabber.statusRight.toString()
        blockPos = if (sL == "YELLOW" && sR == "YELLOW") {
            BlockPos.ZERO_THREE
        } else if (sL == "YELLOW" && sR == "BLACK") {
            BlockPos.ONE_FOUR
        } else if (sL == "BLACK" && sR == "YELLOW") {
            BlockPos.TWO_FIVE
        } else {
            BlockPos.NONE
        }
        telemetry.addData("Block positions", blockPos)

        /*
        blue: none -> posA
        blue: back,yellow -> posC
        blue: yellow, black -> posB

        red: none -> posA
        red: black.yellow -> posB
        red: yellow,black -> posC
         */
    }

    override fun start() {
        frameGrabber.disable()
    }

    override fun loop() {
        telemetry.addData("STATUS", "RUNNING OPENCV TEST")
    }


}
