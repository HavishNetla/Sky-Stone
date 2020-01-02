package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.corningrobotics.enderbots.endercv.CameraViewDisplay
import org.firstinspires.ftc.teamcode.computerDebuging.ComputerDebugging
import org.firstinspires.ftc.teamcode.path.PathBuilder
import org.firstinspires.ftc.teamcode.path.PathFollower
import org.firstinspires.ftc.teamcode.path.PathSegment
import org.firstinspires.ftc.teamcode.path.Paths
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive.*
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import org.firstinspires.ftc.teamcode.vision.FrameGrabber
import java.util.*

@Deprecated(message = "look at BlueAuto.kt for not bad code")
@Autonomous(name = "Main Auto", group = "A")
class MainAuto : AutoOpMode() {

    enum class BlockPos {
        ZERO_THREE,
        ONE_FOUR,
        TWO_FIVE,
        NONE,
    }

    var blockPos: BlockPos = BlockPos.NONE

    // ====================================================

    private var blockPositions: List<Vector2d> = listOf(
            Vector2d(100.14, 23.668),
            Vector2d(100.14, 43.985),
            Vector2d(100.14, 64.305),
            Vector2d(100.14, 84.625),
            Vector2d(100.14, 104.945),
            Vector2d(100.14, 125.265)
    )

    private lateinit var pathFollower: PathFollower
    private lateinit var pathFollower1: PathFollower
    private lateinit var pathFollower2: PathFollower
    private lateinit var pathFollower3: PathFollower

    private lateinit var computerDebugging: ComputerDebugging

    private var t = PathBuilder(Pose2d(20.32, 81.7, 0.0))
    private var path: ArrayList<PathSegment> = t
            .addPoint(blockPositions[0], Math.toRadians(70.0), 0.3, 0.75, "moving forward")
            .create()

    private var t1 = PathBuilder(Pose2d(103.14, 84.625, 0.0))
    private var path1: ArrayList<PathSegment> = t1
            .addPoint(Vector2d(80.0, 132.08), -Math.PI, 0.25, 0.25, "moving forward1")
            .addPoint(Vector2d(80.0, 220.98), -Math.PI, 0.25, 0.25, "moving forward2")
            .addPoint(Vector2d(105.0, 320.72), -Math.PI, 0.25, 0.25, "moving forward3")
            .create()

    private var t2 = PathBuilder(Pose2d(105.0, 320.72, 0.0))
    private var path2: ArrayList<PathSegment> = t2
            .addPoint(Vector2d(65.14, 289.72), Math.toRadians(-175.0), 0.25, 2.0, "moving forward2")
            .create()

    private var t3 = PathBuilder(Pose2d(65.14, 289.72, 0.0))
    private var path3: ArrayList<PathSegment> = t3
            .addPoint(Vector2d(78.9, 160.88), Math.toRadians(0.0), 0.25, 0.7, "moving forward")
            .create()

    private var paths: Paths = Paths()

    override fun setup() {
        pathFollower = PathFollower(path, 55.0, "FIrst")
        pathFollower1 = PathFollower(path1, 55.0, "Second")
        pathFollower2 = PathFollower(path2, 15.0, "Second")
        pathFollower3 = PathFollower(path3, 100.0, "Second")
        //pathFollower1 = PathFollower(path1, 55.0)

        var frameGrabber = FrameGrabber()
        frameGrabber.init(hardwareMap.appContext, CameraViewDisplay.getInstance())
        frameGrabber.enable()
        while (!isStarted()) {
            when {
                gamepad1.dpad_up -> frameGrabber.offset.y -= 0.001
                gamepad1.dpad_down -> frameGrabber.offset.y += 0.001
                gamepad1.dpad_left -> frameGrabber.offset.x -= 0.001
                gamepad1.dpad_right -> frameGrabber.offset.x += 0.001

                gamepad2.dpad_up -> frameGrabber.offset1.y -= 0.001
                gamepad2.dpad_down -> frameGrabber.offset1.y += 0.001
                gamepad2.dpad_left -> frameGrabber.offset1.x -= 0.001
                gamepad2.dpad_right -> frameGrabber.offset1.x += 0.001

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
        }
        frameGrabber.disable()
    }

    override fun run() {
        var blockLoc1 = when (blockPos) {
            BlockPos.ZERO_THREE -> 0
            BlockPos.ONE_FOUR -> 1
            BlockPos.TWO_FIVE -> 2
            else -> 2
        }
        var blockLoc2 = when (blockPos) {
            BlockPos.ZERO_THREE -> 3
            BlockPos.ONE_FOUR -> 4
            BlockPos.TWO_FIVE -> 5
            else -> 5
        }
        robot.drive.followPath(paths.getPathToBlock(blockLoc1))
        robot.drive.setLocalizerConfig(Math.toRadians(0.0), 0.5, 0.75, true)
        robot.drive.waitForPathFollower()

        robot.drive.grabBlock()
        robot.drive.stowBlock()

        robot.drive.followPath(paths.moveTowardsPlatfrom(robot.drive.position))

        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, false)
        robot.drive.waitForPathFollower()

        robot.drive.releaseBlock()
        robot.drive.stowBlock()

        // SECOND BLOCK
        robot.drive.followPath(paths.secondBlock(robot.drive.position, blockLoc2)) //redundant
        robot.drive.setSpecialAngle(true)
        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, true)
        robot.drive.waitForPathFollower()

        robot.drive.followPath(paths.secondBlock(robot.drive.position, 3))
        robot.drive.setSpecialAngle(false)
        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, true)
        robot.drive.waitForPathFollower()

        robot.drive.grabBlock()
        robot.drive.stowBlock()

        robot.drive.followPath(paths.moveTowardsPlatfrom2(robot.drive.position))
        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, false)
        robot.drive.waitForPathFollower()

        robot.drive.releaseBlock()
        robot.drive.stowBlock()

        moveOutALittle = true
        robot.drive.followPath(paths.grabFoundation(robot.drive.position)) //redundant
        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, false)
        robot.drive.waitForPathFollower()

        robot.drive.followPath(paths.moveTowardsPlatfrom(robot.drive.position))
        robot.drive.turn(Math.toRadians(240.0))
        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, true)
        robot.drive.waitForPathFollower()

        moveToFoundation = true
        robot.drive.followPath(paths.grabFoundation(robot.drive.position))
        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, false)
        robot.drive.waitForPathFollower()
        robot.drive.grabFoundation()


        moveToFoundation = false
        robot.drive.followPath(paths.moveFoundation(robot.drive.position))
        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, false)
        robot.drive.waitForPathFollower()

        robot.drive.followPath(paths.moveTowardsPlatfrom(robot.drive.position))
        robot.drive.turn(Math.toRadians(240.0 + 90.0))
        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, true)
        robot.drive.waitForPathFollower()

        robot.drive.openFoundationGrabber()

        moveToEnd = true
        robot.drive.followPath(paths.park(robot.drive.position))
        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, true)
        robot.drive.waitForPathFollower()

//        robot.drive.followPath(paths.secondBlock(robot.drive.position, blockLoc2)) //redundant
//        moveToEnd = true
//        robot.drive.setLocalizerConfig(Math.toRadians(180.0), 0.25, 0.25, false)
//        robot.drive.waitForPathFollower()


    }
}