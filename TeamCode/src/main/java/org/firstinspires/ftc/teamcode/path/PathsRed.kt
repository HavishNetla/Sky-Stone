package org.firstinspires.ftc.teamcode.path

import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d



class PathsRed {
    var xVal =-96.0;
    var blockPositionsRed: List<PathPoint> = listOf(

            PathPoint(Vector2d(xVal, 24.0), Math.PI / 2, 0.35, 0.3, "first block"),
            PathPoint(Vector2d(xVal, 44.0), Math.PI / 2, 0.35, 0.3, "second block"),
            PathPoint(Vector2d(xVal, 64.0), Math.PI / 2, 0.35, 0.3, "third block"),
            PathPoint(Vector2d(xVal, 88.0), Math.PI / 2, 0.35, 0.3, "fourth block"),
            PathPoint(Vector2d(xVal, 109.0), Math.PI / 2, 0.35, 0.3, "fifth block"),
            PathPoint(Vector2d(xVal, 124.0), Math.PI / 2, 0.35, 0.3, "sixth block")
    )

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
    fun getPathToBlock(index: Int): PathFollower {
        userInput = index

        var t = PathBuilder(Pose2d(-16.32, 81.7, 0.0))
        var path: ArrayList<PathSegment> = t
                .addPoint(blockPositionsRed[index].point,
                        blockPositionsRed[index].followAngle,
                        blockPositionsRed[index].speed,
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
    fun moveInToFoundation(pose: Pose2d, loc: Int): PathFollower {
        var angle = Math.PI / 2
        var t = PathBuilder(pose)

        //new points
        t.addPoint(Vector2d(-70.32, 140.0), angle, 0.4, 1.0, "moving forward1")
                .addPoint(Vector2d(-75.0, 170.0), angle, 0.7, 0.8, "moving forward1")
                .addPoint(Vector2d(-75.0, 200.0), angle, 0.7, 0.8, "moving forward1")
                .addPoint(Vector2d(-95.0, 240.0), angle, 0.2, 0.8, "moving forward1")
        when (loc) {
            1 -> {
                t.addPoint(Vector2d(-97.0, 323.0), angle, 0.28, 0.6, "ENTERS THE PATH")
            }
            2 -> {
                t.addPoint(Vector2d(-97.0, 315.0), angle, 0.25, 0.6, "moving forward3")
            }
            3 -> {
                t.addPoint(Vector2d(-97.0, 305.0), angle, 0.20, 0.6, "moving forward3")
            }
            else -> {
                t.addPoint(Vector2d(-97.0, 295.0), angle, 0.20, 0.6, "moving forward3")
            }
        }

        return PathFollower(t.create(), 30.0, "FIrst1")
    }
    fun moveInToFoundation2(pose: Pose2d, loc: Int): PathFollower {
        var angle = Math.PI / 2
        var t = PathBuilder(pose)

        //new points
        t.addPoint(Vector2d(-75.32, 140.0), angle, 0.6, 1.0, "moving forward1")
                .addPoint(Vector2d(-75.0, 170.0), angle, 0.7, 0.8, "moving forward1")
                .addPoint(Vector2d(-75.0, 200.0), angle, 0.7, 0.8, "moving forward1")
                .addPoint(Vector2d(-95.0, 240.0), angle, 0.2, 0.8, "moving forward1")
        when (loc) {
            1 -> {
                t.addPoint(Vector2d(-97.0, 323.0), angle, 0.28, 0.6, "ENTERS THE PATH")
            }
            2 -> {
                t.addPoint(Vector2d(-97.0, 315.0), angle, 0.25, 0.6, "moving forward3")
            }
            3 -> {
                t.addPoint(Vector2d(-80.0, 305.0), 0.0, 0.20, 0.6, "moving forward3")
            }
            else -> {
                t.addPoint(Vector2d(-80.0, 295.0), 0.0, 0.20, 0.6, "moving forward3")
            }
        }

        return PathFollower(t.create(), 30.0, "FIrst1")
    }

    fun moveOutOfFoundation(pose: Pose2d): PathFollower {
        var angle = Math.PI / 2
        var t = PathBuilder(pose)

        t.addPoint(Vector2d(-75.0, 280.0), angle, 0.5, 0.9, "moving forward1")
                .addPoint(Vector2d(-75.0, 200.0), angle, 0.80, 0.9, "moving forward2")
                .addPoint(Vector2d(-75.0, 160.0), angle, 0.60, 0.9, "moving forward2")
                .addPoint(Vector2d(-82.0, 130.0), angle, 0.4, 0.6, "moving forwarad2")

        return PathFollower(t.create(), 40.0, "FIrst11")
    }
    fun moveOutOfFoundationSpecial(pose: Pose2d): PathFollower {
        var angle1 = Math.PI / 2
        var angle2 = -Math.PI/2
        var t = PathBuilder(pose)

        t.addPoint(Vector2d(-75.0, 280.0), angle2, 0.5, 0.9, "moving forward1")
                .addPoint(Vector2d(-75.0, 200.0), angle2, 0.5, 0.9, "moving forward2")
                .addPoint(Vector2d(-75.0, 160.0), angle2, 0.5, 0.9, "moving forward2")
                .addPoint(Vector2d(-85.0, 130.0), angle1, 0.4, 0.8, "moving forwarad2")

        return PathFollower(t.create(), 40.0, "FIrst11")
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