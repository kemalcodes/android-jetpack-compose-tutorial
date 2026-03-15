package com.kemalcodes.composetutorial.notes

// Tutorial #14: Dependency Injection with Hilt — ViewModel with Injection
// This file demonstrates the @HiltViewModel annotation.
// Compare this to Tutorial #13 where we used AndroidViewModel and manually
// created the DAO via DatabaseProvider.getDatabase(application).noteDao().
// With Hilt, the NoteDao is injected automatically through the constructor.
// We use a regular ViewModel instead of AndroidViewModel — no need for Application context.
// The benefit: the ViewModel doesn't know or care where the DAO comes from.

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// State class to hold the UI state
data class NotesState(
    val notes: List<Note> = emptyList(),
    val searchQuery: String = ""
)

// @HiltViewModel tells Hilt this ViewModel should be created with dependency injection
// @Inject constructor tells Hilt to provide the NoteDao automatically
// No more AndroidViewModel or manual DatabaseProvider — Hilt handles everything
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val dao: NoteDao
) : ViewModel() {

    private val _state = MutableStateFlow(NotesState())
    val state: StateFlow<NotesState> = _state.asStateFlow()
    private var searchJob: Job? = null

    // Start observing all notes when the ViewModel is created
    init {
        observeNotes("")
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch { dao.insert(Note(title = title, content = content)) }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch { dao.delete(note) }
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
        observeNotes(query)
    }

    // Observe notes from the database — the Flow updates the UI automatically
    private fun observeNotes(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            val flow = if (query.isEmpty()) dao.getAllNotes() else dao.searchNotes(query)
            flow.collect { notes -> _state.update { it.copy(notes = notes) } }
        }
    }
}
