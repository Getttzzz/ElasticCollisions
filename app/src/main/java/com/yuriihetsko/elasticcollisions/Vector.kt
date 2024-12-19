package com.yuriihetsko.elasticcollisions

import kotlin.math.sqrt
import kotlin.random.Random

data class Vector(val xComp: Double, val yComp: Double)

object VectorUtils {

    fun Vector.magSq(): Double {
        return (xComp * xComp) + (yComp * yComp)
    }

    fun Vector.mag(): Double {
        return sqrt(magSq())
    }

    fun Vector.dot(other: Vector): Double {
        return xComp * other.xComp + yComp * other.yComp
    }

    fun Vector.setMag(n: Double): Vector {
        return this.normalize().mult(n)
    }

    fun Vector.normalize(): Vector {
        val len: Double = this.mag()
        return if (len != 0.0) this.mult(1 / len) else this
    }

    fun Vector.dist(other: Vector): Double {
        return this.sub(other).mag()
    }

    fun Vector.add(other: Vector): Vector {
        return Vector(this.xComp + other.xComp, this.yComp + other.yComp)
    }

    fun Vector.sub(other: Vector): Vector {
        return Vector(this.xComp - other.xComp, this.yComp - other.yComp)
    }

    fun Vector.mult(other: Vector): Vector {
        return Vector(this.xComp * other.xComp, this.yComp * other.yComp)
    }

    fun Vector.mult(num: Double): Vector {
        return Vector(xComp * num, yComp * num)
    }

    fun Vector.div(other: Double): Vector {
        return Vector(this.xComp / xComp, this.yComp / yComp)
    }

    fun random(from: Double, to: Double): Vector {
        return Vector(Random.nextDouble(from, to), Random.nextDouble(from, to))
    }

    fun zero(): Vector {
        return Vector(0.0, 0.0)
    }
}
