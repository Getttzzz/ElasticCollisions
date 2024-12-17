package com.yuriihetsko.elasticcollisions

import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

data class Vector(val x: Double, val y: Double)

object VectorUtils {

    fun Vector.normalisation(v: Vector): Double {
        return sqrt((v.x.pow(2)) + (v.y.pow(2)))
    }

    fun Vector.dot(other: Vector): Double {
        return (this.x * other.x) + (this.y * other.y)
    }

    fun Vector.add(other: Vector): Vector {
        return Vector(this.x + other.x, this.y + other.y)
    }

    fun Vector.mult(other: Vector): Vector {
        return Vector(this.x * other.x, this.y * other.y)
    }

    fun Vector.div(other: Double): Vector {
        return Vector(this.x / x, this.y / y)
    }

    fun random(): Vector {
        return Vector(Random.nextDouble(2.0, 6.0), Random.nextDouble(2.0, 6.0))
    }

    fun zero(): Vector {
        return Vector(0.0, 0.0)
    }
}
