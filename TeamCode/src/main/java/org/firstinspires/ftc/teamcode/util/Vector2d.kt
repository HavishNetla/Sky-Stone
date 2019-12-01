package org.firstinspires.ftc.teamcode.util

import kotlin.math.pow
import kotlin.math.sqrt

class Vector2d(var x: Double, var y: Double) {

    // =============================================

    fun norm(): Double {
        return Math.sqrt(x * x + y * y)
    }

    fun angle(): Double {
        return Math.atan2(y, x)
    }

    operator fun plus(other: Vector2d): Vector2d {
        return Vector2d(x + other.x, y + other.y)
    }

    operator fun minus(other: Vector2d): Vector2d {
        return Vector2d(x - other.x, y - other.y)
    }

    operator fun times(scalar: Double): Vector2d {
        return Vector2d(x * scalar, y * scalar)
    }

    operator fun div(scalar: Double): Vector2d {
        return Vector2d(x / scalar, y / scalar)
    }

    operator fun unaryMinus(): Vector2d {
        return Vector2d(-x, -y)
    }

    fun dot(other: Vector2d): Double {
        return x * other.x + y * other.y
    }

    fun distanceTo(other: Vector2d): Double {
        return this.minus(other).norm()
    }

    fun rotated(angle: Double): Vector2d {
        // 10 * 1              - 0
        val newX = x * Math.cos(angle) - y * Math.sin(angle)
        // 10 * 0              + 0
        val newY = x * Math.sin(angle) + y * Math.cos(angle)
        return Vector2d(newX, newY)
    }

    fun dist(other: Vector2d): Double {
        return sqrt((this.x - other.x).pow(2.0) + (this.y - other.y).pow(2.0))
    }

    override fun toString(): String {
        return java.lang.String.format("(%.3f, %.3f)", x, y)
    }

    fun equals(other: Vector2d): Boolean {
        return if (other is Vector2d) {
            Math.abs(x - other.x) < 1e-4 && Math.abs(y - other.y) < 1e-4
        } else {
            false
        }
    }
}
