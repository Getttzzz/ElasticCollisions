package com.yuriihetsko.elasticcollisions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    data class MainState(
        val particles: MutableList<Particle> = mutableListOf<Particle>(),
        val isCanvasSizeCalculated: Boolean = false,
    )

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    fun endlessDrawing() {
        viewModelScope.launch {
            while (true) {
                redraw()
                delay(UPDATE_SPEED)
            }
        }
    }

    private fun redraw() {
        _mainState.value = MainState(
            particles = mutableListOf(),
            isCanvasSizeCalculated = true
        )
    }

    companion object {
        private const val UPDATE_SPEED = 300L
    }
}
