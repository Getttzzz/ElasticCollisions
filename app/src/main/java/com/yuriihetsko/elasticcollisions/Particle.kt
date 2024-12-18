package com.yuriihetsko.elasticcollisions

import androidx.compose.ui.graphics.Color
import com.yuriihetsko.elasticcollisions.VectorUtils.add
import com.yuriihetsko.elasticcollisions.VectorUtils.div
import com.yuriihetsko.elasticcollisions.VectorUtils.dot
import com.yuriihetsko.elasticcollisions.VectorUtils.mag
import com.yuriihetsko.elasticcollisions.VectorUtils.mult
import com.yuriihetsko.elasticcollisions.VectorUtils.setMag
import com.yuriihetsko.elasticcollisions.VectorUtils.sub
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.random.nextInt


data class Particle(val x: Double, val y: Double) {

    var position = Vector(x, y)
    var velocity = VectorUtils.random() // speed
    var acceleration = VectorUtils.zero()
    var mass = Random.nextDouble(2.0, 10.0)
    var radius = sqrt(mass) * SCALE_FACTOR
    val color = Color(red = Random.nextInt(20..220), green = Random.nextInt(20..220), blue = Random.nextInt(20..220))

    // F = M * A - Newton's second law

    //Collision detection

    //Collision resolution

    fun applyForce(force: Vector) {
        acceleration = acceleration.add(force.div(mass))
    }

    fun update() {
        velocity = velocity.add(acceleration)
        position = position.add(velocity)
        acceleration = acceleration.mult(VectorUtils.zero())
    }

    fun collide(other: Particle) {
        var impactVector = other.position.sub(this.position)
        var distance = impactVector.mag()

        if (distance < radius + other.radius) {
//            val overlap = distance - radius + other.radius
//            var dirVector = impactVector.copy()
//            dirVector = dirVector.setMag(overlap * 0.5)
//            this.position = this.position.add(dirVector)
//            other.position = other.position.sub(dirVector)
//
//            // Correct the distance
//            distance = radius + other.radius
//            impactVector = impactVector.setMag(distance)

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

    override fun toString(): String {
        return "Particle { position=$position, velocity=$velocity, mass=$mass, radius=$radius }"
    }

    companion object {
        private const val SCALE_FACTOR = 50
    }
}
