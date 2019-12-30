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

//        if(gamepad1.a) {
//            robot.drive.grabFoundation()
//        } else {
//            robot.drive.openFoundationGrabber()
//        }
//        robot.lift.spinRotater(Lift.ROTATER_POSITION.RIGHT)

//        robot.intake.power = gamepad1.left_stick_x.toDouble()
//        telemetry.addData("intake", robot.intake.power)
//        telemetry.addData("touchSensor", robot.drive.touchSensorState)

//        if(gamepad1.a) {
//            robot.drive.setRotaterPos(1.0)
//        } else {
//            robot.drive.setRotaterPos(0.5)
//        }
//
//        if(gamepad1.b) {
//            robot.drive.setGrabberPos(0.3)
//        } else {
//            robot.drive.setGrabberPos(0.6)
//        }

        if (gamepad1.a) {
            robot.drive.grabBlock()
        }

        if (gamepad1.b) {
            robot.drive.releaseBlock()
        }

        if (gamepad1.y) {
            robot.drive.stowBlock()
        }
        robot.intake.power = gamepad1.left_stick_x.toDouble()

    }
}