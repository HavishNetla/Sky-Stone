package org.firstinspires.ftc.teamcode.util

import com.qualcomm.robotcore.util.ElapsedTime
import com.qualcomm.robotcore.util.Range

class PIDController(var p: Double, var i: Double, var d: Double) {
    var integral: Double = 0.0
    var derivative: Double = 0.0

    var prevDesired: Double = 0.0
    var prevIntegral: Double = 0.0
    var prevError: Double = 0.0
    var prevTime: Double = 0.0

    var eTime: ElapsedTime = ElapsedTime()

    fun getError(desired: Double, current: Double): Double {
        var currLoopTime: Double = eTime.time()

        var error = desired - current
        derivative = (error - prevError) / (currLoopTime - prevTime)
        integral = (prevIntegral + error) * (currLoopTime - prevTime)

        prevDesired = desired
        prevIntegral = integral
        prevError = error
        prevTime = currLoopTime


        return Range.clip((p * error + i * integral + d * derivative), -1.0, 1.0)
    }
}