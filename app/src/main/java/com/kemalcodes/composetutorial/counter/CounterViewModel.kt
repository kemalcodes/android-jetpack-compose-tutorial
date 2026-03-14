package com.kemalcodes.composetutorial.counter

// Tutorial #10: MVI — Keep Your App Simple and Clean
// VIEWMODEL: The brain. It receives intents, processes them, and creates new states.
// Data flows in one direction: Intent → ViewModel → State → UI

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {

    // Private state — the UI cannot change this directly
    private val _state = mutableStateOf(CounterState())

    // Public state — the UI can only READ this
    val state: State<CounterState> = _state

    // Handle every user action here
    // When the UI sends an intent, this function creates a new state
    fun onIntent(intent: CounterIntent) {
        when (intent) {
            is CounterIntent.Increment -> {
                // Create a new copy with count + 1
                // We NEVER write _state.value.count = 5
                // We ALWAYS use .copy() to create a new state
                _state.value = _state.value.copy(
                    count = _state.value.count + 1
                )
            }
            is CounterIntent.Decrement -> {
                _state.value = _state.value.copy(
                    count = _state.value.count - 1
                )
            }
            is CounterIntent.Reset -> {
                // Reset to default values
                _state.value = CounterState()
            }
        }
    }
}
