package org.firstinspires.ftc.teamcode.opModes.teleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.UtilToggle
import org.firstinspires.ftc.teamcode.util.Vector2d
import kotlin.math.pow

@TeleOp(name = "Mecanum Drive", group = "T")
class MainTeleOp : OpMode() {
    private lateinit var robot: Robot
    private lateinit var powers: DoubleArray
    private val slowToggle = UtilToggle()    // Slows down drivetrain when on

    override fun init() {
        robot = Robot(this, this.telemetry)

        telemetry.addData("ff", "fff")
        robot.start()
    }

    override fun start() {
        robot.drive.resetEncoders()
    }

    override fun loop() {
        //robot.update()

        var scalar = 1.0
        if (gamepad1.right_trigger > 0) {
            scalar = 0.2
        } else if (gamepad1.left_trigger > 0) {
            scalar = 0.5
        }
        var g1Lx = gamepad1.left_stick_x.toDouble() * scalar * 0.6
        var g1Ly = gamepad1.left_stick_y.toDouble() * scalar * 0.6
        var g1Rx = gamepad1.right_stick_x.toDouble() * scalar * 0.4

        robot.drive.setVelocity(Vector2d(g1Lx.pow(1.0) * 0.8,
                g1Ly.pow(1.0)) * 0.8,
                g1Rx.pow(1.0) * 0.8
        )

        if (gamepad2.a) {
            robot.lift.grap()
        }

        // ===============================
        if (gamepad2.x) {
            robot.lift.setLinkagePos(0.78)
        }

        //===============================

        var status = slowToggle.status(gamepad2.y)
        if (status == UtilToggle.Status.IN_PROGRESS) {
            println("statut1: got in here1")
            robot.lift.open()
        } else if (status == UtilToggle.Status.COMPLETE) {
            println("statut1: got in here")
            robot.lift.setLinkagePos(0.36)
        }

        //===============================
        if (robot.lift.touchSensorState || gamepad2.back) {
            when {
                gamepad2.right_trigger > 0.0 -> robot.intake.power = gamepad2.right_trigger.toDouble() * 0.8
                gamepad2.left_trigger > 0.0 -> robot.intake.power = -0.8
                else -> robot.intake.power = 0.0
            }
        }

        robot.lift.lift(gamepad2.left_stick_y.toDouble())

        telemetry.addData("touch sensor", robot.lift.touchSensorState)
        telemetry.addData("lift encoder", robot.lift.encoderValue)

        //===================================

        if (gamepad2.dpad_up) {
            robot.lift.openCap()
        } else if (gamepad2.dpad_down) {
            robot.lift.stowCap()
        }

        telemetry.addData("asd", robot.lift.pid)

    }

    override fun stop() {
        robot.stop()
        powers = doubleArrayOf(0.0, 0.0, 0.0)
    }
}
