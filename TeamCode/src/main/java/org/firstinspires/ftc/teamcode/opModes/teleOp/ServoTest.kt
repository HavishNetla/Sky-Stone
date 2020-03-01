package org.firstinspires.ftc.teamcode.opModes.auto

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.Pose2d

@Autonomous(name = "Servo Test", group = "T")
class ServoTest : AutoOpMode(Pose2d(20.7, 81.7, -Math.PI / 2)) {
    override fun setup() {

    }
    override fun run() {
//        if (gamepad1.a) {
//            robot.drive.grabFoundation()
//        } else {
//            robot.drive.openFoundationGrabber()
//        }


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

//        when {
//            gamepad1.a -> robot.drive.grabBlock()
//            gamepad1.b -> robot.drive.stowBlock()
//            gamepad1.y -> robot.drive.releaseBlock()
//        }

//        robot.drive.grabBlock()
//        robot.drive.stowBlock()
//        robot.drive.releaseBlock()
//

            var time=2.0
        while(opModeIsActive()) {
//            robot.drive.readyBlock()
//            robot.drive.specialDelay(time)
//            robot.drive.grabBlock()
//            robot.drive.specialDelay(time)
//            robot.drive.stowBlockNoDelay()
//            robot.drive.specialDelay(time)
////            robot.drive.placeHoldBlock()
////            robot.drive.specialDelay(time)
////            robot.drive.placeBlock()
//            robot.drive.throwBlock()
////            robot.drive.specialDelay(time)
//            robot.drive.stowBlockNoDelay()
//            robot.drive.specialDelay(time)
            //robot.drive.foundationPrep()


            robot.drive.readyBlockRedNoDelay()
            robot.drive.specialDelay(time)
            robot.drive.grabBlockRed()
            robot.drive.specialDelay(time)
            robot.drive.stowBlockRed()
            robot.drive.specialDelay(time)
            robot.drive.throwBlockRed()
            robot.drive.specialDelay(time)
        }
    }
}