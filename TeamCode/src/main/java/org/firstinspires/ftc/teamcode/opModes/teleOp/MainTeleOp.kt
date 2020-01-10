package org.firstinspires.ftc.teamcode.opModes.teleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.UtilToggle
import org.firstinspires.ftc.teamcode.util.Vector2d
import kotlin.math.pow

@TeleOp(name = "Mecanum Drive", group = "T")
class MainTeleOp : OpMode() {
    private lateinit var robot: Robot
    private lateinit var powers: DoubleArray
    private val slowToggle = UtilToggle()    // Slows down drivetrain when on
    private val dpadTogggleUp = UtilToggle()
    private val dpadTogggleDown = UtilToggle()
    private var liftPos: Int = 0

    override fun init() {
        robot = Robot(Pose2d(20.32, 81.7, -Math.PI / 2), this, this.telemetry)

        telemetry.addData("ff", "fff")
        robot.start()
    }

    override fun start() {
        robot.drive.resetEncoders()
    }

    override fun loop() {
        //robot.update()

        var scalar = 1.0
        if (gamepad1.right_trigger > 0 || gamepad1.a) {
            scalar = 0.2
        } else if (gamepad1.left_trigger > 0) {
            scalar = 0.5
        }
        var g1Lx = gamepad1.left_stick_x.toDouble() * scalar * 0.65
        var g1Ly = gamepad1.left_stick_y.toDouble() * scalar * 0.65
        var g1Rx = gamepad1.right_stick_x.toDouble() * scalar * 0.35

        when {
            gamepad1.dpad_up -> {
                g1Lx = 0.0
                g1Ly = -0.25 * scalar
                g1Rx = 0.0
            }
            gamepad1.dpad_left -> {
                g1Lx = -0.25 * scalar
                g1Ly = 0.0
                g1Rx = 0.0
            }
            gamepad1.dpad_right -> {
                g1Lx = 0.25 * scalar
                g1Ly = 0.0
                g1Rx = 0.0
            }
            gamepad1.dpad_down -> {
                g1Lx = 0.0
                g1Ly = 0.25 * scalar
                g1Rx = 0.0
            }
        }

        robot.drive.setVelocity(Vector2d(g1Lx.pow(1.0),
                g1Ly.pow(1.0)),
                g1Rx.pow(1.0)
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

        if (gamepad2.left_bumper) {
            robot.lift.openCap()
        } else if (gamepad2.right_bumper) {
            robot.lift.stowCap()
        }

        if (gamepad1.a) {
            robot.drive.grabFoundationTele()
        } else {
            robot.drive.openFoundationGrabber()
        }

        var statusUp = dpadTogggleUp.status(gamepad2.dpad_up)
        var statusDown = dpadTogggleUp.status(gamepad2.dpad_down)

        //robot.lift.setJoyStickPos(gamepad2.left_stick_y.toDouble())
        //if (gamepad2.left_stick_y == 0.0f) {

        //}

//        if (statusDown == UtilToggle.Status.COMPLETE) {
//            liftPos += 80
//        } else if (statusUp == UtilToggle.Status.COMPLETE) {
//            liftPos = 0
//        }
//        robot.lift.setLiftPos(liftPos)

        telemetry.addData("asd", robot.lift.pid)


        if (gamepad1.b) {
            robot.drive.setTapeCapPower(1.0)
        } else {
            robot.drive.setTapeCapPower(0.0)
        }


    }

    override fun stop() {
        robot.stop()
        powers = doubleArrayOf(0.0, 0.0, 0.0)
    }
}
