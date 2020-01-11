package org.firstinspires.ftc.teamcode.path

import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d


data class PathPoint(val point: Vector2d, val followAngle: Double, val speed: Double, val turnSpeed: Double, val label: String)

var blockPositions: List<PathPoint> = listOf(
        PathPoint(Vector2d(103.14, 23.668), Math.toRadians(60.0), 0.3, 0.3, "first block"),
        PathPoint(Vector2d(103.14, 43.985), Math.toRadians(66.25), 0.3, 0.3, "second block"),
        PathPoint(Vector2d(103.14, 64.305), Math.toRadians(77.5), 0.3, 0.3, "third block"),
        PathPoint(Vector2d(103.14, 84.625), Math.toRadians(83.75), 0.3, 0.3, "fourth block"),
        PathPoint(Vector2d(103.14, 104.945), Math.toRadians(102.0), 0.3, 0.3, "fifth block"),
        PathPoint(Vector2d(103.14, 130.265), Math.toRadians(116.25), 0.3, 0.3, "sixth block")
)

class Paths {
    var userInput = 0

    /**
     * Returns the path to get to a given block
     *
     * @param index the location of the block
     * @return the path to the block
     */
    fun getPathToBlock(index: Int): PathFollower {
        userInput = index

        var t = PathBuilder(Pose2d(18.32, 81.7, 0.0))
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

    /**
     * Returns the path to get to a given block the SECOND time
     *
     * @param index the location of the block
     * @return the path to the block
     */
    fun secondBlock(pose: Pose2d, loc: Int): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(blockPositions[loc].point.x, blockPositions[loc].point.y),
                        0.0,
                        blockPositions[loc].speed,
                        0.75
                        ,
                        "123123"
                )
                .create()

        return PathFollower(path, 55.0, "FIrst1")
    }

    /**
     * Returns the path to get to the building foundation
     *
     * @param pose the position of the robot
     * @return the path to the foundation
     */
    fun moveTowardsPlatfrom(pose: Pose2d): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(70.0, 120.08), -Math.PI, 0.4, 0.25, "moving forward1")
                .addPoint(Vector2d(78.0, 132.08), -Math.PI, 0.4, 0.25, "moving forward1")
                .addPoint(Vector2d(78.0, 220.98), -Math.PI, 0.4, 0.25, "moving forward2")
                .addPoint(Vector2d(105.0, 320.72), -Math.PI, 0.25, 0.25, "moving forward3")
                .create()

        return PathFollower(path, 55.0, "FIrst1")
    }

    /**
     * Returns the path to get to the building foundation
     *
     * @param pose the position of the robot
     * @return the path to the foundation
     */
    fun moveTowardsPlatfrom2(pose: Pose2d): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(70.0, 120.08), -Math.PI, 0.4, 0.25, "moving forward1")
                .addPoint(Vector2d(78.0, 132.08), -Math.PI, 0.4, 0.25, "moving forward1")
                .addPoint(Vector2d(78.0, 220.98), -Math.PI, 0.4, 0.25, "moving forward2")
                .addPoint(Vector2d(105.0, 320.72), -Math.PI, 0.25, 0.25, "moving forward3")
                .create()

        return PathFollower(path, 55.0, "FIrst1")
    }


    /**
     * @deprecated
     */
    fun grabFoundation(pose: Pose2d): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(pose.x - 20, pose.y), -Math.PI, 0.25, 0.25, "moving forward1")
                .create()

        return PathFollower(path, 55.0, "FIrst1")
    }

    /**
     * Returns the path to move the foundation
     *
     * @param pose the position of the robot
     * @return the path to move the foundation
     */
    fun moveFoundation(pose: Pose2d): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(30.48 * 2.5, 9 * 30.48), 0.0, 0.4, 0.0, "moving forward1")
                .create()

        return PathFollower(path, 40.0, "FIrst1")
    }

    /**
     * @deprecated
     */
    fun park(pose: Pose2d): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(30.48 * 2, 6 * 30.48), 0.0, 0.4, 0.0, "moving forwarasd")
                .create()

        return PathFollower(path, 40.0, "FIASR1")
    }
}