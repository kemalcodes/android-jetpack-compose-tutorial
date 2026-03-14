package com.kemalcodes.composetutorial.counter

// Tutorial #10: MVI — Keep Your App Simple and Clean
// INTENT: Describes what the user wants to do.
// We use a sealed interface so we have a fixed list of actions.
// data object is the modern Kotlin way (instead of object).

sealed interface CounterIntent {
    data object Increment : CounterIntent  // User wants to add 1
    data object Decrement : CounterIntent  // User wants to subtract 1
    data object Reset : CounterIntent      // User wants to go back to 0
}
