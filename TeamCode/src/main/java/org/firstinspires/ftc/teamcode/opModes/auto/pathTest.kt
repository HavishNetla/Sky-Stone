package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.path.Paths
import org.firstinspires.ftc.teamcode.util.Pose2d


@Autonomous(name = "pathTest", group = "A")
class pathTest : AutoOpMode(Pose2d(20.7, 81.7, -Math.PI / 2)) {

    private var paths: Paths = Paths()

    override fun setup() {
//
    }

    override fun run() {
        robot.drive.turn(-Math.PI)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()
    }
}