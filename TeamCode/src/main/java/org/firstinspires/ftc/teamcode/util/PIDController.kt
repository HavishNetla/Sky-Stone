package org.firstinspires.ftc.teamcode.util

class PIDController(var p: Double, var i: Double, var d: Double) {
    var integral: Double = 0.0
    var derivative: Double = 0.0

    var prev_error: Double = 0.0

    fun getError(desired: Double, current: Double): Double {
        var error = desired - current
        integral += error * 0.2

        derivative = (error - prev_error) / 0.02

        return p * error + i * integral + d * derivative
    }
}