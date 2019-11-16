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
import java.util.*

@TeleOp(name = "Mecanum Drive", group = "T")
class MainTeleOp : OpMode() {
    private lateinit var robot: Robot
    private lateinit var computerDebugging: ComputerDebugging
    var t = PathBuilder(Pose2d(0.0, 0.0, 0.0))

    var path: ArrayList<PathSegment> =
            t.addPoint(Vector2d(0.0, 200.0), "moving forward")
                    .addPoint(Vector2d(200.0, 200.0), "moving forward")
                    .create()

    val pathFollower = PathFollower(path, 19.685 * 2.54)

    override fun init() {
        robot = Robot(this, this.telemetry)
        robot.start()

        computerDebugging = ComputerDebugging()

        ComputerDebugging.sendPaths(path)
        ComputerDebugging.sendPacket()

    }

    override fun loop() {
        robot.update()

        //pathFollower.update(robot.drive.position)

        //ComputerDebugging.sendPaths(path)

        //val powers = pathFollower.followCurve(0.0, robot.drive.position, 0.5, 2.0)

        //robot.drive.setVelocity(
        //    Vector2d(powers[0], powers[1]), powers[2])
        robot.update()


        robot.drive.setVelocity(
                Vector2d(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble()), gamepad1.right_stick_x.toDouble())

        //telemetry.addData("Position", robot.drive.position)

        var fixPos = Pose2d(robot.drive.position.x, robot.drive.position.y, -robot.drive.position.heading * (180 / Math.PI))
        ComputerDebugging.sendRobotLocation(fixPos)
        ComputerDebugging.sendNoClear(robot.drive.position.pos())
        //ComputerDebugging.sendPoint(pathFollower.lookAheadPoint)
        ComputerDebugging.sendPacket()
    }
}
