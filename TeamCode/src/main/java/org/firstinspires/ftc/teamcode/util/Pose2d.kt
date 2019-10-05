package org.firstinspires.ftc.teamcode.Util

class Pose2d(var x: Double, var y: Double, var heading: Double) {

    /** @return a vector2d comprised of the x and y of the vector (position)
     */
    fun pos(): Vector2d {
        return Vector2d(x, y)
    }

    /**
     * Adds two Poses
     *
     * @param other Another Pose
     * @return the sum of the Poses
     */
    operator fun plus(other: Pose2d): Pose2d {
        return Pose2d(x + other.x, y + other.y, heading + other.heading)
    }

    /**
     * Subtracts two Poses
     *
     * @param other Another Pose
     * @return the diffrence of the Poses
     */
    operator fun minus(other: Pose2d): Pose2d {
        return Pose2d(x - other.x, y - other.y, heading - other.heading)
    }

    /**
     * multiplys a Pose by a scalar
     *
     * @param scalar A double to scale the vector by
     * @return a scaled Pose2d
     */
    operator fun times(scalar: Double): Pose2d {
        return Pose2d(x * scalar, y * scalar, heading * scalar)
    }

    /**
     * divides a Pose by a scalar
     *
     * @param scalar A double to scale the vector by
     * @return a scaled Pose2d
     */
    operator fun div(scalar: Double): Pose2d {
        return Pose2d(x / scalar, y / scalar, heading / scalar)
    }

    /**
     * negates the values of the psoe
     *
     * @return a negated Pose2d
     */
    operator fun unaryMinus(): Pose2d {
        return Pose2d(-x, -y, -heading)
    }

    /** @return the Pose translated into a string
     */
    override fun toString(): String {
        return String.format("(%.3f, %.3f, %.3f°)", x, y, heading)
    }

    /**
     * checks if the pose is equall to another pose
     *
     * @param other another pose
     * @return true or false
     */
    fun equals(other: Pose2d): Boolean {
        return if (other is Pose2d) {
            (Math.abs(x - other.x) < 1e-4
                    && Math.abs(y - other.y) < 1e-4
                    && Math.abs(heading - other.heading) < 1e-4)
        } else {
            false
        }
    }
}
