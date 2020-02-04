package org.firstinspires.ftc.teamcode.path

import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d


var blockPositionsRed: List<PathPoint> = listOf(
        PathPoint(Vector2d(-113.14, 10.668), Math.toRadians(116.25), 0.3, 0.3, "first block"),
        PathPoint(Vector2d(-110.14, 40.985), Math.toRadians(99.0 + 5), 0.3, 0.3, "second block"),
        PathPoint(Vector2d(-117.14, 64.305), Math.toRadians(83.75 + 9), 0.3, 0.3, "third block"),
        PathPoint(Vector2d(-121.14, 69.625), Math.toRadians(77.5 + 5), 0.3, 0.3, "fourth block"),
        PathPoint(Vector2d(-115.14, 104.945), Math.toRadians(71.25 + 5), 0.3, 0.3, "fifth block"),
        PathPoint(Vector2d(-110.14, 132.265), Math.toRadians(65.0 + 5), 0.3, 0.3, "sixth block")
)

class PathsRed {
    var userInput = 0

    /**
     * Returns the path to get to a given block
     *
     * @param index the location of the block
     * @return the path to the block
     */
//    fun getPathToBlock(index: Int): PathFollower {
//        userInput = index
//
//        var t = PathBuilder(Pose2d(18.32, 81.7, 0.0))
//        var path: ArrayList<PathSegment> = t
//                .addPoint(blockPositions[index].point,
//                        blockPositions[index].followAngle,
//                        blockPositions[index].speed,
//                        0.75
//                        ,
//                        "13312"
//                )
//                .create()
//
//        return PathFollower(path, 55.0, "FIrst")
//    }

    /**
     * Returns the path to get to a given block the SECOND time
     *
     * @param index the location of the block
     * @return the path to the block
     */
    fun secondBlock(pose: Pose2d, loc: Int): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(blockPositionsRed[loc].point.x, blockPositionsRed[loc].point.y),
                        -Math.PI,
                        blockPositionsRed[loc].speed,
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
                .addPoint(Vector2d(-80.0, 192.00), 0.0, 0.4, 0.0, "moving forward0")
                .addPoint(Vector2d(-80.0, 132.08), 0.0, 0.4, 0.25, "moving forward1")
                .addPoint(Vector2d(-80.0, 220.98), 0.0, 0.4, 0.25, "moving forward2")
                .addPoint(Vector2d(-105.0, 330.72), 0.0, 0.25, 0.25, "moving forward3")
                .create()

        return PathFollower(path, 55.0, "FIrst1")
    }

    fun moveTowardsPlatfrom2(pose: Pose2d): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(-80.0, 192.00), 0.0, 0.4, 0.0, "moving forward0")
                .addPoint(Vector2d(-80.0, 132.08), 0.0, 0.4, 0.25, "moving forward1")
                .addPoint(Vector2d(-80.0, 220.98), 0.0, 0.4, 0.25, "moving forward2")
                .addPoint(Vector2d(-105.0, 310.72), 0.0, 0.25, 0.25, "moving forward3")
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
                .addPoint(Vector2d(-30.48 * 2.5, 9 * 30.48), 0.0, 0.5, 0.0, "moving forward1")
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