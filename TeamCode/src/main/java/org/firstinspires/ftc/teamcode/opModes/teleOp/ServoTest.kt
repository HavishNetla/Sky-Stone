package org.firstinspires.ftc.teamcode.opModes.teleOp

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.subsystems.Robot

@TeleOp(name = "Servo Test", group = "T")
class ServoTest : OpMode() {
    private lateinit var robot: Robot
    private var pos = 0.5
    private var b = true
    private var lastGamePad: Gamepad = Gamepad()

    override fun init() {
        robot = Robot(this, this.telemetry)
        robot.start()
    }

    override fun loop() {
        if (gamepad1.a) {
            robot.drive.grabFoundation()
        } else {
            robot.drive.openFoundationGrabber()
        }


//        telemetry.addData("pos", pos)
//
//
//        if (gamepad2.a) {
//            robot.lift.grap()
//        }
//
//        // ===============================
//        if (gamepad2.x) {
//            robot.lift.setLinkagePos(0.78)
//        }
//
//        //===============================
//        println("statut1: " + gamepad2.y    + ", " + lastGamePad.y)
//
//        val slowToggle = UtilToggle()    // Slows down drivetrain when on
//        if (gamepad2.y) {
//            println("statut1: got in here1")
//            robot.lift.open()
//        }
//        if (slowToggle.status(gamepad2.y) == UtilToggle.Status.COMPLETE) {
//            println("statut1: got in here")
//            robot.lift.setLinkagePos(0.36)
//        }
//
//        //===============================
//        when {
//            gamepad1.right_trigger > 0.0 -> robot.intake.power = gamepad1.right_trigger.toDouble()
//            gamepad1.right_bumper -> robot.intake.power = -0.8
//            else -> robot.intake.power = 0.0
//        }
//
//
//        robot.lift.lift(gamepad2.left_stick_y.toDouble())
//
//        telemetry.addData("touch sensor", robot.lift.touchSensorState)
//        telemetry.addData("lift encoder", robot.lift.encoderValue)
//
//        lastGamePad.copy(gamepad2)


    }
}