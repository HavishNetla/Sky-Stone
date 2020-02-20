package org.firstinspires.ftc.teamcode.path

import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d


data class PathPoint(val point: Vector2d, val followAngle: Double, val speed: Double, val turnSpeed: Double, val label: String)


class Paths {
    var blockPositions: List<PathPoint> = listOf(
            PathPoint(Vector2d(96.0, 24.0), -Math.PI / 2, 0.35, 0.3, "first block"),
            PathPoint(Vector2d(96.0, 44.0), -Math.PI / 2, 0.35, 0.3, "second block"),
            PathPoint(Vector2d(96.0, 64.0), -Math.PI / 2, 0.35, 0.3, "third block"),
            PathPoint(Vector2d(96.0, 88.0), -Math.PI / 2, 0.35, 0.3, "fourth block"),
            PathPoint(Vector2d(96.0, 109.0), -Math.PI / 2, 0.35, 0.3, "fifth block"),
            PathPoint(Vector2d(96.0, 124.0), -Math.PI / 2, 0.35, 0.3, "sixth block")
    )
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
     * Returns the path to get to a given block the THIRD time
     *
     * @param index the location of the block
     * @return the path to the block
     */
    fun thirdBlock(pose: Pose2d, loc: Int): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(blockPositions[loc].point.x + 12, blockPositions[loc].point.y),
                        0.0,
                        blockPositions[loc].speed,
                        0.75
                        ,
                        "1231s23"
                )
                .create()

        return PathFollower(path, 55.0, "FIerst1")
    }

    /**
     * Returns the path to get to a given block the THIRD time
     *
     * @param index the location of the block
     * @return the path to the block
     */
    fun fourthBlock(pose: Pose2d, loc: Int): PathFollower {
        var t = PathBuilder(pose)
        var path: ArrayList<PathSegment> = t
                .addPoint(Vector2d(blockPositions[loc].point.x + 25, blockPositions[loc].point.y),
                        0.0,
                        blockPositions[loc].speed,
                        0.75
                        ,
                        "1231s23"
                )
                .create()

        return PathFollower(path, 55.0, "FIerst1")
    }

    /**
     * Returns the path to get to the building foundation
     *
     * @param pose the position of the robot
     * @return the path to the foundation
     */
    fun moveTowardsPlatfrom(pose: Pose2d, loc: Int, robot: Robot): PathFollower {
        var t = PathBuilder(pose)
        t.addPoint(Vector2d(80.0, 150.08), -Math.PI / 2, 0.5, 0.5, "moving aforward1")
                .addPoint(Vector2d(80.0, 250.08), -Math.PI / 2, 0.30, 0.5, "moving forward1")
//                .addPoint(Vector2d(85.0, 170.0), Math.PI, 0.5, 0.25, "moving forward1")
//                .addPoint(Vector2d(85.0, 200.08), Math.PI, 0.25, 0.25, "moving forward1")
//                .addPoint(Vector2d(85.0, 250.0), Math.PI, 0.20, 0.0, "moving forward1")

        return PathFollower(t.create(), 55.0, "FIrst1")
    }

    /**
     * Returns the path to get to the building foundation
     *
     * @param pose the position of the robot
     * @return the path to the foundation
     */
    fun moveTowardsPlatfrom3(pose: Pose2d, loc: Int, robot: Robot): PathFollower {
        var t = PathBuilder(pose)
        t.addPoint(Vector2d(95.0, 150.08), Math.PI, 0.75, 0.25, "moving forward1")
                .addPoint(Vector2d(100.0, 260.08), Math.PI, 0.10, 0.25, "moving forward1")
//                .addPoint(Vector2d(85.0, 170.0), Math.PI, 0.5, 0.25, "moving forward1")
//                .addPoint(Vector2d(85.0, 200.08), Math.PI, 0.25, 0.25, "moving forward1")
//                .addPoint(Vector2d(85.0, 250.0), Math.PI, 0.20, 0.0, "moving forward1")

        return PathFollower(t.create(), 35.0, "FIrst1")
    }

    fun moveInToFoundation(pose: Pose2d, loc: Int): PathFollower {
        var angle = -Math.PI / 2
        var t = PathBuilder(pose)

        //new points
        t.addPoint(Vector2d(75.32, 200.0), angle, 0.7, 1.0, "moving forward1")
                .addPoint(Vector2d(90.0, 240.0), angle, 0.2, 0.8, "moving forward1")
        when (loc) {
            1 -> {
                t.addPoint(Vector2d(90.0, 333.0), angle, 0.30, 0.6, "ENTERS THE PATH")
            }
            2 -> {
                t.addPoint(Vector2d(90.0, 333.0), angle, 0.30, 0.6, "moving forward3")
            }
            3 -> {
                t.addPoint(Vector2d(90.0, 305.0), angle, 0.30, 0.6, "moving forward3")
            }
            else -> {
                t.addPoint(Vector2d(90.0, 305.0), angle, 0.30, 0.6, "moving forward3")
            }
        }

        return PathFollower(t.create(), 40.0, "FIrst1")
    }

    fun moveOutOfFoundation(pose: Pose2d): PathFollower {
        var angle = -Math.PI / 2
        var t = PathBuilder(pose)

        t.addPoint(Vector2d(75.0, 280.0), angle, 0.5, 0.9, "moving forward1")
                .addPoint(Vector2d(75.0, 200.0), angle, 0.80, 0.9, "moving forward2")
                .addPoint(Vector2d(75.0, 160.0), angle, 0.60, 0.9, "moving forward2")
                .addPoint(Vector2d(80.0, 130.0), angle, 0.4, 0.6, "moving forwarad2")

        return PathFollower(t.create(), 30.0, "FIrst11")
    }

    /**
     * Returns the path to get to the building foundation
     *
     * @param pose the position of the robot
     * @return the path to the foundation
     */
    fun pathTest(pose: Pose2d): PathFollower {
        var angle = -Math.PI / 2
        var t = PathBuilder(pose)
        t.addPoint(Vector2d(20.32, 255.0), angle, 0.4, 0.4, "moving forward1")
                .addPoint(Vector2d(50.0, 270.0), angle, 0.4, 0.4, "moving forward1")
                .addPoint(Vector2d(50.0, 320.0), angle, 0.2, 0.4, "moving forward2")
        //.addPoint(Vector2d(105.0, 320.72), -Math.PI/2, 0.25, 0.25, "moving forward3")


        return PathFollower(t.create(), 20.0, "FIrst1")
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