// PriorityBadge.kt — A small colored indicator showing the task priority.
// LOW is green, MEDIUM is orange, and HIGH is red.
// This makes it easy to scan the task list and spot urgent items.
package com.kemalcodes.composetutorial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kemalcodes.composetutorial.domain.model.Priority

@Composable
fun PriorityBadge(
    priority: Priority,
    modifier: Modifier = Modifier
) {
    // Pick a color based on priority level
    val color = when (priority) {
        Priority.LOW -> Color(0xFF4CAF50)    // Green
        Priority.MEDIUM -> Color(0xFFFF9800) // Orange
        Priority.HIGH -> Color(0xFFF44336)   // Red
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Small colored dot
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        // Priority label next to the dot
        Text(
            text = priority.name.lowercase().replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.labelSmall,
            color = color,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
