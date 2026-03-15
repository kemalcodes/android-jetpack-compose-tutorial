// Tutorial #16: Canvas Drawing in Jetpack Compose
// Demonstrates Canvas API, drawArc, drawBehind, and custom chart composables.
// kemalcodes — https://kemalcodes.com

package com.kemalcodes.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CanvasScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Main scrollable screen showing all Canvas drawing examples
@Composable
fun CanvasScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionTitle("Canvas Drawing")

        // Section 1: Circular Progress Indicator using drawArc
        SectionTitle("Circular Progress")
        Text(
            text = "Uses Canvas drawArc to render a progress ring.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        CircularProgress(progress = 0.72f)

        Spacer(modifier = Modifier.height(32.dp))

        // Section 2: Simple Bar Chart using Box/Column
        SectionTitle("Bar Chart")
        Text(
            text = "Built with Box and Column — no Canvas needed for simple charts.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        BarChart(
            data = listOf(
                "Mon" to 65f,
                "Tue" to 40f,
                "Wed" to 85f,
                "Thu" to 55f,
                "Fri" to 90f,
                "Sat" to 30f,
                "Sun" to 70f
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Section 3: Donut Chart using Canvas drawArc
        SectionTitle("Donut Chart")
        Text(
            text = "Canvas drawArc with Stroke style creates donut segments.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        DonutChart(
            data = listOf(
                "Kotlin" to 45f,
                "Swift" to 25f,
                "Dart" to 20f,
                "Other" to 10f
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Section 4: Modifier.drawBehind example
        SectionTitle("drawBehind Modifier")
        Text(
            text = "Modifier.drawBehind lets you draw behind a composable's content.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(12.dp))
        DrawBehindExample()

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// Section title helper
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

// Circular progress ring using Canvas drawArc.
// The background track is drawn first, then the progress arc on top.
@Composable
fun CircularProgress(
    progress: Float,
    modifier: Modifier = Modifier
) {
    // Animate the sweep angle for a smooth entrance
    var animatedTarget by remember { mutableFloatStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = animatedTarget,
        animationSpec = tween(durationMillis = 1000),
        label = "progress"
    )
    LaunchedEffect(progress) { animatedTarget = progress }

    val trackColor = MaterialTheme.colorScheme.surfaceVariant
    val progressColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onSurface

    Box(contentAlignment = Alignment.Center, modifier = modifier.size(180.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 20.dp.toPx()
            val arcSize = size.minDimension - strokeWidth

            // Background track — full circle
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Progress arc — starts at top (-90°)
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = animatedProgress * 360f,
                useCenter = false,
                topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Percentage text in the center
        Text(
            text = "${(animatedProgress * 100).toInt()}%",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = textColor
        )
    }
}

// Simple bar chart built with Box and Column composables.
// Each bar's height is proportional to its value relative to the max.
@Composable
fun BarChart(
    data: List<Pair<String, Float>>,
    modifier: Modifier = Modifier
) {
    val maxValue = data.maxOf { it.second }
    val barColor = MaterialTheme.colorScheme.primary
    val maxBarHeight = 160.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { (label, value) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                // Value label above the bar
                Text(
                    text = "${value.toInt()}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))

                // The bar itself — height is proportional
                Box(
                    modifier = Modifier
                        .width(28.dp)
                        .height(maxBarHeight * (value / maxValue))
                        .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                        .background(barColor)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Day label below the bar
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// Donut chart using Canvas drawArc with Stroke style.
// Each segment is drawn as a separate arc with a gap between them.
@Composable
fun DonutChart(
    data: List<Pair<String, Float>>,
    modifier: Modifier = Modifier
) {
    val total = data.sumOf { it.second.toDouble() }.toFloat()
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.error
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(modifier = modifier.size(180.dp)) {
            val strokeWidth = 36.dp.toPx()
            val arcSize = size.minDimension - strokeWidth
            val gapAngle = 3f // Small gap between segments
            var startAngle = -90f

            data.forEachIndexed { index, (_, value) ->
                val sweepAngle = (value / total) * 360f - gapAngle

                // Draw each donut segment
                drawArc(
                    color = colors[index % colors.size],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(arcSize, arcSize),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
                startAngle += sweepAngle + gapAngle
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Legend showing each segment's label and color
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            data.forEachIndexed { index, (label, value) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(colors[index % colors.size])
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$label ${(value / total * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Modifier.drawBehind draws behind the composable's content.
// Here we draw a rounded rectangle and a diagonal line behind text.
@Composable
fun DrawBehindExample(modifier: Modifier = Modifier) {
    val bgColor = MaterialTheme.colorScheme.primaryContainer
    val accentColor = MaterialTheme.colorScheme.primary

    Text(
        text = "This text has a custom\ncanvas background drawn\nbehind it using drawBehind",
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                // Rounded rectangle background
                drawRoundRect(
                    color = bgColor,
                    cornerRadius = CornerRadius(16.dp.toPx()),
                    size = size
                )

                // Decorative diagonal line
                drawLine(
                    color = accentColor.copy(alpha = 0.3f),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, 0f),
                    strokeWidth = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
            .padding(24.dp)
    )
}

@Preview(showBackground = true, name = "Light")
@Composable
fun CanvasScreenPreviewLight() {
    AndroidjetpackcomposetutorialTheme(darkTheme = false, dynamicColor = false) {
        CanvasScreen()
    }
}

@Preview(showBackground = true, name = "Dark")
@Composable
fun CanvasScreenPreviewDark() {
    AndroidjetpackcomposetutorialTheme(darkTheme = true, dynamicColor = false) {
        CanvasScreen()
    }
}
