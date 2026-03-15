// Tutorial #17: Performance in Jetpack Compose
// Demonstrates stable types, remember, derivedStateOf, and LazyColumn with keys.
// kemalcodes — https://kemalcodes.com

package com.kemalcodes.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kemalcodes.composetutorial.ui.theme.AndroidjetpackcomposetutorialTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidjetpackcomposetutorialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PerformanceScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// --- Stable vs Unstable Types ---
// Compose skips recomposition for composables with stable parameters.
// Primitive types, String, and @Immutable/@Stable classes are stable.

// UNSTABLE: Regular data class with a mutable list — Compose cannot guarantee
// it won't change, so composables using this always recompose.
data class UnstableContact(
    val name: String,
    val tags: List<String> // List is not stable — could be mutable
)

// STABLE: Marking with @Immutable tells Compose this object never changes
// after construction, enabling recomposition skipping.
@Immutable
data class StableContact(
    val id: Int,
    val name: String,
    val role: String
)

// Main screen combining all performance examples in a single LazyColumn
@Composable
fun PerformanceScreen(modifier: Modifier = Modifier) {
    // Search query state
    var searchQuery by remember { mutableStateOf("") }

    // Sample data — created once using remember to avoid re-allocation
    val contacts = remember {
        listOf(
            StableContact(1, "Alex", "Engineer"),
            StableContact(2, "Sam", "Designer"),
            StableContact(3, "Jordan", "Manager"),
            StableContact(4, "Taylor", "Analyst"),
            StableContact(5, "Morgan", "Developer"),
            StableContact(6, "Casey", "Architect"),
            StableContact(7, "Riley", "Tester"),
            StableContact(8, "Quinn", "DevOps"),
            StableContact(9, "Avery", "Product Owner"),
            StableContact(10, "Blake", "Scrum Master"),
            StableContact(11, "Drew", "Tech Lead"),
            StableContact(12, "Jamie", "Data Scientist")
        )
    }

    // derivedStateOf: only recomputes when searchQuery changes,
    // not on every recomposition. Useful for filtering/transforming state.
    val filteredContacts by remember {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                contacts
            } else {
                contacts.filter { contact ->
                    contact.name.contains(searchQuery, ignoreCase = true) ||
                        contact.role.contains(searchQuery, ignoreCase = true)
                }
            }
        }
    }

    // remember: caches an expensive computation so it runs only once.
    // Without remember, this would recalculate on every recomposition.
    val primeNumbers = remember {
        computePrimes(limit = 500)
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Fixed header section (not scrollable)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SectionTitle("Performance Concepts")

            // --- Section 1: Stable vs Unstable Types ---
            SectionTitle("Stable vs Unstable Types")
            StabilityExplanation()

            Spacer(modifier = Modifier.height(16.dp))

            // --- Section 2: remember for expensive computation ---
            SectionTitle("remember — Cached Computation")
            Text(
                text = "Primes up to 500 (computed once with remember):",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = primeNumbers.joinToString(", "),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- Section 3: derivedStateOf for search filtering ---
            SectionTitle("derivedStateOf — Search Filter")
            Text(
                text = "derivedStateOf recomputes only when the search query changes.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search contacts") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${filteredContacts.size} results",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))
            SectionTitle("LazyColumn with Keys")
            Text(
                text = "Using key = { it.id } helps Compose track items efficiently.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // --- Section 4: LazyColumn with keys ---
        // Providing a stable key lets Compose reorder items without
        // recomposing everything when the list changes.
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = filteredContacts,
                key = { it.id } // Stable key — enables efficient diffing
            ) { contact ->
                ContactCard(contact = contact)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// Shows the difference between stable and unstable types
@Composable
fun StabilityExplanation() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(12.dp)
    ) {
        // Unstable example
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.error)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Unstable: data class with List<String>",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "  -> Always recomposes (Compose can't skip)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Stable example
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Stable: @Immutable data class with val fields",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "  -> Skips recomposition when inputs unchanged",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// Card for each contact in the LazyColumn.
// Because StableContact is @Immutable, Compose can skip recomposition
// for cards whose contact hasn't changed.
@Composable
fun ContactCard(contact: StableContact) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar circle with initials
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.first().toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = contact.role,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // ID badge
            Text(
                text = "#${contact.id}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
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
            .padding(vertical = 4.dp)
    )
}

// Simulates an expensive computation — finding prime numbers
// This is cached with remember so it only runs once.
fun computePrimes(limit: Int): List<Int> {
    return (2..limit).filter { n ->
        (2..sqrt(n.toDouble()).toInt()).none { n % it == 0 }
    }
}

@Preview(showBackground = true, name = "Light")
@Composable
fun PerformanceScreenPreviewLight() {
    AndroidjetpackcomposetutorialTheme(darkTheme = false, dynamicColor = false) {
        PerformanceScreen()
    }
}

@Preview(showBackground = true, name = "Dark")
@Composable
fun PerformanceScreenPreviewDark() {
    AndroidjetpackcomposetutorialTheme(darkTheme = true, dynamicColor = false) {
        PerformanceScreen()
    }
}
