// Category.kt — Defines the types of tasks a user can create.
// Using an enum keeps the options fixed and type-safe,
// so you can not accidentally set an invalid category.
package com.kemalcodes.composetutorial.domain.model

enum class Category {
    WORK,
    PERSONAL,
    SHOPPING,
    HEALTH
}
