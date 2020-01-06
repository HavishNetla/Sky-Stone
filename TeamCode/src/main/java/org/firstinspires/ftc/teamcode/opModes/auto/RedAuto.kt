package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.corningrobotics.enderbots.endercv.CameraViewDisplay
import org.firstinspires.ftc.teamcode.path.PathsRed
import org.firstinspires.ftc.teamcode.path.blockPositionsRed
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import org.firstinspires.ftc.teamcode.vision.FrameGrabber

@Autonomous(name = "Red Auto", group = "A")
class RedAuto : AutoOpMode(Pose2d(-20.32, 81.7, Math.PI / 2)) {

    enum class BlockPos {
        ZERO_THREE,
        ONE_FOUR,
        TWO_FIVE,
        NONE,
    }

    private var blockPos: BlockPos = BlockPos.NONE
    private var paths: PathsRed = PathsRed()

    override fun setup() {
        val frameGrabber = FrameGrabber()
        frameGrabber.init(hardwareMap.appContext, CameraViewDisplay.getInstance())
        frameGrabber.enable()
        while (!isStarted) {
            when {
                gamepad1.dpad_up -> frameGrabber.offset.y -= 0.005
                gamepad1.dpad_down -> frameGrabber.offset.y += 0.005
                gamepad1.dpad_left -> frameGrabber.offset.x -= 0.005
                gamepad1.dpad_right -> frameGrabber.offset.x += 0.005

                gamepad2.dpad_up -> frameGrabber.offset1.y -= 0.005
                gamepad2.dpad_down -> frameGrabber.offset1.y += 0.005
                gamepad2.dpad_left -> frameGrabber.offset1.x -= 0.005
                gamepad2.dpad_right -> frameGrabber.offset1.x += 0.005

                gamepad2.y -> frameGrabber.threshold++
                gamepad2.a -> frameGrabber.threshold--
            }

            telemetry.addData("THRESHOLD", frameGrabber.threshold)

            telemetry.addData(
                    "stat", frameGrabber.statusLeft.toString() + ", " + frameGrabber.statusRight)

            var sL = frameGrabber.statusLeft.toString()
            var sR = frameGrabber.statusRight.toString()
            blockPos = if (sL == "YELLOW" && sR == "YELLOW") {
                BlockPos.ZERO_THREE
            } else if (sL == "YELLOW" && sR == "BLACK") {
                BlockPos.TWO_FIVE
            } else if (sL == "BLACK" && sR == "YELLOW") {
                BlockPos.ONE_FOUR
            } else {
                BlockPos.NONE
            }
            telemetry.addData("Block positions", blockPos)
            telemetry.update()
        }
        frameGrabber.disable()
    }

    override fun run() {
        val blockLoc1 = when (blockPos) {
            BlockPos.ZERO_THREE -> 0
            BlockPos.ONE_FOUR -> 1
            BlockPos.TWO_FIVE -> 2
            else -> 2
        }
        val blockLoc2 = when (blockPos) {
            BlockPos.ZERO_THREE -> 3
            BlockPos.ONE_FOUR -> 4
            BlockPos.TWO_FIVE -> 5
            else -> 5
        }

        // Robot moves towards the first skystone
        var pp = blockPositionsRed[blockLoc1]
        robot.drive.goToPoint(pp.point, pp.followAngle, pp.speed, pp.turnSpeed)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Grabs and stows the first skysone
        robot.drive.grabBlockRed()
        robot.drive.stowBlockRed()

        // Robot drives to the foundation
        robot.drive.followPath(paths.moveTowardsPlatfrom(robot.drive.position))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Robot releases the block and stows the arm
        robot.drive.releaseBlockRed()
        robot.drive.stowBlockRed()

        //==========================================================================================
        // SECOND BLOCK ============================================================================
        //==========================================================================================
        // Move underneath the bridge
        robot.drive.goToPoint(Vector2d(-70.0, 132.08), 0.0, 0.5, 0.5)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()


        // Pick up the next skystone
        pp = blockPositionsRed[blockLoc2]
        robot.drive.goToPoint(Vector2d(pp.point.x + 20, pp.point.y), pp.followAngle, pp.speed, pp.turnSpeed)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()


        // Grab and stow the block
        robot.drive.grabBlockRed()
        robot.drive.stowBlockRed()

        // Move to the bulding foundation
        robot.drive.followPath(paths.moveTowardsPlatfrom(robot.drive.position))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Release the block and stow the arm
        robot.drive.releaseBlockRed()
        robot.drive.stowBlockRed()

        //Move out a little from the foundation
        robot.drive.goToPoint(Vector2d(-90.0, 300.72), 0.0, 0.3, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // Turn to grab the foundation
        robot.drive.turn(Math.toRadians(-50.0))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Slam against the foundation to prep the grab
        robot.drive.goToPoint(Vector2d(-110.0, 300.72), Math.PI, 0.3, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // Grab the foundation
        robot.drive.grabFoundation()

        // Move the foundation forward
        robot.drive.followPath(paths.moveFoundation(robot.drive.position))
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // Spin move the foundation into the zone
        robot.drive.turn(Math.toRadians(-50.0 - 90.0))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Release the foundation
        robot.drive.openFoundationGrabber()

        // Move underneath the bridge
        robot.drive.goToPoint(Vector2d(-30.48 * 3.5, 6 * 30.48), 0.0, 0.3, 0.0)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()
    }
}