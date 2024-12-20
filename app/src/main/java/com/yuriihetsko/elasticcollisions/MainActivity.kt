package com.yuriihetsko.elasticcollisions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yuriihetsko.elasticcollisions.ui.theme.ElasticCollisionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = viewModel<MainViewModel>()
            ElasticCollisionsTheme {
                MainScreen(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(WindowInsets.statusBars.asPaddingValues()), viewModel
                )
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val mainState by viewModel.mainState.collectAsState()
    val haptic = LocalHapticFeedback.current
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("Remove one", "Remove all")

    Box(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { newSize ->
                    if (!mainState.isCanvasSizeCalculated) {
                        // This block will run only the first time the Canvas is drawn
                        viewModel.endlessDrawing(newSize.width, newSize.height)
                    }
                }
                .background(Color.DarkGray)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { offset ->
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        viewModel.addParticle(offset.x, offset.y)
                    })
                }
        ) {
            if (!mainState.isCanvasSizeCalculated) return@Canvas

            mainState.particles.forEach { particle ->
                drawCircle(
                    color = particle.color,
                    radius = particle.radius.toFloat(),
                    center = Offset(particle.position.xComp.toFloat(), particle.position.yComp.toFloat()),
                    alpha = 1f,
                    style = Stroke(5f),
                    colorFilter = null,
                    blendMode = DefaultBlendMode,
                )
                drawCircle(
                    color = particle.color,
                    radius = particle.radius.toFloat(),
                    center = Offset(particle.position.xComp.toFloat(), particle.position.yComp.toFloat()),
                    alpha = 0.65f,
                    style = Fill,
                    colorFilter = null,
                    blendMode = DefaultBlendMode,
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                Icons.Default.MoreVert, "More", tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false, radius = 30.dp),
                        onClick = { expanded = true }),
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEachIndexed { index, option ->
                    DropdownMenuItem(text = { Text(text = option) }, onClick = {
                        expanded = false
                        when (index) {
                            0 -> viewModel.removeOneParticle()
                            1 -> viewModel.removeAll()
                        }
                    })
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ElasticCollisionsTheme {
        MainScreen(Modifier.size(400.dp, 400.dp), viewModel<MainViewModel>())
    }
}
