package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.corningrobotics.enderbots.endercv.CameraViewDisplay
import org.firstinspires.ftc.teamcode.path.PathsRed
import org.firstinspires.ftc.teamcode.path.blockPositionsRed
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import org.firstinspires.ftc.teamcode.vision.FrameGrabber

@Autonomous(name = "Fake Auto", group = "A")
class FakeAuto : AutoOpMode(Pose2d(-20.32, 81.7, Math.PI / 2)) {

    enum class BlockPos {
        ZERO_THREE,
        ONE_FOUR,
        TWO_FIVE,
        NONE,
    }

    private var blockPos: BlockPos = BlockPos.NONE
    private var paths: PathsRed = PathsRed()

    override fun setup() {

    }

    override fun run() {
      robot.drive.grabBlock()
    }
}