package org.firstinspires.ftc.teamcode.opModes.teleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Vector2d

@TeleOp(name = "Mecanum Drive", group = "T")
class MainTeleOp : OpMode() {
    private lateinit var robot: Robot
    private var count: Int = 0
    override fun init() {
        robot = Robot(this, this.telemetry)
        robot.start()
    }

    override fun loop() {
        robot.update()
        robot.drive.setVelocity(
                Vector2d(gamepad1.left_stick_x.toDouble(), -gamepad1.left_stick_y.toDouble()), gamepad1.right_stick_x.toDouble())
        count++
    }
}
