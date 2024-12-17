package com.yuriihetsko.elasticcollisions

import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

data class Vector(val xComp: Double, val yComp: Double)

object VectorUtils {

    fun Vector.normalisation(v: Vector): Double {
        return sqrt((v.xComp.pow(2)) + (v.yComp.pow(2)))
    }

    fun Vector.dot(other: Vector): Double {
        return (this.xComp * other.xComp) + (this.yComp * other.yComp)
    }

    fun Vector.add(other: Vector): Vector {
        return Vector(this.xComp + other.xComp, this.yComp + other.yComp)
    }

    fun Vector.mult(other: Vector): Vector {
        return Vector(this.xComp * other.xComp, this.yComp * other.yComp)
    }

    fun Vector.div(other: Double): Vector {
        return Vector(this.xComp / xComp, this.yComp / yComp)
    }

    fun random(): Vector {
        return Vector(Random.nextDouble(2.0, 6.0), Random.nextDouble(2.0, 6.0))
    }

    fun zero(): Vector {
        return Vector(0.0, 0.0)
    }
}
