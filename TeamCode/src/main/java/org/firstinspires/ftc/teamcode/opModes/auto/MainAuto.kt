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
    private lateinit var pathFollower: PathFollower
    private lateinit var pathFollower1: PathFollower
    private lateinit var computerDebugging: ComputerDebugging


    private var t = PathBuilder(Pose2d(20.32, 139.7, 0.0))
    private var path: ArrayList<PathSegment> = t
            .addPoint(Vector2d(104.14, 99.06), "moving forward")
            .create()

    private var t1 = PathBuilder(Pose2d(104.14, 99.06, 0.0))
    private var path1: ArrayList<PathSegment> = t1
            .addPoint(Vector2d(88.9, 132.08), "moving forward")
            .addPoint(Vector2d(88.9, 220.98), "moving forward")
            .create()

    override fun setup() {
        pathFollower = PathFollower(path, 55.0, "FIrst")
        pathFollower1 = PathFollower(path1, 55.0, "Second")
        //pathFollower1 = PathFollower(path1, 55.0)

        computerDebugging = ComputerDebugging()
        ComputerDebugging.sendPaths(path)
        ComputerDebugging.sendPacket()
    }

    override fun run() {
        robot.drive.followPath(path, pathFollower)
        robot.drive.waitForPathFollower()

        System.out.println("statut: done step 1")
        robot.drive.followPath(path1, pathFollower1)
        System.out.println("statut: started step 2")
        robot.drive.waitForPathFollower()
    }
}