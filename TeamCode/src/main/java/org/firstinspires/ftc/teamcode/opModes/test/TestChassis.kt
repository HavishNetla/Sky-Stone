package org.firstinspires.ftc.teamcode.opModes.test

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp

import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Vector2d

@TeleOp(name = "Test Chassis Drive", group = "T")
class TestChassis : OpMode() {
    private lateinit var robot: Robot

    override fun init() {
        robot = Robot(this)
        robot.start()
    }

    override fun loop() {
        robot.drive.setVelocity(
                Vector2d(gamepad1.left_stick_x.toDouble(), gamepad1.left_stick_y.toDouble()), gamepad1.right_stick_x.toDouble())

        telemetry.addData("Position", robot.drive.position)
    }
}
