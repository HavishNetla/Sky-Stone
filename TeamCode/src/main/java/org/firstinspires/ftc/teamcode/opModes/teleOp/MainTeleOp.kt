package org.firstinspires.ftc.teamcode.opModes.teleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.computerDebuging.ComputerDebugging
import org.firstinspires.ftc.teamcode.path.PathBuilder
import org.firstinspires.ftc.teamcode.path.PathFollower
import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import java.text.DecimalFormat

@TeleOp(name = "Mecanum Drive", group = "T")
class MainTeleOp : OpMode() {
    private lateinit var robot: Robot
    private lateinit var computerDebugging: ComputerDebugging
    private var df = DecimalFormat("#.##")

    var t = PathBuilder(Pose2d(15.0, 15.0, 0.0))

    var path = t
            .addPoint(Vector2d(15.0, 200.0), "moving forward")
//            .addPoint(Vector2d(30.0 * 2, 45.72 * 2), "moving forward")
//            .addPoint(Vector2d(45.72 * 2, 152.4 * 2), "moving forward")
            .create()


    val pathFollower = PathFollower(path, 10.0)

    override fun init() {
        robot = Robot(this, this.telemetry)
        robot.start()

        computerDebugging = ComputerDebugging()

        ComputerDebugging.sendPaths(path)
        //ComputerDebugging.sendPacket()
        robot.drive.resetEncoders()

    }

    override fun loop() {
        // ComputerDebugging.sendPaths(path)
        robot.update()

//        robot.drive.setVelocity(Vector2d(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble()), gamepad1.right_stick_x.toDouble())

        val powers = pathFollower.followCurve(0.0, robot.drive.position, 0.25, 2.0)

        robot.drive.setVelocity(
                Vector2d(-powers[0], powers[1]), 0.0)

        var fixPos = Pose2d(robot.drive.position.x, robot.drive.position.y, robot.drive.position.heading * (180 / Math.PI))

        telemetry.addData("left", robot.drive.trackingWheelPositions[0])
        telemetry.addData("right", robot.drive.trackingWheelPositions[1])
        telemetry.addData("center", robot.drive.trackingWheelPositions[2])

        ComputerDebugging.sendRobotLocation(fixPos)
        ComputerDebugging.sendPoint(pathFollower.getLookAheadPoint(robot.drive.position))
        ComputerDebugging.sendLog("\nPosition - " + robot.drive.position.toString() +
                "\nPowers - " + df.format(powers[0]) + ", " + df.format(powers[1]) + ", " + df.format(powers[2]) +
                "\nPowersg - " + df.format(gamepad1.left_stick_x) + ", " + df.format(gamepad1.left_stick_y) + ", " + df.format(gamepad1.right_stick_x) +
                "\nRelTurn - " + PathFollower.relTurnAngle
        )
        ComputerDebugging.sendPacket()
    }
}
