package org.firstinspires.ftc.teamcode.path

import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import java.util.*

private var t = PathBuilder(Pose2d(20.32, 81.7, 0.0))

data class PathPoint(val point: Vector2d, val followAngle: Double, val speed: Double, val turnSpeed: Double, val label: String)

private var blockPositions: List<PathPoint> = listOf(
        PathPoint(Vector2d(103.14, 23.668), 65.0, 0.3, 0.5, "first block"),
        PathPoint(Vector2d(103.14, 43.985), 60.0, 0.3, 0.5, "second block"),
        PathPoint(Vector2d(103.14, 64.305), 55.0, 0.3, 0.5, "third block"),
        PathPoint(Vector2d(103.14, 84.625), 50.0, 0.3, 0.5, "fourth block"),
        PathPoint(Vector2d(103.14, 104.945), 45.0, 0.3, 0.5, "fifth block"),
        PathPoint(Vector2d(103.14, 125.265), 40.0, 0.3, 0.5, "sixth block")
)

class Paths() {
    fun getPathToBlock(index: Int): ArrayList<PathSegment> {
        var path: ArrayList<PathSegment> = t
                .addPoint(blockPositions[index].point,
                        blockPositions[index].followAngle,
                        blockPositions[index].speed,
                        blockPositions[index].turnSpeed,
                        blockPositions[index].label
                )
                .create()

        return path
    }
}