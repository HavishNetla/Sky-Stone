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
    var t = PathBuilder(Pose2d(40.0, 40.0, 0.0))

    var path = t.addPoint(Vector2d(100.0, 30.0), "moving forward")
            .addPoint(Vector2d(30.0, 200.0), "moving forward")
            .addPoint(Vector2d(200.0, 300.0), "moving forward")
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

        ComputerDebugging.sendPaths(path)

        val powers = pathFollower.followCurve(0.0, robot.drive.position, 0.5, 2.0)

        robot.drive.setVelocity(
            Vector2d(powers[0], powers[1]), powers[2])

//        val powers = PathFollower.goToPoint(Vector2d(50.0, 100.0), robot.drive.position, 0.0, 0.5, 2.0)
//        telemetry.addData("powers0", powers[0])
//        telemetry.addData("powers1", powers[1])
//        telemetry.addData("powers2", powers[2])

        robot.drive.setVelocity(
                Vector2d(powers[0], powers[1]), powers[2])


//        robot.drive.setVelocity(
//                Vector2d(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble()), gamepad1.right_stick_x.toDouble())

        //telemetry.addData("Position", robot.drive.position)

        var fixPos = Pose2d(robot.drive.position.x, robot.drive.position.y, robot.drive.position.heading * (180 / Math.PI))
        ComputerDebugging.sendRobotLocation(fixPos)
        //ComputerDebugging.sendNoClear(robot.drive.position.pos())
        ComputerDebugging.sendPoint(pathFollower.getLookAheadPoint(robot.drive.position))
        ComputerDebugging.sendPacket()
    }
}
