package com.yuriihetsko.elasticcollisions

import androidx.compose.ui.graphics.Color
import com.yuriihetsko.elasticcollisions.VectorUtils.add
import com.yuriihetsko.elasticcollisions.VectorUtils.div
import com.yuriihetsko.elasticcollisions.VectorUtils.mult
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.random.nextInt


data class Particle(val x: Double, val y: Double) {

    var position = Vector(x, y)
    var velocity = VectorUtils.random() // speed
    var acceleration = VectorUtils.zero()
    var mass = Random.nextDouble(2.0, 10.0)
    var radius = sqrt(mass) * 20
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

    override fun toString(): String {
        return "Particle { position=$position, velocity=$velocity, mass=$mass, radius=$radius }"
    }
}
