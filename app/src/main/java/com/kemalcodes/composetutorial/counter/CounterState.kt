package com.kemalcodes.composetutorial.counter

// Tutorial #10: MVI — Keep Your App Simple and Clean
// STATE: Holds everything the UI needs to display.
// One screen = one state. This is Rule #1 of MVI.
// We use a data class so we get .copy() for free (Rule #2: never change state directly).

data class CounterState(
    val count: Int = 0
)
