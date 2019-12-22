package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.computerDebuging.ComputerDebugging
import org.firstinspires.ftc.teamcode.path.PathBuilder
import org.firstinspires.ftc.teamcode.path.PathFollower
import org.firstinspires.ftc.teamcode.path.PathSegment
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import java.util.*

@Autonomous(name = "Pure Pursuit Auto", group = "A")
class PurePursuitAuto : AutoOpMode() {
    private lateinit var pathFollower: PathFollower
    private lateinit var pathFollower1: PathFollower
    private lateinit var computerDebugging: ComputerDebugging


    private var t = PathBuilder(Pose2d(15.0, 15.0, 0.0))

    private var path: ArrayList<PathSegment> = t
            .addPoint(Vector2d(15.0, 150.0), "moving forward")
            .addPoint(Vector2d(200.0, 150.0), "moving forward")
            .create()

    private var t1 = PathBuilder(Pose2d(200.0, 150.0, 0.0))
    private var path1: ArrayList<PathSegment> = t
            .addPoint(Vector2d(200.0, 300.0), "moving forward")
            .addPoint(Vector2d(300.0, 300.0), "moving forward")
            .create()

    override fun setup() {
        pathFollower = PathFollower(path, 55.0)
        //pathFollower1 = PathFollower(path1, 55.0)
        computerDebugging = ComputerDebugging()

        ComputerDebugging.sendPaths(path)
        ComputerDebugging.sendPacket()
    }

    override fun run() {
        robot.drive.followPath(path, pathFollower)
        robot.drive.waitForPathFollower()

        robot.drive.followPath(path1, pathFollower1)
        robot.drive.waitForPathFollower()
    }
}