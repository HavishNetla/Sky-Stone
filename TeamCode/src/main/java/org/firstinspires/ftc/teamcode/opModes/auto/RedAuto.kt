package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.util.ElapsedTime
import org.corningrobotics.enderbots.endercv.CameraViewDisplay
import org.firstinspires.ftc.teamcode.path.PathsRed
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import org.firstinspires.ftc.teamcode.vision.FrameGrabber

@Autonomous(name = "Red Auto", group = "A")
class RedAuto : AutoOpMode(Pose2d(-20.7, 81.7, Math.PI / 2)) {

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
        var eTime = ElapsedTime()

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
        val blockLoc3 = when (blockLoc1) {
            0 -> 4
            1 -> 3
            2 -> 4
            else -> 4
        }

        val blockLoc4 = when (blockLoc1) {
            0 -> 2
            1 -> 2
            2 -> 3
            else -> 2
        }

        robot.drive.readyBlockRedNoDelay()

        // Robot moves towards the first skystone
        robot.drive.followPathGlobal(paths.getPathToBlock(blockLoc1))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Grabs and stows the first skystone
        robot.drive.grabBlockRed()
        robot.drive.stowBlockRed()

        // move out
        robot.drive.goToPoint(Vector2d(robot.drive.position.x + 6, robot.drive.position.y), 0.0, 0.25, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // go to foundation
        robot.drive.followPathGlobal(paths.moveInToFoundation(robot.drive.position, 1))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Robot releases the block and stows the arm
        robot.drive.throwBlockRed()
        robot.drive.stowBlockRed()

        //==========================================================================================
        // SECOND BLOCK ============================================================================
        //==========================================================================================
        // Move underneath the bridge
        robot.drive.followPathGlobal(paths.moveOutOfFoundation(robot.drive.position,blockLoc2))
        robot.drive.setInnacurate()
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        robot.drive.readyBlockRedNoDelay()

        // Pick up the next skystone
        var p = paths.blockPositionsRed[blockLoc2]
        robot.drive.goToPointGlobal(Vector2d(p.point.x - 5, p.point.y - 5), Math.PI / 2, p.speed, p.turnSpeed, false)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Grab and stow the block
        robot.drive.grabBlockRed()
        robot.drive.stowBlockRed()

        //move out
        if (blockLoc2 == 5) {
            robot.drive.goToPoint(Vector2d(robot.drive.position.x + 15, robot.drive.position.y), 0.0, 0.3, 0.0)
            robot.drive.setLocalizerConfig(false)
            robot.drive.waitForPathFollower()
        } else {
            robot.drive.goToPoint(Vector2d(robot.drive.position.x + 6, robot.drive.position.y), 0.0, 0.3, 0.0)
            robot.drive.setLocalizerConfig(false)
            robot.drive.waitForPathFollower()
        }
        //go to foundation
        robot.drive.followPathGlobal(paths.moveInToFoundation(robot.drive.position, 2))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        //throw block
        robot.drive.throwBlockRed()
        robot.drive.stowBlockRed()


        //==========================================================================================
        // THIRD BLOCK ============================================================================
        //==========================================================================================
        // Move underneath the bridge
        robot.drive.followPathGlobal(paths.moveOutOfFoundation(robot.drive.position,blockLoc3))
        robot.drive.setInnacurate()
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        robot.drive.readyBlockRedNoDelay()

        // Pick up the next skystone
        var p1 = paths.blockPositionsRed[blockLoc3]
        robot.drive.goToPointGlobal(Vector2d(p1.point.x - 9, p1.point.y - 5), Math.PI / 2, p1.speed, p1.turnSpeed, false)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        // Grab and stow the block
        robot.drive.grabBlockRed()
        robot.drive.stowBlockRed()

        robot.drive.goToPoint(Vector2d(robot.drive.position.x + 6, robot.drive.position.y), 0.0, 0.3, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        robot.drive.followPathGlobal(paths.moveInToFoundation(robot.drive.position, 3))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        robot.drive.throwBlockRed()
        robot.drive.stowBlockRed()

        // Machine Learning to check if we should do the fourth block
        if (/*eTime.time() > 22*/ false) {
            // big rip
            foundationProcedure()
        } else {
            // you know what time it is baby, its FOURTH BLOCK TIME YEEHAWW

            //==========================================================================================
            // FOURTH BLOCK ============================================================================
            //==========================================================================================

            // Move underneath the bridge
            robot.drive.followPathGlobal(paths.moveOutOfFoundation(robot.drive.position,blockLoc4))
            robot.drive.setInnacurate()
            robot.drive.setLocalizerConfig(true)
            robot.drive.waitForPathFollower()

            robot.drive.readyBlockRedNoDelay()

            // Pick up the next skystone
            var p2 = paths.blockPositionsRed[blockLoc4]
            robot.drive.goToPointGlobal(Vector2d(p2.point.x - 9, p2.point.y), Math.PI / 2, p2.speed, p2.turnSpeed, false)
            robot.drive.setLocalizerConfig(true)
            robot.drive.waitForPathFollower()

            // Grab and stow the block
            robot.drive.grabBlockRed()
            robot.drive.stowBlockRed()

            robot.drive.goToPoint(Vector2d(robot.drive.position.x + 6, robot.drive.position.y), 0.0, 0.3, 0.0)
            robot.drive.setLocalizerConfig(false)
            robot.drive.waitForPathFollower()

            robot.drive.followPathGlobal(paths.moveInToFoundation(robot.drive.position, 4))
            robot.drive.setLocalizerConfig(true)
            robot.drive.waitForPathFollower()

            robot.drive.throwBlockRed()
            robot.drive.stowBlockRed()

            //==========================================================================================
            // FOUNDATION MOVER THING ==================================================================
            //==========================================================================================
            foundationProcedure()
        }
    }

    private fun foundationProcedure() {
        robot.drive.goToPoint(Vector2d(robot.drive.position.x + 5, robot.drive.position.y ), 0.0, 0.3, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        robot.drive.turn(0.0)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        //added for double check - matt
        robot.drive.turn(0.0)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        robot.drive.foundationPrep()

        robot.drive.goToPoint(Vector2d(robot.drive.position.x - 12.5, robot.drive.position.y), 0.0, 0.1, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        robot.drive.goToPoint(Vector2d(robot.drive.position.x - 12, robot.drive.position.y), 0.0, 0.1, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        robot.drive.grabFoundation()

        robot.drive.goToPointGlobal(Vector2d(robot.drive.position.x + 35, robot.drive.position.y - 50), -Math.PI / 2, 0.7, 0.0, false)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        robot.drive.turn(-Math.PI / 2)
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        robot.drive.openFoundationGrabber()

        robot.intake.openIntakeBois()

        robot.drive.goToPoint(Vector2d(-80.0, 220.0), 0.0, 0.5, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        robot.intake.power = 0.0
    }
}