package org.firstinspires.ftc.teamcode.vision

import org.corningrobotics.enderbots.endercv.OpenCVPipeline
import org.firstinspires.ftc.teamcode.util.Vector2d
import org.firstinspires.ftc.teamcode.vision.filters.ColorFilter
import org.firstinspires.ftc.teamcode.vision.filters.Filter
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

class FrameGrabber : OpenCVPipeline() {

    private var statusLeft = Status.BLACK
    private var statusRight = Status.BLACK

    private val offset = Vector2d(50.0, 50.0)
    private val offset1 = Vector2d(0.0, 0.0)
    private val threshold = 100.0
    private val colorFiler = ColorFilter()
    private val workingMat = Mat()
    private val displayMat = Mat()
    private val yellowMask = Mat()
    private var sum = 0.0
    private var sum1 = 0.0

    private enum class Status {
        YELLOW,
        BLACK
    }

    override fun processFrame(rgba: Mat, gray: Mat): Mat {
        rgba.copyTo(displayMat)
        rgba.copyTo(workingMat)
        rgba.release()

        colorFiler.processFrame(workingMat.clone(), yellowMask, threshold)

        Imgproc.rectangle(
                displayMat,
                Point(0 + offset.x, 0 + offset.y),
                Point(20 + offset.x, 20 + offset.y),
                Scalar(255.0, 0.0, 0.0),
                1,
                0)

        Imgproc.rectangle(
                displayMat,
                Point(0 + offset1.x, 0 + offset1.y),
                Point(20 + offset1.x, 20 + offset1.y),
                Scalar(0.0, 255.0, 0.0),
                1,
                0)



            var i = offset.x.toInt()
            while (i < 20 + offset.x) {
                var j = offset.y.toInt()
                while (j < 20 + offset.y) {
                    sum += yellowMask.get(i, j)[0]
                    j++
                }
                i++
            }


        var i = offset1.x.toInt()
        while (i < 20 + offset1.x) {
            var j = offset1.y.toInt()
            while (j < 20 + offset1.y) {
                sum1 += yellowMask.get(i, j)[0]
                j++
            }
            i++
        }

        sum /= 400.0
        sum1 /= 400.0


        statusLeft = if (sum > 100) Status.YELLOW else Status.BLACK
        statusRight = if (sum1 > 100) Status.YELLOW else Status.BLACK

        Imgproc.putText(displayMat, "$statusLeft, $statusRight", Point(0.0, (displayMat.height() - 40).toDouble()), 0, 2.0, Scalar(255.0, 255.0, 255.0))
        Imgproc.putText(displayMat, "" + statusLeft, Point(offset.x - 20, offset.y + 20), 0, 1.0, Scalar(255.0, 255.0, 255.0))
        Imgproc.putText(displayMat, "" + statusRight, Point(offset1.x - 20, offset1.y + 20), 0, 1.0, Scalar(255.0, 255.0, 255.0))

        sum = 0.0
        sum1 = 0.0

        return displayMat
    }
}
