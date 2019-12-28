package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.computerDebuging.ComputerDebugging
import org.firstinspires.ftc.teamcode.path.PathBuilder
import org.firstinspires.ftc.teamcode.path.PathFollower
import org.firstinspires.ftc.teamcode.path.PathSegment
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import java.util.*

@Autonomous(name = "Main Auto", group = "A")
class MainAuto : AutoOpMode() {
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

    private var t1 = PathBuilder(Pose2d(104.14, 99.06, 0.0))
    private var path1: ArrayList<PathSegment> = t1
            .addPoint(Vector2d(88.9, 132.08), Math.toRadians(-90.0), 0.25, 0.25, "moving forward")
            .addPoint(Vector2d(88.9, 220.98), Math.toRadians(-90.0), 0.25, 0.25, "moving forward")
            .addPoint(Vector2d(88.9, 299.72), Math.toRadians(-90.0), 0.25, 0.25, "moving forward")
            .create()

    private var t2 = PathBuilder(Pose2d(88.9, 299.72, 0.0))
    private var path2: ArrayList<PathSegment> = t2
            .addPoint(Vector2d(65.14, 289.72), Math.toRadians(-175.0), 0.25, 2.0, "moving forward")
            .create()

    private var t3 = PathBuilder(Pose2d(65.14, 289.72, 0.0))
    private var path3: ArrayList<PathSegment> = t3
            .addPoint(Vector2d(78.9, 160.88), Math.toRadians(0.0), 0.25, 0.7, "moving forward")
            .create()

    override fun setup() {
        pathFollower = PathFollower(path, 55.0, "FIrst")
        pathFollower1 = PathFollower(path1, 55.0, "Second")
        pathFollower2 = PathFollower(path2, 15.0, "Second")
        pathFollower3 = PathFollower(path3, 100.0, "Second")
        //pathFollower1 = PathFollower(path1, 55.0)

        computerDebugging = ComputerDebugging()
        ComputerDebugging.sendPaths(path)
        ComputerDebugging.sendPacket()
    }

    override fun run() {
        robot.drive.followPath(pathFollower)
        robot.drive.setLocalizerConfig(Math.toRadians(0.0), 0.5, 0.75)
        robot.drive.waitForPathFollower()

        robot.drive.grabBlock()
        robot.drive.stowBlock()


//        robot.drive.followPath(pathFollower1)
//        robot.drive.setLocalizerConfig(Math.toRadians(-90.0), 0.25, 0.25)
//        robot.drive.waitForPathFollower()
//
//        robot.drive.followPath(pathFollower2)
//        robot.drive.setLocalizerConfig(Math.toRadians(-175.0), 0.25, 2.0)
//        robot.drive.waitForPathFollower()
//
//        robot.drive.followPath(pathFollower3)
//        robot.drive.setLocalizerConfig(Math.toRadians(0.0), 0.25, 0.75)
//        robot.drive.waitForPathFollower()
    }
}