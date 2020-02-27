package org.firstinspires.ftc.teamcode.opModes.teleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.localization.ThreeWheelLocalizer
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive
import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.UtilToggle
import org.firstinspires.ftc.teamcode.util.Vector2d
import kotlin.math.abs
import kotlin.math.pow

@TeleOp(name = "Mecanum Drive", group = "T")
class MainTeleOp : OpMode() {
    private lateinit var robot: Robot
    private lateinit var powers: DoubleArray
    private val slowToggle = UtilToggle()    // Slows down drivetrain when on
    private val dpadTogggleUp = UtilToggle()
    private val dpadTogggleDown = UtilToggle()
    private var liftPos: Int = 0
    private lateinit var statusUp: UtilToggle.Status
    private lateinit var statusDown: UtilToggle.Status
    private var lastPressed = ""
    private lateinit var eTime: ElapsedTime

    override fun init() {
        robot = Robot(Pose2d(20.32, 81.7, -Math.PI / 2), this, this.telemetry)

        telemetry.addData("ff", "fff")
        robot.start()
        robot.drive.stowBlockRedTele()
        eTime = ElapsedTime()
    }

    override fun start() {
        robot.drive.resetEncoders()

        //robot.drive.setMode(MecanumDrive.LocalizerMode.THREE_WHEEL_LOCALIZER)
    }

    override fun loop() {
        var scalar = 1.0
        if (gamepad1.right_trigger > 0 || gamepad1.a) {
            scalar = 0.2
        } else if (gamepad1.left_trigger > 0) {
            scalar = 0.5
        }
        var g1Lx = gamepad1.left_stick_x.toDouble() * scalar * 1.0
        var g1Ly = gamepad1.left_stick_y.toDouble() * scalar * 1.0
<<<<<<< Updated upstream
        var g1Rx = gamepad1.right_stick_x.toDouble() * scalar * 0.9

=======
        var g1Rx = gamepad1.right_stick_x.toDouble() * scalar * 0.80
>>>>>>> Stashed changes

//        telemetry.addData("PID", robot.drive.pid)
        when {
            gamepad1.dpad_up -> {
                g1Lx = 0.0
                g1Ly = -0.2 * scalar
                g1Rx = 0.0
            }
            gamepad1.dpad_left -> {
                g1Lx = -0.2 * scalar
                g1Ly = 0.0
                g1Rx = 0.0
            }
            gamepad1.dpad_right -> {
                g1Lx = 0.2 * scalar
                g1Ly = 0.0
                g1Rx = 0.0
            }
            gamepad1.dpad_down -> {
                g1Lx = 0.0
                g1Ly = 0.2 * scalar
                g1Rx = 0.0
            }
        }


        var dpadStatus = gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.dpad_left || gamepad1.dpad_right

        if (gamepad1.left_stick_x != 0.0f || gamepad1.left_stick_y != 0.0f || gamepad1.right_stick_x != 0.0f || dpadStatus) {
            robot.drive.mode = MecanumDrive.Mode.OPEN_LOOP
            robot.drive.setVelocity(Vector2d(g1Lx.pow(1.0),
                    g1Ly.pow(1.0)),
                    g1Rx.pow(1.0)
            )
        } else if (abs(gamepad2.right_stick_x) > 0.1f || abs(gamepad2.right_stick_y) > 0.1f) {
            robot.drive.setVelocity(Vector2d(gamepad2.right_stick_x * 0.075,
                    gamepad2.right_stick_y * 0.075),
                    0.0
            )
        } else {
            robot.drive.setVelocity(Vector2d(0.0,
                    0.0), 0.0
            )
        }

        if (gamepad2.a) {
            robot.lift.grap()
        }

        // ===============================
        if (gamepad2.x) {
            robot.lift.linkagePos = 0.78
        }

        //===============================

        var status = slowToggle.status(gamepad2.y)
        if (status == UtilToggle.Status.IN_PROGRESS) {
//            println("statut1: got in here1")
            robot.lift.open()
        } else if (status == UtilToggle.Status.COMPLETE) {
//            println("statut1: got in here")

            if (robot.lift.linkagePos != 0.36) {
                robot.lift.liftStatus = Lift.LIFT_STATUS.RUN_TO_POSITION
                robot.lift.setTargetPosition(robot.lift.currentPosition - 100)
            }

            robot.lift.linkagePos = 0.36
        }

        //===============================
        if (robot.lift.touchSensorState || gamepad2.back) {
            when {
                gamepad2.back -> robot.intake.openIntakeBois()
                gamepad2.right_trigger > 0.0 -> robot.intake.power = gamepad2.right_trigger.toDouble() * 0.8
                gamepad2.left_trigger > 0.0 -> robot.intake.power = -0.8
                else -> robot.intake.power = 0.0
            }
        }

        //robot.lift.lift(gamepad2.left_stick_y.toDouble())


//        telemetry.addData("touch sensor", robot.lift.touchSensorState)
//        telemetry.addData("lift encoder", robot.lift.encoderValue)

        //===================================

        if (gamepad2.left_bumper) {
            robot.lift.openCap()
        } else if (gamepad2.right_bumper) {
            robot.lift.stowCap()
        }

        if (gamepad1.a) {
            robot.drive.grabFoundationTele()
        } else {
            robot.drive.openFoundationGrabberTele()
        }


        statusUp = dpadTogggleUp.status(gamepad2.dpad_up)
        statusDown = dpadTogggleDown.status(gamepad2.dpad_down)

        if (abs(gamepad2.left_stick_y) > 0.05f) {
            robot.lift.liftStatus = Lift.LIFT_STATUS.MANUAL
            robot.lift.setLiftPower(gamepad2.left_stick_y.toDouble() * 0.5)
        } else {
            // Manual is never left and 0.0 is set.
            robot.lift.setLiftPower(0.0)

            if (statusDown == UtilToggle.Status.COMPLETE && gamepad2.b) {
                robot.lift.liftStatus = Lift.LIFT_STATUS.RUN_TO_POSITION
                liftPos += 238

                robot.lift.setTargetPosition(liftPos)
            } else if (statusUp == UtilToggle.Status.COMPLETE) {
                robot.lift.liftStatus = Lift.LIFT_STATUS.RUN_TO_POSITION
                liftPos -= 238

                robot.lift.setTargetPosition(liftPos)
            } else if (gamepad2.dpad_down) {
                robot.lift.liftStatus = Lift.LIFT_STATUS.RESETING
            }
        }

        println("CURRENT POSITION: " + robot.lift.currentPosition)

//        telemetry.addData("LIFT STATUS", robot.lift.liftStatus)
//        telemetry.addData("LIFT POS", liftPos)
//        telemetry.addData("LIFT ENCODER POS", robot.lift.liftEncoderPos)
//        telemetry.addData("VAL", robot.lift.currentPosition)
//        telemetry.addData("TOUCH SENSOR", robot.lift.touchSensorState)

//        telemetry.addData("dX", ThreeWheelLocalizer.dX)
//        telemetry.addData("dY", ThreeWheelLocalizer.dY)
//        telemetry.addData("dL",ThreeWheelLocalizer.dL)
//        telemetry.addData("dR", ThreeWheelLocalizer.dR)
        telemetry.addData("X",ThreeWheelLocalizer.x)
        telemetry.addData("Y",ThreeWheelLocalizer.y)
        telemetry.addData("Theta", ThreeWheelLocalizer.theta)
        telemetry.update()
//
//

        when {
            gamepad1.x -> {
                lastPressed = "x"
                robot.drive.setTapeCapPower(1.0)
            }
            gamepad1.b -> {
                lastPressed = "a"
                robot.drive.setTapeCapPower1(1.0)
            }
            gamepad1.y -> {
                if (lastPressed == "a") {
                    robot.drive.setTapeCapPower1(-1.0)
                } else if (lastPressed == "x") {
                    robot.drive.setTapeCapPower(-1.0)
                }
            }
            else -> {
                robot.drive.setTapeCapPower(0.0)
                robot.drive.setTapeCapPower1(0.0)
            }
        }

        //telemetry.addData("POSITION", robot.drive.position)
        //telemetry.addData("WHEEL POSITION", robot.drive.trackingWheelPositions)
        //telemetry.update()
        //println("POSITION: " + robot.drive.position)

        if (eTime.time() == 31.0) {
           // telemetry.speak("FOUNDATION FIRST")
        } else if (eTime.time() == 10.0) {
           // telemetry.speak("TAPE MEASURE")
        }
    }

    override fun stop() {
        robot.stop()
        powers = doubleArrayOf(0.0, 0.0, 0.0)
    }
}
