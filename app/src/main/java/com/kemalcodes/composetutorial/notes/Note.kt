package com.kemalcodes.composetutorial.notes

// Tutorial #14: Dependency Injection with Hilt — Room Entity
// This file defines the Note data class as a Room Entity.
// The Entity annotation tells Room to create a database table for this class.
// This is the same as Tutorial #13 — Hilt doesn't change how entities work.

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity marks this class as a database table called "notes"
// @PrimaryKey with autoGenerate creates an auto-incrementing ID
@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis()
)
