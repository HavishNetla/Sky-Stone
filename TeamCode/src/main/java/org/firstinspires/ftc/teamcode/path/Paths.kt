package org.firstinspires.ftc.teamcode.path

import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import java.util.*


data class PathPoint(val point: Vector2d, val followAngle: Double, val speed: Double, val turnSpeed: Double, val label: String)

private var blockPositions: List<PathPoint> = listOf(
        PathPoint(Vector2d(103.14, 23.668), Math.toRadians(65.0), 0.3, 0.3, "first block"),
        PathPoint(Vector2d(103.14, 43.985), Math.toRadians(71.25), 0.3, 0.3, "second block"),
        PathPoint(Vector2d(103.14, 64.305), Math.toRadians(77.5), 0.3, 0.3, "third block"),
        PathPoint(Vector2d(103.14, 84.625), Math.toRadians(83.75), 0.3, 0.3, "fourth block"),
        PathPoint(Vector2d(103.14, 104.945), Math.toRadians(90.0), 0.3, 0.3, "fifth block"),
        PathPoint(Vector2d(103.14, 125.265), Math.toRadians(99.25), 0.3, 0.3, "sixth block")
)

class Paths {
    var userInput = 0;
    fun getPathToBlock(index: Int): PathFollower {
        userInput = index

        var t = PathBuilder(Pose2d(20.32, 81.7, 0.0))
        var path: ArrayList<PathSegment> = t
                .addPoint(blockPositions[index].point,
                        blockPositions[index].followAngle,
                        blockPositions[index].speed,
                        0.75
                        ,
                        "13312"
                )
                .create()

        return PathFollower(path, 55.0, "FIrst")
    }

    fun secondBlock(pose: Pose2d): PathFollower {
        return moveFromPlatformToBlock(pose, userInput - 1)
    }

    fun moveFromPlatformToBlock(pose: Pose2d, index: Int): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(80.0, 132.08), 0.0, 0.25, 0.25, "moving forward1")
                .addPoint(blockPositions[index].point,
                        0.0,
                        blockPositions[index].speed,
                        0.75
                        ,
                        "123123"
                ).create()

        return PathFollower(path, 55.0, "FIrst1")
    }

    fun moveTowardsPlatfrom(pose: Pose2d): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(80.0, 132.08), -Math.PI, 0.25, 0.25, "moving forward1")
                .addPoint(Vector2d(80.0, 220.98), -Math.PI, 0.25, 0.25, "moving forward2")
                .addPoint(Vector2d(105.0, 320.72), -Math.PI, 0.25, 0.25, "moving forward3")
                .create()

        return PathFollower(path, 55.0, "FIrst1")
    }
}