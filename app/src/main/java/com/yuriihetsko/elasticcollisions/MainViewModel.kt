package com.yuriihetsko.elasticcollisions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Idea number 1: change position and velocity of the particles based on accelerometer.
 * Replicate behavior of steel balls on the table.
 *
 * */

class MainViewModel : ViewModel() {

    data class MainState(
        val particles: MutableList<Particle> = mutableListOf<Particle>(
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
            Particle(400.0, 400.0),
        ),
        val isCanvasSizeCalculated: Boolean = false,
        val counter: Long = 0,
    )

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    fun endlessDrawing(width: Int, height: Int) {
        viewModelScope.launch {
            while (true) {
                redraw(width, height)
                delay(UPDATE_SPEED)
            }
        }
    }

    private fun redraw(width: Int, height: Int) {
        var counter = _mainState.value.counter
        val particles = _mainState.value.particles
        val particlesUpdated = mutableListOf<Particle>()

        particles.forEach { particle ->

            particle.update()

            if (particle.position.xComp > width - particle.radius) {
                particle.position = particle.position.copy(xComp = width - particle.radius)
                particle.velocity = particle.velocity.copy(xComp = particle.velocity.xComp * -1)
            } else if (particle.position.xComp < particle.radius) {
                particle.position = particle.position.copy(xComp = particle.radius)
                particle.velocity = particle.velocity.copy(xComp = particle.velocity.xComp * -1)
            }

            if (particle.position.yComp > height - particle.radius) {
                particle.position = particle.position.copy(yComp = height - particle.radius)
                particle.velocity = particle.velocity.copy(yComp = particle.velocity.yComp * -1)
            } else if (particle.position.yComp < particle.radius) {
                particle.position = particle.position.copy(yComp = particle.radius)
                particle.velocity = particle.velocity.copy(yComp = particle.velocity.yComp * -1)
            }

            particlesUpdated.add(particle)
        }


        _mainState.value = _mainState.value.copy(
            particles = particlesUpdated,
            isCanvasSizeCalculated = true,
            counter = ++counter
        )
    }

    companion object {
        private const val UPDATE_SPEED = 1L
    }
}
