package org.firstinspires.ftc.teamcode.computerDebuging

import org.firstinspires.ftc.teamcode.path.PathBuilder
import org.firstinspires.ftc.teamcode.path.PathFollower
import org.firstinspires.ftc.teamcode.path.PathSegment
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import java.util.*

class MyOpMode : OpMode() {
    private lateinit var pathFollower: PathFollower
    private lateinit var pathFollower1: PathFollower
    private lateinit var pathFollower2: PathFollower
    private lateinit var pathFollower3: PathFollower

    private lateinit var computerDebugging: ComputerDebugging

    private var t = PathBuilder(Pose2d(20.32, 139.7, 0.0))
    private var path: ArrayList<PathSegment> = t
            .addPoint(Vector2d(104.14, 99.06), Math.toRadians(0.0), 0.5, 0.75, "moving forward")
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

    override fun init() {
        //    path =
        //        t.addPoint(new Vector2d(15, 100.0), "moving forward")
        //            .addPoint(new Vector2d(100.0, 100.0), "f")
        //            .create();

        ComputerDebugging.sendPaths(path)
        ComputerDebugging.sendPacket()
    }

    override fun loop() {
        val pathFollower = PathFollower(path, 10.0, "asd")

        ComputerDebugging.sendPaths(path)

        //val powers = pathFollower.followCurve(0.0, Robot.robotPos, 0.5, 2.0)
        ComputerDebugging.sendPoint(pathFollower.lookAheadPoint)

        println("Pos: " + Robot.robotPos)
//        println("0: " + powers[0] + ", 1:" + powers[1] + ", 2:" + powers[2])
//        Robot.x = powers[1]
//        Robot.y = powers[0]
//        Robot.c = powers[2]
    }
}
