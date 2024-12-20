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
 * Idea number 2: add DVD staff with TV box on top with transparent.
 * */

class MainViewModel : ViewModel() {

    data class MainState(
        val particles: MutableList<Particle> = mutableListOf<Particle>(
            Particle(200.0, 200.0),
            Particle(400.0, 1200.0),
        ),
        val isCanvasSizeCalculated: Boolean = false,
        val counter: Long = 0,
    )

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    fun addParticle(x: Float, y: Float) {
        val old = _mainState.value.particles
        old.add(Particle(x.toDouble(), y.toDouble()))
        _mainState.value = _mainState.value.copy(particles = old)
    }

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

        particles.forEachIndexed { i, currParticle ->
            for (j in i + 1..particles.size - 1) {
                currParticle.collide(particles[j])
            }

            currParticle.update()

            currParticle.edges(width, height)

            particlesUpdated.add(currParticle)
        }


        _mainState.value = _mainState.value.copy(
            particles = particlesUpdated,
            isCanvasSizeCalculated = true,
            counter = ++counter
        )
    }

    fun removeOneParticle() {
        _mainState.value.particles.removeFirstOrNull()
    }

    fun removeAll() {
        _mainState.value.particles.clear()
    }

    companion object {
        private const val UPDATE_SPEED = 10L
    }
}
