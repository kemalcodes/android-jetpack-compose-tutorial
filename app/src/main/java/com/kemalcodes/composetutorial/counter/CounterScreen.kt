package com.kemalcodes.composetutorial.counter

// Tutorial #10: MVI — Keep Your App Simple and Clean
// VIEW (UI): The UI has one job — show the state and send intents.
// It does NOT think. It does NOT calculate. It only displays and sends intents.
// This is Rule #3 of MVI.

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CounterScreen(
    modifier: Modifier = Modifier,
    viewModel: CounterViewModel = viewModel()
) {
    // Read the current state
    val state by viewModel.state

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "MVI Counter",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "The UI just reads state and sends intents",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Display the count from state
        // The UI does not calculate — it just shows what the state says
        Text(
            text = "${state.count}",
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Buttons send intents — they don't change state directly
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // Send Decrement intent
            Button(
                onClick = { viewModel.onIntent(CounterIntent.Decrement) }
            ) {
                Text("  −  ", fontSize = 20.sp)
            }

            // Send Increment intent
            Button(
                onClick = { viewModel.onIntent(CounterIntent.Increment) }
            ) {
                Text("  +  ", fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Send Reset intent
        OutlinedButton(
            onClick = { viewModel.onIntent(CounterIntent.Reset) }
        ) {
            Text("Reset")
        }
    }
}
