package org.firstinspires.ftc.teamcode.path

import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import org.firstinspires.ftc.teamcode.subsystems.Robot


class PathsRed {
    private lateinit var robot: Robot

    private var xVal = -96.0
    var blockPositionsRed: List<PathPoint> = listOf(

            PathPoint(Vector2d(xVal, 24.0), Math.PI / 2, 0.35, 0.3, "first block"),
            PathPoint(Vector2d(xVal, 44.0), Math.PI / 2, 0.35, 0.3, "second block"),
            PathPoint(Vector2d(xVal, 64.0), Math.PI / 2, 0.35, 0.3, "third block"),
            PathPoint(Vector2d(xVal, 88.0), Math.PI / 2, 0.35, 0.3, "fourth block"),
            PathPoint(Vector2d(xVal, 109.0), Math.PI / 2, 0.35, 0.3, "fifth block"),
            PathPoint(Vector2d(xVal, 124.0), Math.PI / 2, 0.35, 0.3, "sixth block")
    )

    private var userInput = 0


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

    fun moveInToFoundation(pose: Pose2d, loc: Int): PathFollower {
        var angle = Math.PI / 2
        var t = PathBuilder(pose)

        //new points
        t.addPoint(Vector2d(-77.32, 140.0), angle, 0.7, 1.0, "moving forward1")
                .addPoint(Vector2d(-85.0, 170.0), angle, 0.8, 0.8, "moving forward1")
                .addPoint(Vector2d(-85.0, 200.0), angle, 0.7, 0.8, "moving forward1")
                robot.drive.setRotaterRedPos(0.0)
                t.addPoint(Vector2d(-95.0, 240.0), angle, 0.2, 0.8, "moving forward1")
        when (loc) {
            1 -> {
                t.addPoint(Vector2d(-103.0, 328.0), angle, 0.3, 0.6, "ENTERS THE PATH")
            }
            2 -> {
                t.addPoint(Vector2d(-103.0, 328.0), angle, 0.3, 0.6, "moving forward3")
            }
            3 -> {
                t.addPoint(Vector2d(-103.0, 312.0), angle, 0.3, 0.6, "moving forward3")
            }
            else -> {
                t.addPoint(Vector2d(-103.0, 312.0), angle, 0.3, 0.6, "moving jjforward3")
            }
        }

        return PathFollower(t.create(), 30.0, "FIrst1")
    }

    /**
     * @deprecated
     */
    fun moveInToFoundation2(pose: Pose2d, loc: Int): PathFollower {
        var angle = Math.PI / 2
        var t = PathBuilder(pose)

        //new points
        t.addPoint(Vector2d(-75.32, 140.0), angle, 0.7, 1.0, "moving forward1")
                .addPoint(Vector2d(-75.0, 170.0), angle, 0.7, 0.8, "moving forward1")
                .addPoint(Vector2d(-75.0, 200.0), angle, 0.7, 0.8, "moving forward1")
                .addPoint(Vector2d(-95.0, 240.0), angle, 0.2, 0.8, "moving forward1")
        when (loc) {
            1 -> {
                t.addPoint(Vector2d(-97.0, 328.0), angle, 0.28, 0.6, "ENTERS THE PATH")
            }
            2 -> {
                t.addPoint(Vector2d(-97.0, 320.0), angle, 0.25, 0.6, "moving forward3")
            }
            3 -> {
                t.addPoint(Vector2d(-80.0, 310.0), 0.0, 0.20, 0.6, "moving forward3")
            }
            else -> {
                t.addPoint(Vector2d(-80.0, 300.0), 0.0, 0.20, 0.6, "moving forward3")
            }
        }

        return PathFollower(t.create(), 30.0, "FIrst1")
    }

    fun moveOutOfFoundation(pose: Pose2d, loc :Int): PathFollower {
        var angle = Math.PI / 2
        var turnSPD = 0.6
        var sPeEd = 0.4
        var xCord = -82.0
        var xOffset = 50
        var t = PathBuilder(pose)

        t.addPoint(Vector2d(-73.0, 280.0), angle, 0.5, 0.9, "moving forward1")
                .addPoint(Vector2d(-73.0, 220.0), angle, 0.80, 0.9, "moving forward2")
                .addPoint(Vector2d(-73.0, 176.0), angle, 0.60, 0.9, "moving fasdasorward2")
                //.addPoint(Vector2d(-73.0, 132.0), angle, 0.60, 0.6, "moving fasorward2")
        //old point
        .addPoint(Vector2d(-82.0, 130.0), angle, 0.4, 0.6, "moving forwarad2")

//        when (loc) {
//            2 -> {
//                t.addPoint(Vector2d(xCord, 44.0 + xOffset), angle, sPeEd, turnSPD, "Stone 2")
//            }
//            3 -> {
//                t.addPoint(Vector2d(xCord, 64.0 + xOffset), angle, sPeEd, turnSPD, "Stone 3")
//            }
//            4 -> {
//                t.addPoint(Vector2d(xCord, 88.0 + xOffset), angle, sPeEd, turnSPD, "Stone 4")
//            }
//            else -> {
//                t.addPoint(Vector2d(xCord, 64.0 + xOffset), angle, sPeEd, turnSPD, "Stone Else")
//            }
//        }

        return PathFollower(t.create(), 40.0, "FIrst11")
    }

    fun moveOutOfFoundationSpecial(pose: Pose2d): PathFollower {
        var angle1 = Math.PI / 2
        var angle2 = -Math.PI / 2
        var t = PathBuilder(pose)

        t.addPoint(Vector2d(-75.0, 280.0), angle2, 0.5, 0.9, "movinaaag forward1")
                .addPoint(Vector2d(-75.0, 200.0), angle2, 0.5, 0.9, "movingasda forward2")
                .addPoint(Vector2d(-75.0, 160.0), angle2, 0.5, 0.9, "moving forward2")
                .addPoint(Vector2d(-85.0, 130.0), angle1, 0.4, 0.8, "moving forawarad2")

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
}