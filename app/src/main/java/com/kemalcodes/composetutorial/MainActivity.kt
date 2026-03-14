package com.kemalcodes.composetutorial

// Tutorial #2: Layouts — Column, Row, and Box Explained
// https://kemalcodes.com/posts/jetpack-compose-tutorial-layouts/
//
// This file demonstrates:
// - Column (vertical layout) with Arrangement and Alignment
// - Row (horizontal layout) with weight
// - Box (layered layout) with individual alignment
// - Spacer for adding empty space
// - Combining all three to build real screens
// - Practical examples: settings screen, message row, product card

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                    LayoutExamplesScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// --- Main Screen ---
// Shows all layout examples in a scrollable column

@Composable
fun LayoutExamplesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // Makes the screen scrollable
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp) // Space between each section
    ) {
        // Section title
        Text("Layout Examples", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // --- Column Example ---
        SectionTitle("Column — Settings Screen")
        SettingsScreen()

        // --- Row Example ---
        SectionTitle("Row — Message Bubbles")
        MessageRow(sender = "Alex", message = "Hey, did you see the new Compose update?", time = "10:30")
        MessageRow(sender = "Kemal", message = "Yes! Navigation 3 looks great.", time = "10:32")

        // --- Box Example ---
        SectionTitle("Box — Icon with Badge")
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            IconWithBadge(count = 3)
            IconWithBadge(count = 0)
            IconWithBadge(count = 99)
        }

        // --- All Together ---
        SectionTitle("Combined — Product Card")
        ProductCard(
            name = "Wireless Headphones",
            price = "$49.99",
            rating = "4.5",
            isNew = true
        )
        ProductCard(
            name = "USB-C Cable",
            price = "$12.99",
            rating = "4.8",
            isNew = false
        )

        // Extra space at the bottom
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

// --- Column Example: Settings Screen ---
// Column places items from top to bottom

@Composable
fun SettingsScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp) // Small gap between items
    ) {
        SettingsItem(title = "Notifications", subtitle = "Manage alerts and sounds")
        SettingsItem(title = "Appearance", subtitle = "Dark mode, font size, theme")
        SettingsItem(title = "Privacy", subtitle = "Permissions and data")
        SettingsItem(title = "Storage", subtitle = "Cache and downloads")
    }
}

@Composable
fun SettingsItem(title: String, subtitle: String) {
    // Each settings item is also a Column (title above subtitle)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// --- Row Example: Message Bubble ---
// Row places items from left to right

@Composable
fun MessageRow(sender: String, message: String, time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top // Align items to the top
    ) {
        // Avatar — fixed size circle with first letter
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = sender.firstOrNull()?.toString() ?: "?",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Message content — takes remaining space with weight(1f)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = sender, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(
                text = message,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Time — fixed width on the right
        Text(
            text = time,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

// --- Box Example: Icon with Badge ---
// Box places items on top of each other (like layers)

@Composable
fun IconWithBadge(count: Int) {
    Box {
        // Bottom layer: the icon
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            modifier = Modifier.size(32.dp)
        )

        // Top layer: the red badge (only if count > 0)
        if (count > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd) // Position in top-right corner
                    .size(18.dp)
                    .background(Color.Red, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (count > 99) "99+" else count.toString(),
                    color = Color.White,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// --- Combined Example: Product Card ---
// Uses Box (for badge overlay), Column (vertical layout), Row (price + rating)

@Composable
fun ProductCard(
    name: String,
    price: String,
    rating: String,
    isNew: Boolean
) {
    // Outer Box — so we can overlay the "NEW" badge
    Box(modifier = Modifier.fillMaxWidth()) {
        // Main card content — Column for vertical layout
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text("Image", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Product name
            Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(4.dp))

            // Row for price and rating — side by side
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = price,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(text = "⭐ $rating", fontSize = 14.sp)
            }
        }

        // "NEW" badge — overlaid on top-right corner using Box alignment
        if (isNew) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "NEW",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun LayoutExamplesPreview() {
    AndroidjetpackcomposetutorialTheme {
        LayoutExamplesScreen()
    }
}

@Preview(
    showBackground = true,
    name = "Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun LayoutExamplesDarkPreview() {
    AndroidjetpackcomposetutorialTheme {
        LayoutExamplesScreen()
    }
}
