package com.yuriihetsko.elasticcollisions

import com.yuriihetsko.elasticcollisions.VectorUtils.add
import com.yuriihetsko.elasticcollisions.VectorUtils.dot
import com.yuriihetsko.elasticcollisions.VectorUtils.mag
import com.yuriihetsko.elasticcollisions.VectorUtils.mult
import com.yuriihetsko.elasticcollisions.VectorUtils.setMag
import com.yuriihetsko.elasticcollisions.VectorUtils.sub
import com.yuriihetsko.elasticcollisions.ui.theme.particleColors
import kotlin.math.round
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.random.nextInt


data class Particle(val x: Double, val y: Double) {

    var position = Vector(x, y)
    var velocity = VectorUtils.random(MIN_VELOCITY, MAX_VELOCITY)
    var acceleration = VectorUtils.zero()
    var mass = Random.nextDouble(MIN_MASS, MAX_MASS)
    var radius = sqrt(mass) * SCALE_FACTOR
    val color = particleColors[Random.nextInt(0..15)]

    fun update() {
        velocity = velocity.add(acceleration)
        position = position.add(velocity)
        acceleration = acceleration.mult(VectorUtils.zero())
    }

    fun collide(other: Particle) {
        var impactVector = other.position.sub(this.position)
        var distance = impactVector.mag()

        if (distance < radius + other.radius) {

            // Push the particles out so that they are not overlapping
            val overlap = distance - (radius + other.radius)
            var dirVector = impactVector.copy()
            dirVector = dirVector.setMag(overlap * 0.5)
            this.position = this.position.add(dirVector)
            other.position = other.position.sub(dirVector)
            // Correct the distance
            distance = radius + other.radius
            impactVector = impactVector.setMag(distance)

            val mSum = mass + other.mass
            val vDiff = other.velocity.sub(this.velocity)

            // Particle A (this)
            val num = vDiff.dot(impactVector)
            val den = mSum * distance * distance

            var deltaVA = impactVector.copy()
            deltaVA = deltaVA.mult(2 * other.mass * num / den)
            this.velocity = this.velocity.add(deltaVA)

            // Particle B (other)
            var deltaVB = impactVector.copy()
            deltaVB = deltaVB.mult(-2 * this.mass * num / den)
            other.velocity = other.velocity.add(deltaVB)
        }

    }

    fun edges(width: Int, height: Int) {
        if (position.xComp > width - radius) {
            position = position.copy(xComp = width - radius)
            velocity = velocity.copy(xComp = velocity.xComp * -1)
        } else if (position.xComp < radius) {
            position = position.copy(xComp = radius)
            velocity = velocity.copy(xComp = velocity.xComp * -1)
        }

        if (position.yComp > height - radius) {
            position = position.copy(yComp = height - radius)
            velocity = velocity.copy(yComp = velocity.yComp * -1)
        } else if (position.yComp < radius) {
            position = position.copy(yComp = radius)
            velocity = velocity.copy(yComp = velocity.yComp * -1)
        }
    }

    override fun toString(): String {
        return "Particle { position=$position, velocity=$velocity, mass=$mass, radius=$radius }"
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    companion object {
        private const val SCALE_FACTOR = 50
        private const val MIN_MASS = 3.0
        private const val MAX_MASS = 10.0
        private const val MIN_VELOCITY = 2.0
        private const val MAX_VELOCITY = 4.0
    }
}
