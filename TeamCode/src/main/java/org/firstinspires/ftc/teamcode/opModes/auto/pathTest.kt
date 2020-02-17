package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.corningrobotics.enderbots.endercv.CameraViewDisplay
import org.firstinspires.ftc.teamcode.path.Paths
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import org.firstinspires.ftc.teamcode.vision.FrameGrabber
import org.openftc.revextensions2.ExpansionHubEx
import org.openftc.revextensions2.ExpansionHubMotor
import org.openftc.revextensions2.RevBulkData


@Autonomous(name = "pathTest", group = "A")
class pathTest : AutoOpMode(Pose2d(20.7, 81.7, -Math.PI / 2)) {

    private var paths: Paths = Paths()

    override fun setup() {
//
    }

    override fun run() {
        robot.drive.followPathGlobal(paths.pathTest(robot.drive.position))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()
    }
}