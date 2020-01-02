package org.firstinspires.ftc.teamcode.util

class PIDController(var p: Double, var i: Double, var d: Double) {
    var integral: Double = 0.0
    var derivative: Double = 0.0

    var prevDesired: Double = 0.0
    var prevIntegral: Double = 0.0

    fun getError(desired: Double, current: Double): Double {
        var error = desired - current
        derivative = desired - prevDesired
        integral = prevIntegral + error

        prevDesired = desired
        prevIntegral = integral

        return p * error + i * integral + d * derivative
    }
}