package com.kemalcodes.composetutorial

// Tutorial #15: Animations — Make Your UI Feel Alive
// https://kemalcodes.com/posts/jetpack-compose-tutorial-animations/
//
// This file demonstrates:
// - AnimatedVisibility with custom enter/exit transitions
// - animateColorAsState, animateDpAsState, animateFloatAsState
// - animateContentSize for auto-sizing
// - AnimatedContent for switching between content
// - Practical examples: expandable card, animated counter, settings screen

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
                    AnimationsScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// --- Main Screen ---

@Composable
fun AnimationsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Animation Examples", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // AnimatedVisibility — show/hide
        SectionTitle("AnimatedVisibility")
        ToggleMessage()

        // Expandable card
        SectionTitle("Expandable Card")
        ExpandableCard(
            title = "What is Compose?",
            content = "Jetpack Compose is a modern toolkit for building Android UI. It simplifies and accelerates UI development with less code, powerful tools, and intuitive Kotlin APIs."
        )
        ExpandableCard(
            title = "Why Animations?",
            content = "Animations make your app feel polished and responsive. They guide the user's attention and provide feedback that the app is working."
        )

        HorizontalDivider()

        // Animated counter
        SectionTitle("AnimatedContent — Counter")
        AnimatedCounter()

        HorizontalDivider()

        // animateContentSize
        SectionTitle("animateContentSize")
        ExpandableText()

        HorizontalDivider()

        // Animated toggle
        SectionTitle("animateColorAsState")
        AnimatedToggle()

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.primary
    )
}

// --- AnimatedVisibility Demo ---
// Shows and hides content with fade + expand animation

@Composable
fun ToggleMessage() {
    var isVisible by remember { mutableStateOf(false) }

    Column {
        Button(onClick = { isVisible = !isVisible }) {
            Text(if (isVisible) "Hide Message" else "Show Message")
        }

        // Content animates in with expand + fade, out with shrink + fade
        AnimatedVisibility(
            visible = isVisible,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Text(
                "Hello! I appear and disappear with a smooth animation.",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

// --- Expandable Card ---
// Tap to expand/collapse with AnimatedVisibility

@Composable
fun ExpandableCard(title: String, content: String) {
    var isExpanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }

            // Smooth expand/collapse
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Text(
                    content,
                    modifier = Modifier.padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// --- Animated Counter ---
// Numbers slide in/out when count changes

@Composable
fun AnimatedCounter() {
    var count by remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // AnimatedContent switches the displayed number with animation
        AnimatedContent(
            targetState = count,
            transitionSpec = {
                // New number slides in from bottom, old slides out to top
                slideInVertically { it } + fadeIn() togetherWith
                    slideOutVertically { -it } + fadeOut()
            },
            label = "counter"
        ) { targetCount ->
            Text(
                text = "$targetCount",
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { count-- }) { Text("  −  ") }
            Button(onClick = { count++ }) { Text("  +  ") }
            OutlinedButton(onClick = { count = 0 }) { Text("Reset") }
        }
    }
}

// --- animateContentSize Demo ---
// Text expands smoothly when toggled

@Composable
fun ExpandableText() {
    var isExpanded by remember { mutableStateOf(false) }

    Text(
        text = "Jetpack Compose animations make your app feel polished and professional. " +
            "With just a few lines of code, you can add smooth transitions, " +
            "expanding cards, sliding content, and animated counters. " +
            "The best part is that Compose handles all the complexity for you — " +
            "you just declare what the end state should look like.",
        maxLines = if (isExpanded) Int.MAX_VALUE else 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(8.dp)
            )
            .clickable { isExpanded = !isExpanded }
            .padding(16.dp)
    )
}

// --- Animated Toggle ---
// Color smoothly transitions when toggled

@Composable
fun AnimatedToggle() {
    var isEnabled by remember { mutableStateOf(false) }

    // Color animates smoothly between two values
    val backgroundColor by animateColorAsState(
        targetValue = if (isEnabled) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(500),
        label = "toggleColor"
    )

    val textColor by animateColorAsState(
        targetValue = if (isEnabled) MaterialTheme.colorScheme.onPrimaryContainer
        else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(500),
        label = "textColor"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (isEnabled) "Feature Enabled" else "Feature Disabled",
            color = textColor,
            fontWeight = if (isEnabled) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        Switch(checked = isEnabled, onCheckedChange = { isEnabled = it })
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun AnimationsPreview() {
    AndroidjetpackcomposetutorialTheme {
        AnimationsScreen()
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AnimationsDarkPreview() {
    AndroidjetpackcomposetutorialTheme {
        AnimationsScreen()
    }
}
