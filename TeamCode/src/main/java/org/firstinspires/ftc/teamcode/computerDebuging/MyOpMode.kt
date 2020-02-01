package org.firstinspires.ftc.teamcode.computerDebuging

import org.firstinspires.ftc.teamcode.computerDebuging.Robot.*
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

    private var t = PathBuilder(Pose2d(300.0, 300.0, 0.0))
    private var path: ArrayList<PathSegment> = t
            .addPoint(Vector2d(300.0, 10.0), Math.toRadians(Math.PI), 0.5, 0.0, "moving forward")
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

        val powers = pathFollower.followCurve(robotPos)
        x = powers[1] * 0.5
        y = -powers[0] * 0.5
        c = powers[2] * 0.0
        ComputerDebugging.sendPoint(pathFollower.lookAheadPoint)
    }
}
