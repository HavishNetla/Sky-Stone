package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.subsystems.Robot

abstract class AutoOpMode : LinearOpMode() {
    protected abstract fun setup()
    protected abstract fun run()

    protected lateinit var robot: Robot


    override fun runOpMode() {
        robot = Robot(this, this.telemetry)
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