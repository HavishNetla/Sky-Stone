package org.firstinspires.ftc.teamcode.opModes.test

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp(name = "Test Chassis Drive", group = "T")
class TestChassis : OpMode() {
    //private lateinit var robot: Robot
    private lateinit var frontLeft: DcMotor
    private lateinit var frontRight: DcMotor
    private lateinit var backLeft: DcMotor

    override fun init() {
        frontLeft = hardwareMap.get(DcMotor::class.java, "FL")
        frontRight = hardwareMap.get(DcMotor::class.java, "FR")
        backLeft = hardwareMap.get(DcMotor::class.java, "BL")

    }

    override fun loop() {
        telemetry.addData("FL", frontLeft.currentPosition)
        telemetry.addData("FR", frontRight.currentPosition)
        telemetry.addData("BL", backLeft.currentPosition)

    }
}
