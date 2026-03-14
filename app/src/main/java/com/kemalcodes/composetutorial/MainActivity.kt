package com.kemalcodes.composetutorial

// Tutorial #6: Lists — LazyColumn and LazyRow
// https://kemalcodes.com/posts/jetpack-compose-tutorial-lists/
//
// This file demonstrates:
// - LazyColumn for vertical scrolling lists
// - LazyRow for horizontal filter chips
// - items() with keys for performance
// - Click handling and selection state
// - Combining LazyRow + LazyColumn in one screen
// - Contact list with avatar, online status, and selection

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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme

// Data class for our contacts
data class Contact(
    val id: Int,
    val name: String,
    val email: String,
    val isOnline: Boolean
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// --- Main Screen ---
// Combines LazyRow (horizontal filters) + LazyColumn (vertical contact list)

@Composable
fun ContactListScreen(modifier: Modifier = Modifier) {
    // Sample contact data
    val allContacts = remember {
        listOf(
            Contact(1, "Alex", "alex@example.com", true),
            Contact(2, "Sam", "sam@example.com", false),
            Contact(3, "Jordan", "jordan@example.com", true),
            Contact(4, "Taylor", "taylor@example.com", false),
            Contact(5, "Morgan", "morgan@example.com", true),
            Contact(6, "Casey", "casey@example.com", false),
            Contact(7, "Riley", "riley@example.com", true),
            Contact(8, "Quinn", "quinn@example.com", true),
            Contact(9, "Avery", "avery@example.com", false),
            Contact(10, "Blake", "blake@example.com", true),
            Contact(11, "Drew", "drew@example.com", false),
            Contact(12, "Ellis", "ellis@example.com", true),
        )
    }

    // State: which contact is selected and which filter is active
    var selectedId by remember { mutableStateOf(-1) }
    var activeFilter by remember { mutableStateOf("All") }

    // Filter contacts based on active filter
    val contacts = when (activeFilter) {
        "Online" -> allContacts.filter { it.isOnline }
        "Offline" -> allContacts.filter { !it.isOnline }
        else -> allContacts
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Header
        Text(
            text = "Contacts",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        // --- LazyRow: Horizontal filter chips ---
        // Users can tap a filter to show All, Online, or Offline contacts
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            val filters = listOf("All", "Online", "Offline")
            items(filters) { filter ->
                FilterChip(
                    label = filter,
                    isSelected = filter == activeFilter,
                    onClick = { activeFilter = filter }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- LazyColumn: Vertical contact list ---
        // Uses keys so Compose can track items when the list changes
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(
                items = contacts,
                key = { it.id }  // Unique key for each item
            ) { contact ->
                ContactCard(
                    contact = contact,
                    isSelected = contact.id == selectedId,
                    onClick = { selectedId = contact.id }
                )
            }

            // Footer showing count
            item {
                Text(
                    text = "${contacts.size} contacts",
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

// --- Filter Chip ---
// Changes color when selected

@Composable
fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Text(
        text = label,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.secondaryContainer
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = if (isSelected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSecondaryContainer,
        fontSize = 14.sp
    )
}

// --- Contact Card ---
// Shows avatar with online status, name, email
// Highlights when selected

@Composable
fun ContactCard(
    contact: Contact,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar with online status dot
        Box {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.firstOrNull()?.toString() ?: "?",
                    fontWeight = FontWeight.Bold
                )
            }
            // Green dot for online users
            if (contact.isOnline) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .align(Alignment.BottomEnd)
                        .background(Color(0xFF4CAF50), CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Contact info
        Column(modifier = Modifier.weight(1f)) {
            Text(contact.name, fontWeight = FontWeight.Medium)
            Text(
                contact.email,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Light Mode")
@Composable
fun ContactListPreview() {
    AndroidjetpackcomposetutorialTheme {
        ContactListScreen()
    }
}

@Preview(showBackground = true, name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ContactListDarkPreview() {
    AndroidjetpackcomposetutorialTheme {
        ContactListScreen()
    }
}
