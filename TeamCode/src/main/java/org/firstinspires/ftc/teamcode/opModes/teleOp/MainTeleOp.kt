package org.firstinspires.ftc.teamcode.opModes.teleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.computerDebuging.ComputerDebugging
import org.firstinspires.ftc.teamcode.path.PathBuilder
import org.firstinspires.ftc.teamcode.path.PathFollower
import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Pose2d
import org.firstinspires.ftc.teamcode.util.Vector2d

@TeleOp(name = "Mecanum Drive", group = "T")
class MainTeleOp : OpMode() {
    private lateinit var robot: Robot
    private lateinit var computerDebugging: ComputerDebugging
    var t = PathBuilder(Pose2d(15.0, 15.0, 0.0))

    var path = t
            .addPoint(Vector2d(200.0, 30.0), "moving forward")
//            .addPoint(Vector2d(30.0 * 2, 45.72 * 2), "moving forward")
//            .addPoint(Vector2d(45.72 * 2, 152.4 * 2), "moving forward")
            .create()


    val pathFollower = PathFollower(path, 10.0)

    override fun init() {
        robot = Robot(this, this.telemetry)
        robot.start()

        computerDebugging = ComputerDebugging()

        ComputerDebugging.sendPaths(path)
        ComputerDebugging.sendPacket()
    }

    override fun loop() {
        ComputerDebugging.sendPaths(path)
        robot.update()

        val powers = pathFollower.followCurve(0.0, robot.drive.position, 0.5, 2.0)

        robot.drive.setVelocity(
                Vector2d(0.0, powers[1]), 0.0)

        telemetry.addData("x", powers[0])
        telemetry.addData("y", powers[1])
        telemetry.addData("c", powers[2])

        telemetry.addData("pos", robot.drive.position)

        var fixPos = Pose2d(robot.drive.position.x, robot.drive.position.y, robot.drive.position.heading * (180 / Math.PI))

        ComputerDebugging.sendRobotLocation(fixPos)
        ComputerDebugging.sendPoint(pathFollower.getLookAheadPoint(robot.drive.position))
        ComputerDebugging.sendPacket()
    }
}
