package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Pose2d

abstract class AutoOpMode(ogPose: Pose2d) : LinearOpMode() {
    private var ogPose: Pose2d = ogPose

    protected abstract fun setup()
    protected abstract fun run()

    protected lateinit var robot: Robot


    override fun runOpMode() {
        robot = Robot(ogPose, this, this.telemetry)
        robot.start()

        setup()

        waitForStart()

        if (isStopRequested) {
            return
        }

        run()
        //robot.stop()
    }
}
