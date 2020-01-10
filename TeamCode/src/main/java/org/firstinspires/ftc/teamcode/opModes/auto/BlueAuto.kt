package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.corningrobotics.enderbots.endercv.CameraViewDisplay
import org.firstinspires.ftc.teamcode.path.Paths
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import org.firstinspires.ftc.teamcode.vision.FrameGrabber

@Autonomous(name = "Blue Auto", group = "A")
class BlueAuto : AutoOpMode(Pose2d(20.32, 81.7, -Math.PI / 2)) {

    enum class BlockPos {
        ZERO_THREE,
        ONE_FOUR,
        TWO_FIVE,
        NONE,
    }

    private var blockPos: BlockPos = BlockPos.NONE
    private var paths: Paths = Paths()

    override fun setup() {
        val frameGrabber = FrameGrabber()
        frameGrabber.init(hardwareMap.appContext, CameraViewDisplay.getInstance())
        frameGrabber.enable()
        while (!isStarted) {
            when {
                gamepad1.dpad_up -> frameGrabber.offset.y -= 0.002
                gamepad1.dpad_down -> frameGrabber.offset.y += 0.002
                gamepad1.dpad_left -> frameGrabber.offset.x -= 0.002
                gamepad1.dpad_right -> frameGrabber.offset.x += 0.002

                gamepad2.dpad_up -> frameGrabber.offset1.y -= 0.002
                gamepad2.dpad_down -> frameGrabber.offset1.y += 0.002
                gamepad2.dpad_left -> frameGrabber.offset1.x -= 0.002
                gamepad2.dpad_right -> frameGrabber.offset1.x += 0.002

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
                BlockPos.ONE_FOUR
            } else if (sL == "BLACK" && sR == "YELLOW") {
                BlockPos.TWO_FIVE
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
        robot.drive.followPath(paths.getPathToBlock(blockLoc1))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Grabs and stows the first skysone
        robot.drive.grabBlock()
        robot.drive.stowBlock()

        // Robot drives to the foundation
        robot.drive.followPath(paths.moveTowardsPlatfrom(robot.drive.position))
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // Robot releases the block and stows the arm
        robot.drive.releaseBlock()
        robot.drive.stowBlock()

        //==========================================================================================
        // SECOND BLOCK ============================================================================
        //==========================================================================================
        // Move underneath the bridge
        robot.drive.goToPoint(Vector2d(80.0, 132.08), 0.0, 0.5, 0.5)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()


        if (blockLoc2 == 5) {
            robot.drive.goToPoint(Vector2d(103.14, 127.265), 0.0, 0.3, 0.3)
            robot.drive.setLocalizerConfig(false)
            robot.drive.waitForPathFollower()
        } else {
            // Pick up the next skystone
            robot.drive.followPath(paths.secondBlock(robot.drive.position, blockLoc2))
            robot.drive.setLocalizerConfig(true)
            robot.drive.waitForPathFollower()
        }

        // Grab and stow the block
        robot.drive.grabBlock()
        robot.drive.stowBlock()

        // Move to the bulding foundation
        robot.drive.followPath(paths.moveTowardsPlatfrom2(robot.drive.position))
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // Release the block and stow the arm
        robot.drive.releaseBlock()
        robot.drive.stowBlock()

        // Move out a little from the foundation
        robot.drive.goToPoint(Vector2d(90.0, 300.72), 0.0, 0.3, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // Turn to grab the foundation
        robot.drive.turn(Math.toRadians(240.0))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Slam against the foundation to prep the grab
        robot.drive.goToPoint(Vector2d(110.0, 300.72), Math.PI, 0.3, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // Grab the foundation
        robot.drive.grabFoundation()

        // Move the foundation forward
        robot.drive.followPath(paths.moveFoundation(robot.drive.position))
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // Spin move the foundation into the zone
        robot.drive.turn(Math.toRadians(240.0 + 90.0))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Release the foundation
        robot.drive.openFoundationGrabber()

        // Move underneath the bridge
        robot.drive.goToPoint(Vector2d(30.48 * 3.5, 6 * 30.48), 0.0, 0.3, 0.0)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()
    }
}