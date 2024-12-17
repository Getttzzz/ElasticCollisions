package com.yuriihetsko.elasticcollisions

import com.yuriihetsko.elasticcollisions.VectorUtils.add
import com.yuriihetsko.elasticcollisions.VectorUtils.div
import com.yuriihetsko.elasticcollisions.VectorUtils.mult
import kotlin.math.sqrt
import kotlin.random.Random


data class Particle(val x: Double, val y: Double) {

    private var position = Vector(x, y)
    private var velocity = VectorUtils.random() // speed
    private var acceleration = VectorUtils.zero()
    private var mass = Random.nextDouble(2.0, 6.0)
    private var r = sqrt(mass) * 20

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
}
