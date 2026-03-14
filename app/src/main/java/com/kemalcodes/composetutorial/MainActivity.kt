package com.kemalcodes.composetutorial

// Tutorial #3: Modifiers — The Secret to Styling Everything
// https://kemalcodes.com/posts/jetpack-compose-tutorial-modifiers/
//
// This file demonstrates:
// - Size modifiers: fillMaxWidth, size, weight
// - Spacing modifiers: padding (inner and outer), offset
// - Appearance modifiers: background, clip, border, shadow, alpha
// - Interaction modifiers: clickable, verticalScroll
// - Why modifier ORDER matters
// - Practical examples: styled card, tag chips, avatar with status dot

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                    ModifierExamplesScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// --- Main Screen ---

@Composable
fun ModifierExamplesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("Modifier Examples", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // --- Modifier Order Demo ---
        SectionTitle("Modifier Order Matters")
        ModifierOrderDemo()

        // --- Styled Card ---
        SectionTitle("Styled Card")
        StyledCard(title = "Jetpack Compose", subtitle = "Build beautiful UIs with Kotlin")
        StyledCard(title = "Material 3", subtitle = "Modern design system for apps")

        // --- Tag Chips ---
        SectionTitle("Tag Chips (clickable)")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TagChip("Kotlin")
            TagChip("Compose")
            TagChip("Dev")
        }

        // --- Avatar with Status ---
        SectionTitle("Avatar with Status Dot")
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AvatarWithStatus(name = "kemalcodes", isOnline = true)
            AvatarWithStatus(name = "Alex", isOnline = false)
            AvatarWithStatus(name = "Sam", isOnline = true)
        }

        // --- Custom Button ---
        SectionTitle("Custom Button (built with modifiers)")
        PrimaryButton(text = "Get Started")

        // --- Border and Clip Examples ---
        SectionTitle("Border & Clip Shapes")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Rounded rectangle
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("12dp", fontSize = 11.sp)
            }
            // Circle
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("Circle", fontSize = 11.sp)
            }
            // Border only
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("Border", fontSize = 11.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// --- Helper ---

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.primary
    )
}

// --- Modifier Order Demo ---
// Shows how padding + background order changes the result

@Composable
fun ModifierOrderDemo() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // padding THEN background — padding is outside the color
        Column {
            Text("padding → background", fontSize = 12.sp, color = Color.Gray)
            Text(
                text = "Hello",
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color(0xFF6200EE))
                    .padding(8.dp),
                color = Color.White
            )
        }
        // background THEN padding — padding is inside the color
        Column {
            Text("background → padding", fontSize = 12.sp, color = Color.Gray)
            Text(
                text = "Hello",
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color(0xFF6200EE))
                    .padding(16.dp),
                color = Color.White
            )
        }
    }
}

// --- Styled Card ---

@Composable
fun StyledCard(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)      // Outer space (like margin)
            .shadow(4.dp, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .padding(16.dp)                   // Inner space for content
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = subtitle, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// --- Tag Chip ---
// clip BEFORE clickable so ripple stays inside the rounded shape

@Composable
fun TagChip(label: String) {
    Text(
        text = label,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .clickable { /* handle click */ }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        fontSize = 14.sp
    )
}

// --- Avatar with Status Dot ---

@Composable
fun AvatarWithStatus(name: String, isOnline: Boolean) {
    Box {
        // Avatar circle
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.firstOrNull()?.toString() ?: "?",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        // Status dot
        Box(
            modifier = Modifier
                .size(14.dp)
                .align(Alignment.BottomEnd)
                .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                .background(
                    if (isOnline) Color(0xFF4CAF50) else Color.Gray,
                    CircleShape
                )
        )
    }
}

// --- Custom Button ---

@Composable
fun PrimaryButton(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable { /* handle click */ }
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun ModifierExamplesPreview() {
    AndroidjetpackcomposetutorialTheme {
        ModifierExamplesScreen()
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ModifierExamplesDarkPreview() {
    AndroidjetpackcomposetutorialTheme {
        ModifierExamplesScreen()
    }
}
