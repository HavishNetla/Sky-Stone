/*
 * Copyright (c) 2018 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.openftc.revextensions2.examples

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.openftc.revextensions2.ExpansionHubEx
import org.openftc.revextensions2.ExpansionHubMotor
import org.openftc.revextensions2.RevBulkData

@TeleOp(group = "RevExtensions2Examples")
class BulkDataExample : OpMode() {
    var bulkData: RevBulkData? = null
    var bulkData2: RevBulkData? = null

    // Encoder Wheels
    private val left: DcMotor? = null
    private val right: DcMotor? = null
    private val center: DcMotor? = null

    var FR: ExpansionHubMotor? = null
    var FL: ExpansionHubMotor? = null
    var BR: ExpansionHubMotor? = null
    var BL: ExpansionHubMotor? = null
    var R: ExpansionHubMotor? = null
    var L: ExpansionHubMotor? = null
    var C: ExpansionHubMotor? = null
    var expansionHub: ExpansionHubEx? = null
    var expansionHub2: ExpansionHubEx? = null


    override fun init() { /*
         * Before init() was called on this user code, REV Extensions 2
         * was notified via OpModeManagerNotifier.Notifications and
         * it automatically took care of initializing the new objects
         * in the hardwaremap for you. Historically, you would have
         * needed to call RevExtensions2.init()
         */
        expansionHub = hardwareMap.get(ExpansionHubEx::class.java, "Expansion Hub 4")
        FR = hardwareMap.dcMotor["FR"] as ExpansionHubMotor
        FL = hardwareMap.dcMotor["FL"] as ExpansionHubMotor
        BR = hardwareMap.dcMotor["BR"] as ExpansionHubMotor
        BL = hardwareMap.dcMotor["BL"] as ExpansionHubMotor

        expansionHub2 = hardwareMap.get(ExpansionHubEx::class.java, "Expansion Hub 2")
        R = hardwareMap.dcMotor["R"] as ExpansionHubMotor
        L = hardwareMap.dcMotor["L"] as ExpansionHubMotor
        C = hardwareMap.dcMotor["C"] as ExpansionHubMotor

    }

    override fun loop() { /*
         * ------------------------------------------------------------------------------------------------
         * Bulk data
         *
         * NOTE: While you could get all of this information by issuing many separate commands,
         * the amount of time required to fetch the information would increase drastically. By
         * reading all of this information in one command, we can loop at over 300Hz (!!!)
         * ------------------------------------------------------------------------------------------------
         */
        bulkData = expansionHub!!.bulkInputData
        bulkData2 = expansionHub2!!.bulkInputData
        val header = "**********************************\n" +
                "BULK DATA EXAMPLE                 \n" +
                "**********************************\n"
        telemetry.addLine(header)
        /*
         * Encoders
         */telemetry.addData("FR", bulkData!!.getMotorCurrentPosition(FR))
        telemetry.addData("FL", bulkData!!.getMotorCurrentPosition(FL))
        telemetry.addData("BR", bulkData!!.getMotorCurrentPosition(BR))
        telemetry.addData("BL", bulkData!!.getMotorCurrentPosition(BL))

        telemetry.addData("R", bulkData2!!.getMotorCurrentPosition(R))
        telemetry.addData("L", bulkData2!!.getMotorCurrentPosition(L))
        telemetry.addData("C", bulkData2!!.getMotorCurrentPosition(C))

//        /*
//         * Encoder velocities
//         */telemetry.addData("M0 velocity", bulkData.getMotorVelocity(motor0))
//        telemetry.addData("M1 velocity", bulkData.getMotorVelocity(motor1))
//        telemetry.addData("M2 velocity", bulkData.getMotorVelocity(motor2))
//        telemetry.addData("M3 velocity", bulkData.getMotorVelocity(motor3))
//        /*
//         * Is motor at target position?
//         */telemetry.addData("M0 at target pos", bulkData.isMotorAtTargetPosition(motor0))
//        telemetry.addData("M1 at target pos", bulkData.isMotorAtTargetPosition(motor1))
//        telemetry.addData("M2 at target pos", bulkData.isMotorAtTargetPosition(motor2))
//        telemetry.addData("M3 at target pos", bulkData.isMotorAtTargetPosition(motor3))
//        /*
//         * Analog voltages
//         */telemetry.addData("A0", bulkData.getAnalogInputValue(a0))
//        telemetry.addData("A1", bulkData.getAnalogInputValue(a1))
//        telemetry.addData("A2", bulkData.getAnalogInputValue(a2))
//        telemetry.addData("A3", bulkData.getAnalogInputValue(a3))
//        /*
//         * Digital states
//         */telemetry.addData("D0", bulkData.getDigitalInputState(d0))
//        telemetry.addData("D1", bulkData.getDigitalInputState(d1))
//        telemetry.addData("D2", bulkData.getDigitalInputState(d2))
//        telemetry.addData("D3", bulkData.getDigitalInputState(d3))
//        telemetry.addData("D4", bulkData.getDigitalInputState(d4))
//        telemetry.addData("D5", bulkData.getDigitalInputState(d5))
//        telemetry.addData("D6", bulkData.getDigitalInputState(d6))
//        telemetry.addData("D7", bulkData.getDigitalInputState(d7))
          telemetry.update()
    }
}