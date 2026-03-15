package com.kemalcodes.composetutorial.domain.model
// Task priority levels
enum class Priority(val level: Int, val displayName: String) {
    LOW(0, "Low"), MEDIUM(1, "Medium"), HIGH(2, "High")
}
