package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.corningrobotics.enderbots.endercv.CameraViewDisplay
import org.firstinspires.ftc.teamcode.path.Paths
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import org.firstinspires.ftc.teamcode.vision.FrameGrabber

@Autonomous(name = "Blue Auto 4", group = "A")
class BlueAuto4 : AutoOpMode(Pose2d(20.32, 81.7, -Math.PI / 2)) {

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
        robot.drive.stowBlockRedTele()

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


        robot.drive.readyBlock()

        // Robot moves towards the first skystone
        robot.drive.followPath(paths.getPathToBlock(blockLoc1))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()
        println("POSITION1" + robot.drive.position)

        // Grabs and stows the first skystone
        robot.drive.grabBlock()
        robot.drive.stowBlock()

        robot.drive.goToPoint(Vector2d(robot.drive.position.x - 6, robot.drive.position.y), 0.0, 0.25, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // robot drives to the foundation
        robot.drive.followPathGlobal(paths.moveTowardsPlatfrom(robot.drive.position, 1, robot))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        robot.drive.followPathGlobal(paths.moveInToFoundation(robot.drive.position, 1))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()
        println("FoundPOSITION1" + robot.drive.position)


        // Robot releases the block and stows the arm
        //robot.drive.halfPlaceBlock()
        robot.drive.throwBlock()
        robot.drive.stowBlockNoDelay()


        //==========================================================================================
        // SECOND BLOCK ============================================================================
        //==========================================================================================
        // Move underneath the bridge
        robot.drive.goToPointGlobal(Vector2d(70.0, 85.08), -Math.PI / 2, 0.5, 0.5)
        robot.drive.stowBlockNoDelay()
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

//        robot.drive.goToPoint(Vector2d(85.14, 130.265), 0.0, 0.3, 0.3)
//        robot.drive.setLocalizerConfig(false)
//        robot.drive.waitForPathFollower()

        robot.drive.readyBlock()

        if (blockLoc2 == 5) {
            robot.drive.goToPoint(Vector2d(103.14, 127.265), 0.0, 0.3, 0.3)
            robot.drive.setLocalizerConfig(false)
            robot.drive.waitForPathFollower()
        } else {
            // Pick up the next skystone
            var p = paths.blockPositions[blockLoc2]
            robot.drive.goToPoint(Vector2d(p.point.x + 6, p.point.y), 0.0, p.speed, p.turnSpeed)
            robot.drive.setLocalizerConfig(false)
            robot.drive.waitForPathFollower()
        }
        println("POSITION2" + robot.drive.position)


        // Grab and stow the block
        robot.drive.grabBlock()
        robot.drive.stowBlock()

        robot.drive.goToPoint(Vector2d(robot.drive.position.x - 6, robot.drive.position.y), 0.0, 0.25, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // robot drives to the foundation
        robot.drive.followPathGlobal(paths.moveTowardsPlatfrom(robot.drive.position, 2, robot))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        //robot.drive.preRelease()

        robot.drive.followPathGlobal(paths.moveInToFoundation(robot.drive.position, 2))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()
        println("FoundPOSITION2" + robot.drive.position)

        // Robot releases the block and stows the arm
        //robot.drive.halfPlaceBlock()
        robot.drive.throwBlock()
        robot.drive.stowBlockNoDelay()


        //==========================================================================================
        // THIRD BLOCK ============================================================================
        //==========================================================================================
        // Move underneath the bridge
        robot.drive.goToPointGlobal(Vector2d(70.0, 85.08), -Math.PI / 2, 0.5, 0.5)
        robot.drive.stowBlockNoDelay()
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

//        robot.drive.goToPoint(Vector2d(85.14, 130.265), 0.0, 0.3, 0.3)
//        robot.drive.setLocalizerConfig(false)
//        robot.drive.waitForPathFollower()

        robot.drive.readyBlock()

        if (blockLoc2 == 5) {
            robot.drive.goToPoint(Vector2d(103.14, 127.265), 0.0, 0.3, 0.3)
            robot.drive.setLocalizerConfig(false)
            robot.drive.waitForPathFollower()
        } else {
            // Pick up the next skystone
            var p = paths.blockPositions[blockLoc3]
            robot.drive.goToPoint(Vector2d(p.point.x + 12, p.point.y), 0.0, p.speed, p.turnSpeed)
            robot.drive.setLocalizerConfig(false)
            robot.drive.waitForPathFollower()
        }
        println("POSITION2" + robot.drive.position)


        // Grab and stow the block
        robot.drive.grabBlock()
        robot.drive.stowBlock()

        robot.drive.goToPoint(Vector2d(robot.drive.position.x - 6, robot.drive.position.y), 0.0, 0.25, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // robot drives to the foundation
        robot.drive.followPathGlobal(paths.moveTowardsPlatfrom(robot.drive.position, 3, robot))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        //robot.drive.preRelease()

        robot.drive.followPathGlobal(paths.moveInToFoundation(robot.drive.position, 3))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()
        println("FoundPOSITION2" + robot.drive.position)

        // Robot releases the block and stows the arm
        //robot.drive.halfPlaceBlock()
        robot.drive.throwBlock()
        robot.drive.stowBlockNoDelay()


        //==========================================================================================
        // FOURTH BLOCK ============================================================================
        //==========================================================================================
        // Move underneath the bridge
        // Move underneath the bridge
        robot.drive.goToPointGlobal(Vector2d(70.0, 85.08), -Math.PI / 2, 0.5, 0.5)
        robot.drive.stowBlockNoDelay()
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

//        robot.drive.goToPoint(Vector2d(85.14, 130.265), 0.0, 0.3, 0.3)
//        robot.drive.setLocalizerConfig(false)
//        robot.drive.waitForPathFollower()

        robot.drive.readyBlock()

        if (blockLoc2 == 5) {
            robot.drive.goToPoint(Vector2d(103.14, 127.265), 0.0, 0.3, 0.3)
            robot.drive.setLocalizerConfig(false)
            robot.drive.waitForPathFollower()
        } else {
            // Pick up the next skystone
            var p = paths.blockPositions[blockLoc4]
            robot.drive.goToPoint(Vector2d(p.point.x + 18, p.point.y), 0.0, p.speed, p.turnSpeed)
            robot.drive.setLocalizerConfig(false)
            robot.drive.waitForPathFollower()
        }
        println("POSITION2" + robot.drive.position)


        // Grab and stow the block
        robot.drive.grabBlock()
        robot.drive.stowBlock()

        robot.drive.goToPoint(Vector2d(robot.drive.position.x - 6, robot.drive.position.y - 3), 0.0, 0.25, 0.0)
        robot.drive.setLocalizerConfig(false)
        robot.drive.waitForPathFollower()

        // robot drives to the foundation
        robot.drive.followPathGlobal(paths.moveTowardsPlatfrom(robot.drive.position, 4, robot))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()

        //robot.drive.preRelease()

        robot.drive.followPathGlobal(paths.moveInToFoundation(robot.drive.position, 4))
        robot.drive.setLocalizerConfig(true)
        robot.drive.waitForPathFollower()
        println("FoundPOSITION2" + robot.drive.position)

        // Robot releases the block and stows the arm
        //robot.drive.halfPlaceBlock()
        robot.drive.throwBlock()
        robot.drive.stowBlockNoDelay()
    }
}