// MainActivity.kt
// Demonstrates adaptive layouts using BoxWithConstraints.
// Switches between compact (single column) and expanded (two column) layouts.

package com.kemalcodes.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdaptiveLayoutScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Main screen that adapts layout based on available width
@Composable
fun AdaptiveLayoutScreen(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        // Determine if the layout should be compact or expanded
        val isExpanded = maxWidth >= 600.dp
        val horizontalPadding = if (isExpanded) 32.dp else 16.dp

        if (isExpanded) {
            // Two-column layout for wider screens
            ExpandedLayout(horizontalPadding = horizontalPadding)
        } else {
            // Single-column layout for compact screens
            CompactLayout(horizontalPadding = horizontalPadding)
        }
    }
}

// Single-column layout for phones and narrow screens
@Composable
fun CompactLayout(horizontalPadding: Dp) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = horizontalPadding, vertical = 16.dp)
    ) {
        ProfileCard()
        Spacer(modifier = Modifier.height(16.dp))
        SettingsList()
        Spacer(modifier = Modifier.height(16.dp))
        AboutSection()
    }
}

// Two-column layout for tablets and wider screens
@Composable
fun ExpandedLayout(horizontalPadding: Dp) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Left column: profile card
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            ProfileCard()
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Right column: settings and about
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsList()
            Spacer(modifier = Modifier.height(16.dp))
            AboutSection()
        }
    }
}

// Displays user profile information inside a card
@Composable
fun ProfileCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Jane Doe",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "jane.doe@example.com",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Joined March 2025",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
            )
        }
    }
}

// Displays a list of settings items inside a card
@Composable
fun SettingsList() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))

            val settingsItems = listOf(
                "Notifications",
                "Privacy",
                "Language",
                "Storage",
                "Accessibility"
            )

            settingsItems.forEachIndexed { index, item ->
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (index < settingsItems.lastIndex) {
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}

// Displays an about section inside a card
@Composable
fun AboutSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "About",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "This app demonstrates adaptive layouts in Jetpack Compose. " +
                    "It uses BoxWithConstraints to detect the available width and " +
                    "switches between a single-column layout on phones and a " +
                    "two-column layout on tablets.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Version 1.0.0",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.6f)
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun AdaptiveLayoutPreviewLight() {
    AndroidjetpackcomposetutorialTheme(darkTheme = false) {
        AdaptiveLayoutScreen()
    }
}

@Preview(showBackground = true, name = "Dark Mode")
@Composable
fun AdaptiveLayoutPreviewDark() {
    AndroidjetpackcomposetutorialTheme(darkTheme = true) {
        AdaptiveLayoutScreen()
    }
}
