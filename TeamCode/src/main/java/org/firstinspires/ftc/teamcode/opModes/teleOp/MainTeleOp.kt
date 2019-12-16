package org.firstinspires.ftc.teamcode.opModes.teleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.computerDebuging.ComputerDebugging
import org.firstinspires.ftc.teamcode.path.PathBuilder
import org.firstinspires.ftc.teamcode.path.PathFollower
import org.firstinspires.ftc.teamcode.path.PathSegment
import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import java.text.DecimalFormat
import java.util.ArrayList

@TeleOp(name = "Mecanum Drive", group = "T")
class MainTeleOp : OpMode() {
    private lateinit var robot: Robot
    private lateinit var computerDebugging: ComputerDebugging
    private var df = DecimalFormat("#.##")

    private var t = PathBuilder(Pose2d(15.0, 15.0, 0.0))

    private var path: ArrayList<PathSegment> = t
            .addPoint(Vector2d(15.0, 200.0), "moving forward")
            .addPoint(Vector2d(200.0, 200.0), "moving forward")
//            .addPoint(Vector2d(30.0 * 2, 45.72 * 2), "moving forward")
//            .addPoint(Vector2d(45.72 * 2, 152.4 * 2), "moving forward")
            .create()


    private lateinit var pathFollower: PathFollower
    override fun init() {
        robot = Robot(this, this.telemetry)
        robot.start()
        pathFollower = PathFollower(path, 35.0)
        computerDebugging = ComputerDebugging()

        ComputerDebugging.sendPaths(path)
        ComputerDebugging.sendPacket()

        robot.drive.resetEncoders()
    }

    override fun loop() {
        // ComputerDebugging.sendPaths(path)
        robot.update()

//        robot.drive.setVelocity(Vector2d(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble()), gamepad1.right_stick_x.toDouble())

        val powers = pathFollower.followCurve(0.0, robot.drive.position, 0.25, 2.0)
//        val powers = PathFollower.goToPoint(Vector2d(15.0, 50.0), robot.drive.position, 0.0, 0.5, 0.25)

        robot.drive.setVelocity(
                Vector2d(powers[1], -powers[0]), powers[1])

        var fixPos = Pose2d(robot.drive.position.x, robot.drive.position.y, robot.drive.position.heading * (180 / Math.PI))

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
