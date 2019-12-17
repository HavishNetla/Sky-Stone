package org.firstinspires.ftc.teamcode.opModes.teleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.computerDebuging.ComputerDebugging
import org.firstinspires.ftc.teamcode.path.PathBuilder
import org.firstinspires.ftc.teamcode.path.PathFollower
import org.firstinspires.ftc.teamcode.path.PathSegment
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive
import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d
import java.text.DecimalFormat
import java.util.*

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
    private lateinit var powers: DoubleArray
    private lateinit var pathFollower: PathFollower

    override fun init() {
        robot = Robot(this, this.telemetry)
        pathFollower = PathFollower(path, 55.0)
        computerDebugging = ComputerDebugging()

        ComputerDebugging.sendPaths(path)
        ComputerDebugging.sendPacket()
    }

    override fun init_loop() {
        robot.update()
        telemetry.addData("position", robot.drive.position)
        telemetry.addData("lookahead point", pathFollower.lookAheadPoint)
    }

    override fun start() {
        robot.drive.resetEncoders()
    }

    override fun loop() {
        // ComputerDebugging.sendPaths(path)
        robot.update()

//      robot.drive.setVelocity(Vector2d(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble()), gamepad1.right_stick_x.toDouble())

        robot.drive.followPath(path, pathFollower)
        var fixPos = Pose2d(robot.drive.position.x, robot.drive.position.y, robot.drive.position.heading * (180 / Math.PI))

        ComputerDebugging.sendRobotLocation(fixPos)
        ComputerDebugging.sendPoint(pathFollower.getLookAheadPoint(robot.drive.position))
        ComputerDebugging.sendLog("\nPosition - " + robot.drive.position.toString() +
                "\nPowersg - " + df.format(gamepad1.left_stick_x) + ", " + df.format(gamepad1.left_stick_y) + ", " + df.format(gamepad1.right_stick_x) +
                "\nRelTurn - " + PathFollower.relTurnAngle
        )
        ComputerDebugging.sendPacket()

        telemetry.addData("position", robot.drive.position)
        telemetry.addData("powers", "" + MecanumDrive.pathPowers[0] + ", " + MecanumDrive.pathPowers[1] + ", " + MecanumDrive.pathPowers[2])
        telemetry.addData("lookahead point", pathFollower.lookAheadPoint)
    }

    override fun stop() {
        robot.stop()
        powers = doubleArrayOf(0.0, 0.0, 0.0)


    }
}
