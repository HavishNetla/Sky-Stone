package org.firstinspires.ftc.teamcode.opModes.teleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.Robot

@TeleOp(name = "Servo Test", group = "T")
class ServoTest : OpMode() {
    private lateinit var robot: Robot
    private var pos = 0.5
    private var b = true

    override fun init() {
        robot = Robot(this, this.telemetry)
        robot.start()
    }

    override fun loop() {
        if (gamepad1.a) {
            robot.lift.grap()
        } else {
            robot.lift.open()
        }

        if (gamepad1.b) {
            pos += 0.001
        } else if (gamepad1.y) {
            pos -= 0.001
        }
        telemetry.addData("pos",  pos)
        robot.lift.setLinkagePos(pos)
//        robot.lift.spinRotater(Lift.ROTATER_POSITION.RIGHT)

    }
}